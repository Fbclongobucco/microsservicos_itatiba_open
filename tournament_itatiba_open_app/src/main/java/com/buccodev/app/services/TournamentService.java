package com.buccodev.app.services;

import com.buccodev.app.dtos.tournament.TournamentRequestDto;
import com.buccodev.app.dtos.tournament.TournamentResponseDto;
import com.buccodev.app.entities.Match;
import com.buccodev.app.entities.Player;
import com.buccodev.app.repositories.MatchRepository;
import com.buccodev.app.repositories.PlayerRepository;
import com.buccodev.app.repositories.TournamentRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final PlayerRepository playerRepository;
    private final MatchRepository  matchRepository;

    public TournamentService(TournamentRepository tournamentRepository, PlayerRepository playerRepository, MatchRepository matchRepository) {
        this.tournamentRepository = tournamentRepository;
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
    }

    public TournamentResponseDto createTournament(TournamentRequestDto tournamentRequestDto){
        var tournament = TournamentResponseDto.toEntity(tournamentRequestDto);
        tournamentRepository.save(tournament);
        return TournamentResponseDto.fromEntity(tournament);
    }

    public TournamentResponseDto getTournamentById(Long id){
        var tournament = tournamentRepository.findById(id).orElseThrow(() -> new RuntimeException("Tournament not found"));
        return TournamentResponseDto.fromEntity(tournament);
    }

    public void updateTournament(Long id, TournamentRequestDto tournamentRequestDto){
        var tournament = tournamentRepository.findById(id).orElseThrow(() -> new RuntimeException("Tournament not found"));
        tournament.setName(tournamentRequestDto.name());
        tournament.setLocation(tournamentRequestDto.location());
        tournament.setStartDate(tournamentRequestDto.startDate());
        tournament.setEndDate(tournamentRequestDto.endDate());
        tournamentRepository.save(tournament);
    }

    public void deleteTournament(Long id){
        var tournament = tournamentRepository.findById(id).orElseThrow(() -> new RuntimeException("Tournament not found"));
        tournamentRepository.delete(tournament);
    }

    @Transactional
    public TournamentResponseDto generateBracket(Long tournamentId){
        var tournament = tournamentRepository.findById(tournamentId).orElseThrow(() -> new RuntimeException("Tournament not found"));

        if(playerRepository.count() != 32) throw new RuntimeException("The brackets are not yet complete");

        var seededPlayers = playerRepository.findAll().stream()
                        .sorted(Comparator.comparing(Player::getRanking))
                                .limit(16).toList();

        seededPlayers.forEach(s -> s.setSeed(true));
        playerRepository.saveAll(seededPlayers);

        var unseededPlayers = playerRepository.findByIsSeed(false);

        var seedersSideA = new ArrayList<Player>();
        var seedersSideB = new ArrayList<Player>();

        for(int i = 0; i < seededPlayers.size(); i++){
            if(i % 2 == 0){
                seedersSideA.add(seededPlayers.get(i));
            } else {
                seedersSideB.add(seededPlayers.get(i));
            }
        }

        var unSeedersSideA = new ArrayList<Player>();
        var unSeedersSideB = new ArrayList<Player>();

        while(unSeedersSideA.size() < 8 && !unseededPlayers.isEmpty()){
            var randomIndex = (int) (Math.random() * unseededPlayers.size());
            unSeedersSideA.add(unseededPlayers.get(randomIndex));
            unseededPlayers.remove(randomIndex);
        }

        while(unSeedersSideB.size() < 8 && !unseededPlayers.isEmpty()){
            var randomIndex = (int) (Math.random() * unseededPlayers.size());
            unSeedersSideB.add(unseededPlayers.get(randomIndex));
            unseededPlayers.remove(randomIndex);
        }

        var matches = createMatches(seedersSideA, seedersSideB, unSeedersSideA, unSeedersSideB );

        tournament.getMatches().addAll(matches);
        matches.forEach(match -> match.setTournament(tournament));
        matchRepository.saveAll(matches);

        return TournamentResponseDto.fromEntity(tournament);
    }

    private List<Match> createMatches(List<Player> seedersSideA, List<Player> seedersSideB, List<Player> unSeedersSideA, List<Player> unSeedersSideB){

        var matchesSideA = new ArrayList<Match>();
        var matchesSideB = new ArrayList<Match>();

        for(int i = 0; i < 8; i++){
            var match = new Match();
            match.setPlayer1(seedersSideA.get(i));
            match.setPlayer2(unSeedersSideA.get(i));
            match.setRound(1);
            match.setSide("A"+i);
            matchesSideA.add(match);
        }

        for(int i = 0; i < 8; i++){
            var match = new Match();
            match.setPlayer1(seedersSideB.get(i));
            match.setPlayer2(unSeedersSideB.get(i));
            match.setRound(1);
            match.setSide("B"+i);
            matchesSideB.add(match);
        }

        var allMatches = new ArrayList<Match>();
        allMatches.addAll(matchesSideA);
        allMatches.addAll(matchesSideB);

        return allMatches;
    }

}
