package com.example.engine.api.controller;

import com.example.engine.api.dto.request.ActionRequestDTO;
import com.example.engine.api.dto.response.GameStatusResponseDTO;
import com.example.engine.api.dto.response.TurnResponseDTO;
import com.example.engine.api.repository.GameRepository;
import com.example.engine.api.repository.UserRepository;
import com.example.engine.api.service.UserService;
import com.example.engine.model.Game;
import com.example.engine.model.User;
import com.example.engine.model.actions.ActionRequest;
import com.example.engine.model.actions.CannotResolveActionException;
import com.example.engine.model.logs.GameLog;
import com.example.engine.model.logs.LogEntry;
import com.example.engine.model.utils.PositionXY;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/game")
public class GameController {

    private final GameRepository gameRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public GameController(GameRepository gameRepository, UserService userService, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        List<Game> games = gameRepository.findAll();
        List<GameStatusResponseDTO> responses = new ArrayList<>();
        for (Game game : games) {
            GameStatusResponseDTO response = new GameStatusResponseDTO();
            response.setId(game.getId());
            response.setFounder(game.getFounder());
            response.setState(game.getState());
            response.setUsers(game.getUsers());
            response.setTurnNumber(game.getTurnNumber());
            response.setCurrentTurn(game.getCurrentTurnUser());
            responses.add(response);
        }
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/")
    public ResponseEntity<?> createGame(Authentication authentication) {
        User user = userService.getUser(authentication);
        Game game = new Game(user);
        userRepository.save(game.registerPlayer(user));
        gameRepository.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(game.getId());
    }

    @PostMapping("/{id}/connect")
    public ResponseEntity<?> connectToGame(Authentication authentication, @PathVariable String id) {
        User user = userService.getUser(authentication);
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game id not found"));
        User session = userRepository.save(game.registerPlayer(user));
        gameRepository.save(game);
        return ResponseEntity.ok(session);
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<?> startGame(Authentication authentication, @PathVariable String id) {
        User user = userService.getUser(authentication);
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game id not found"));
        if (game.getFounder().equals(user)) {
            game.start();
            gameRepository.save(game);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not founder of this game.");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<?> getStatus(@PathVariable String id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game id not found"));
        GameStatusResponseDTO response = new GameStatusResponseDTO();
        response.setId(game.getId());
        response.setFounder(game.getFounder());
        response.setState(game.getState());
        response.setUsers(game.getUsers());
        response.setTurnNumber(game.getTurnNumber());
        response.setCurrentTurn(game.getCurrentTurnUser());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGame(@PathVariable String id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game id not found"));
        return ResponseEntity.ok(game);
    }

    @PostMapping("/{id}/action")
    public ResponseEntity<?> postAction(Authentication authentication, @PathVariable String id, @RequestBody List<ActionRequestDTO> body) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game id not found"));
        User user = userService.getUser(authentication);
        if (!user.equals(game.getCurrentTurnUser())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Now it is not your turn");
        }
        for( ActionRequestDTO action : body) {
            game.addUserActionRequest(new ActionRequest(action.getActionType(), new PositionXY(action.getX(), action.getY())));
        }
        gameRepository.save(game);
        return ResponseEntity.ok(game.getActionRequests());
    }

    @GetMapping("/{id}/mapObjects")
    public ResponseEntity<?> getMapObjects(@PathVariable String id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game id not found"));
        return ResponseEntity.ok(game.getMap().getTiles().stream().filter(t -> !t.isEmpty()).collect(Collectors.toList()));
    }

    @PostMapping("/{id}/takeTurn")
    public ResponseEntity<?> takeTurn(Authentication authentication, @PathVariable String id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game id not found"));
        User user = userService.getUser(authentication);
        if (!user.equals(game.getCurrentTurnUser())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Now this is not your turn");
        }
        gameRepository.save(game.takeTurn());
        long invalidActions = game.getGameLog().getForTurnAndUser(game.getTurnNumber() - 1, user).filter(x -> x.getTag().equals(LogEntry.INVALID_ACTION)).count();
        return ResponseEntity.ok(new TurnResponseDTO(invalidActions, true)); //todo
    }

    @PostMapping("/{id}/endTurn")
    public ResponseEntity<?> endTurn(Authentication authentication, @PathVariable String id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game id not found"));
        User user = userService.getUser(authentication);
        if (!user.equals(game.getCurrentTurnUser())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Now this is not your turn");
        }
        gameRepository.save(game.endTurn());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/act")
    public ResponseEntity<?> postActionAndTakeTurn(Authentication authentication, @PathVariable String id, @RequestBody List<ActionRequestDTO> body) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game id not found"));
        User user = userService.getUser(authentication);
        if (!user.equals(game.getCurrentTurnUser())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Now it is not your turn");
        }
        for( ActionRequestDTO action : body) {
            game.addUserActionRequest(new ActionRequest(action.getActionType(), new PositionXY(action.getX(), action.getY())));
        }
        gameRepository.save(game.takeTurn());
        long invalidActions = game.getGameLog().getForTurnAndUser(game.getTurnNumber(), user).filter(x -> x.getTag().equals(LogEntry.INVALID_ACTION)).count();
        if(invalidActions > 1){
            System.out.println(invalidActions);
        }
        return ResponseEntity.ok(new TurnResponseDTO(invalidActions, true)); //todo
    }

//    @PostMapping("/{id}/reset")
//    public ResponseEntity<?> reset(Authentication authentication, @PathVariable String id) {
//        Game game = gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game id not found"));
//        User user = userService.getUser(authentication);
//        if (game.getFounder().equals(user)) {
//            game.start();
//            gameRepository.save(game);
//        } else {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not founder of this game.");
//        }
//        return ResponseEntity.ok().build();
//    }
}
