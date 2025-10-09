package com.buccodev.app.controllers;

import com.buccodev.app.dtos.tournament.TournamentRequestDto;
import com.buccodev.app.dtos.tournament.TournamentResponseDto;
import com.buccodev.app.services.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @PostMapping
    public ResponseEntity<TournamentResponseDto> createTournament(@RequestBody TournamentRequestDto tournamentRequestDto){
        return ResponseEntity.ok(tournamentService.createTournament(tournamentRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentResponseDto> getTournamentById(@PathVariable Long id){
        return ResponseEntity.ok(tournamentService.getTournamentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTournament(@PathVariable Long id, @RequestBody TournamentRequestDto tournamentRequestDto) {
        tournamentService.updateTournament(id, tournamentRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        tournamentService.deleteTournament(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/generate-bracket")
    public ResponseEntity<TournamentResponseDto> generateBracket(@PathVariable Long id) {
        return ResponseEntity.ok(tournamentService.generateBracket(id));
    }

}
