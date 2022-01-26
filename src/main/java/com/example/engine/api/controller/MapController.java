package com.example.engine.api.controller;

import com.example.engine.api.repository.GameRepository;
import com.example.engine.model.Game;
import com.example.engine.model.GameMap;
import com.example.engine.model.GameState;
import com.example.engine.model.utils.MapToPNG;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("api/map")
public class MapController {

    private final GameRepository gameRepository;

    public MapController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/PNG/{id}")
    public void getMap(@PathVariable String id, HttpServletResponse response) throws IOException {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        if (game.getState() != GameState.STARTED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid state of the game (state != STARTED)");
        }
        GameMap gameMap = game.getMap();
        response.setContentType("image/png");
        response.getOutputStream().write(MapToPNG.getBytes(gameMap));
        response.getOutputStream().close();
    }

}
