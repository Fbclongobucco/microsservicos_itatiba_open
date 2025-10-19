package com.buccodev.app.services;

import com.buccodev.app.dtos.player.PlayerResponseDto;
import com.buccodev.app.entities.Player;
import com.buccodev.app.repositories.PlayerRepository;
import com.buccodev.app.repositories.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerClient client;
    private final TournamentRepository tournamentRepository;

    public PlayerService(PlayerRepository playerRepository, PlayerClient client, TournamentRepository tournamentRepository) {
        this.playerRepository = playerRepository;
        this.client = client;
        this.tournamentRepository = tournamentRepository;
    }

    public List<PlayerResponseDto> getAtpPlayers(String name){
        return client.getPlayerByName(name);
    }


    public void addPlayer(UUID id, Long tournamentId){

        var tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));

        if(tournament.getPlayers().size() >= 32){
            throw new RuntimeException("Max players reached");
        }

        var playerDto = client.getPlayerById(id);

        if(playerDto == null){
            throw new RuntimeException("Player not found");
        }

        if(playerRepository.existsByTournamentId(tournamentId)){
            throw new RuntimeException("Player already exists");
        }

        var player = PlayerResponseDto.toEntity(playerDto);

        player.setTournament(tournament);

        tournament.getPlayers().add(player);

        tournamentRepository.save(tournament);

        playerRepository.save(player);
    }

    public void addManyPlayers(List<UUID> uuids, Long tournamentId){
      
        var tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));

        if(tournament.getPlayers().size() >= 32){
            throw new RuntimeException("Max players reached");
        }
        
        var players = new ArrayList<Player>();
        
        for(UUID id : uuids){
            var playerDto = client.getPlayerById(id);
            if(playerDto == null){
                throw new RuntimeException("Player not found");
            }
            var player = PlayerResponseDto.toEntity(playerDto);
            player.setTournament(tournament);
            players.add(player);
        }
        
        tournament.getPlayers().addAll(players);
        tournamentRepository.save(tournament);
        playerRepository.saveAll(players);
        
        
    }

    public PlayerResponseDto getPlayerTournamentById(UUID id){
        var player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        return PlayerResponseDto.fromEntity(player);
    }

    public List<PlayerResponseDto> getAllPlayersOfTournament(){
        var players = playerRepository.findAll();
        return players.stream().map(PlayerResponseDto::fromEntity).toList();
    }

    public void removePlayer(UUID id){
        if(playerRepository.existsById(id)){
            playerRepository.deleteById(id);
        }
        throw new RuntimeException("Player not found");
    }

    public void updatePlayer(UUID id){
        var playerDto = client.getPlayerById(id);
        if(playerDto != null && playerRepository.existsById(id)){
            var player = PlayerResponseDto.toEntity(playerDto);
            playerRepository.save(player);
        }
        throw new RuntimeException("Player not found");
    }

    public void updateSeed(UUID id, Boolean newSeed){

        var ifMaxSeeds = playerRepository.findByIsSeed(true);
        if(newSeed && ifMaxSeeds.size() >= 8){
            throw new RuntimeException("Max seeds reached");
        }

        var player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        player.setSeed(newSeed);
        playerRepository.save(player);
    }
}
