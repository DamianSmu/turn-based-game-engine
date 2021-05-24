package com.example.engine.api.controller;

import com.example.engine.api.repository.MapRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class MapController {

    private final MapRepository mapRepository;

    public MapController(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    @GetMapping("map/{id}")
    public ResponseEntity<?> getMap(@PathVariable String id){
        return ResponseEntity.ok(mapRepository.findById(id).get());
    }
}
