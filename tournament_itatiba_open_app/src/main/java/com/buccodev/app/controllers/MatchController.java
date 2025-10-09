package com.buccodev.app.controllers;

import com.buccodev.app.dtos.match.MatchRequestDto;
import com.buccodev.app.dtos.match.MatchResponseDto;
import com.buccodev.app.dtos.match.MatchUpdateDto;
import com.buccodev.app.services.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/create")
    public ResponseEntity<MatchResponseDto> createMatch(@RequestBody MatchRequestDto matchRequestDto) {
        return ResponseEntity.ok(matchService.createMatch(matchRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchResponseDto> getMatchById(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.getMatchById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMatch(@PathVariable Long id, @RequestBody MatchUpdateDto matchRequestDto) {
        matchService.updateMatch(id, matchRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<List<MatchResponseDto>> getMatchesByPlayerId(@PathVariable UUID id) {
            return ResponseEntity.ok(matchService.getMatchesByPlayer(id));
    }

    @GetMapping
    public ResponseEntity<List<MatchResponseDto>> getAllMatches() {
        return ResponseEntity.ok(matchService.getAllMatches());
    }

}

