package com.buccodev.app.services;

import com.buccodev.app.dtos.match.MatchRequestDto;
import com.buccodev.app.dtos.match.MatchResponseDto;
import com.buccodev.app.dtos.match.MatchUpdateDto;
import com.buccodev.app.entities.Match;
import com.buccodev.app.repositories.MatchRepository;
import com.buccodev.app.repositories.PlayerRepository;
import com.buccodev.app.repositories.TournamentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final TournamentRepository tournamentRepository;

    public MatchService(MatchRepository matchRepository, PlayerRepository playerRepository, TournamentRepository tournamentRepository) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
        this.tournamentRepository = tournamentRepository;
    }

    @Transactional
    public MatchResponseDto createMatch(MatchRequestDto matchRequestDto) {

        var playerOne = playerRepository.findById(matchRequestDto.playerOneId())
                .orElseThrow(() -> new RuntimeException("Player one not found"));
        var playerTwo = playerRepository.findById(matchRequestDto.playerTwoId())
                .orElseThrow(() -> new RuntimeException("Player two not found"));
        var tournament = tournamentRepository.findById(matchRequestDto.tournamentId())
                .orElseThrow(() -> new RuntimeException("Tournament not found"));

        var maxMatches = getMaxMatchesForRound(matchRequestDto.round());
        
        var count = matchRepository.countByRoundAndTournamentId(matchRequestDto.round(), matchRequestDto.tournamentId());

        if(count >= maxMatches){
            throw new RuntimeException("Max matches reached for round " + matchRequestDto.round());
        }

        var match = MatchRequestDto.toEntity(matchRequestDto);
        match.setPlayer1(playerOne);
        match.setPlayer2(playerTwo);
        match.setTournament(tournament);
        var matchSalved = matchRepository.save(match);
        return MatchResponseDto.fromEntity(matchSalved);
    }

    public MatchResponseDto getMatchById(Long id) {
        var match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found"));
        return MatchResponseDto.fromEntity(match);
    }

    public void updateMatch(Long id, MatchUpdateDto matchUpdateDto) {
        var match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        var score = matchUpdateDto.score() == null || matchUpdateDto.score().isBlank() ? "" : matchUpdateDto.score();
        match.setRound(matchUpdateDto.round());
        match.setSide(matchUpdateDto.side());
        match.setScore(score);

        if(matchUpdateDto.winnerId() == null){
            match.setWinner(null);
            matchRepository.save(match);
            return;
        }

        var player = playerRepository.findById(matchUpdateDto.winnerId())
                .orElseThrow(() -> new RuntimeException("Player not found"));

        if (match.getPlayer1().getId().equals(player.getId())) {
            match.setWinner(match.getPlayer1());
        } else if (match.getPlayer2().getId().equals(player.getId())) {
            match.setWinner(match.getPlayer2());
        } else {
            throw new RuntimeException("Winner must be either Player 1 or Player 2");
        }

        matchRepository.save(match);
    }

    public void deleteMatch(Long id) {
        if (matchRepository.existsById(id)) {
            matchRepository.deleteById(id);
        } else {
            throw new RuntimeException("Match not found");
        }
    }

    public List<MatchResponseDto> getMatchesByPlayer(UUID playerId) {

        var player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        var matches = matchRepository.findByPlayer1(player);

        if(matches.isEmpty()){
            matches = matchRepository.findByPlayer2(player);
        }
        return matches.stream()
                .map(MatchResponseDto::fromEntity)
                .toList();
    }

    public List<MatchResponseDto> getAllMatches(){
        var matches = matchRepository.findAll();
        return matches.stream()
                .map(MatchResponseDto::fromEntity)
                .toList();
    }

    private int getMaxMatchesForRound(int round) {
        return switch (round) {
            case 1 -> 16;
            case 2 -> 8;
            case 3 -> 4;
            case 4 -> 2;
            case 5 -> 1;
            default -> throw new IllegalArgumentException("invalid round: " + round);
        };
    }
}
