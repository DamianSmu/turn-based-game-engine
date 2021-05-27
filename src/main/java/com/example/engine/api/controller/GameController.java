package com.example.engine.api.controller;

import com.example.engine.api.dto.request.ActionRequestDTO;
import com.example.engine.api.dto.response.GameStatusResponseDTO;
import com.example.engine.api.repository.GameRepository;
import com.example.engine.api.repository.PlayerSessionRepository;
import com.example.engine.api.service.UserService;
import com.example.engine.model.Game;
import com.example.engine.model.User;
import com.example.engine.model.actions.ActionRequest;
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
    private final PlayerSessionRepository playerSessionRepository;

    public GameController(GameRepository gameRepository, UserService userService, PlayerSessionRepository playerSessionRepository) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.playerSessionRepository = playerSessionRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getSAll() {
        List<Game> games = gameRepository.findAll();
        List<GameStatusResponseDTO> responses = new ArrayList<>();
        for (Game game : games) {
            GameStatusResponseDTO response = new GameStatusResponseDTO();
            response.setId(game.getId());
            response.setFounder(game.getFounder());
            response.setState(game.getState());
            response.setPlayerSessions(game.getPlayerSessions());
            response.setTurnNumber(game.getTurnNumber());
            response.setCurrentTurn(game.getCurrentTurnPlayerSession());
            responses.add(response);
        }
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/")
    public ResponseEntity<?> createGame(Authentication authentication) {
        User user = userService.getUser(authentication);
        Game game = new Game(new Random().nextLong(), user);
        playerSessionRepository.save(game.registerPlayer(user));
        gameRepository.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(game.getId());
    }

    @PostMapping("/{id}/connect")
    public ResponseEntity<?> connectToGame(Authentication authentication, @PathVariable String id) {
        User user = userService.getUser(authentication);
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game id not found"));
        return ResponseEntity.ok(game.registerPlayer(user));
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
        response.setPlayerSessions(game.getPlayerSessions());
        response.setTurnNumber(game.getTurnNumber());
        response.setCurrentTurn(game.getCurrentTurnPlayerSession());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGame(@PathVariable String id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game id not found"));
        return ResponseEntity.ok(game);
    }

    @PostMapping("/{id}/action")
    public ResponseEntity<?> postAction(Authentication authentication, @PathVariable String id, @RequestBody ActionRequestDTO body) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game id not found"));
        User user = userService.getUser(authentication);
        if (!user.equals(game.getCurrentTurnPlayerSession().getUser())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Now it is not your turn");
        }

        game.addUserActionRequest(new ActionRequest(body.getActionType(), body.getFrom(), body.getTo()));
        gameRepository.save(game);
        return ResponseEntity.ok(game.getActionRequests());
    }

    @GetMapping("/{id}/mapObjects")
    public ResponseEntity<?> getMapObjects(@PathVariable String id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game id not found"));
        return ResponseEntity.ok(game.getMap().getTiles().stream().filter(t -> t.getMapObjects().size() > 0).collect(Collectors.toList()));
    }

    @PostMapping("/{id}/takeTurn")
    public ResponseEntity<?> postAction(Authentication authentication, @PathVariable String id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game id not found"));
        User user = userService.getUser(authentication);
        if (!user.equals(game.getCurrentTurnPlayerSession().getUser())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Now this is not your turn");
        }
        playerSessionRepository.save(game.takeTurn());
        gameRepository.save(game);
        return ResponseEntity.ok(game.getActionRequests());
    }
}
