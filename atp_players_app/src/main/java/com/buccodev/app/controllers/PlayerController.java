package com.buccodev.app.controllers;

import com.buccodev.app.dtos.PlayerRequestDto;
import com.buccodev.app.dtos.PlayerResponseDto;
import com.buccodev.app.services.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.lang.IO.println;

@RestController
@RequestMapping("/players")
@CrossOrigin(origins = "*")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<PlayerResponseDto> createPlayer(@RequestBody PlayerRequestDto requestDto){
        println(requestDto);
        return ResponseEntity.ok(playerService.createPlayer(requestDto));
    }

    @PostMapping("/many")
    public ResponseEntity<List<PlayerResponseDto>> createManyPlayers(@RequestBody List<PlayerRequestDto> requestDtos){
        return ResponseEntity.ok(playerService.createManyPlayers(requestDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponseDto> getPlayerById(@PathVariable UUID id){
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable UUID id){
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerResponseDto> updatePlayer(@PathVariable UUID id, @RequestBody PlayerRequestDto requestDto){
        return ResponseEntity.ok(playerService.updatePlayer(id, requestDto));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlayerResponseDto>> getPlayerByName(@RequestParam String name){
        return ResponseEntity.ok(playerService.getPlayerByName(name));
    }

    @GetMapping()
    public ResponseEntity<List<PlayerResponseDto>> getAllPlayers(@RequestParam(defaultValue = "0") Integer page,
                                                                 @RequestParam(defaultValue = "20") Integer size){
        return ResponseEntity.ok(playerService.getAllPlayers(page, size));
    }
}
