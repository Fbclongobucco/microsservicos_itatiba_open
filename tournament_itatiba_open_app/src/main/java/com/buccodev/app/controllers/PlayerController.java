package com.buccodev.app.controllers;

import com.buccodev.app.dtos.player.ListUuidsDto;
import com.buccodev.app.dtos.player.PlayerResponseDto;
import com.buccodev.app.services.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/atp/{name}")
    public ResponseEntity<List<PlayerResponseDto>> getAptPlayers(@PathVariable String name){
        var players = playerService.getAtpPlayers(name);
        return ResponseEntity.ok(players);
    }

    @PostMapping("/add/player/{playerId}/tournament/{tournamentId}")
    public ResponseEntity<Void> addPlayer(@PathVariable UUID playerId, @PathVariable Long tournamentId){
        playerService.addPlayer(playerId, tournamentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/many/tournament/{tournamentId}")
    public ResponseEntity<Void> addManyPlayers( @PathVariable Long tournamentId, @RequestBody List<UUID> playersIds){
        playerService.addManyPlayers(playersIds, tournamentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponseDto> getPlayerTournamentById(@PathVariable UUID id) {
        var player = playerService.getPlayerTournamentById(id);
        return ResponseEntity.ok(player);
    }

    @GetMapping
    public ResponseEntity<List<PlayerResponseDto>> getAllPlayersOfTournament() {
        var players = playerService.getAllPlayersOfTournament();
        return ResponseEntity.ok(players);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePlayer(@PathVariable UUID id){
        playerService.removePlayer(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePlayer(@PathVariable UUID id) {
        playerService.updatePlayer(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/seed/{id}")
    public ResponseEntity<Void> setPlayerAsSeed(@PathVariable UUID id, @RequestParam Boolean seed) {
        playerService.updateSeed(id, seed);
        return ResponseEntity.ok().build();
    }
}
