package com.buccodev.app.services;

import com.buccodev.app.dtos.player.PlayerResponseDto;
import com.buccodev.app.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerService {

    private final PlayerRepository repository;
    private final PlayerClient client;

    public PlayerService(PlayerRepository repository, PlayerClient client) {
        this.repository = repository;
        this.client = client;
    }

    public List<PlayerResponseDto> getAtpPlayers(String name){
        return client.getPlayerByName(name);
    }

    public void addPlayer(UUID id){
        if(repository.findAll().size() >= 32){
            throw new RuntimeException("Max players reached");
        }
        var playerDto = client.getPlayerById(id);
        if(playerDto == null){
            throw new RuntimeException("Player not found");
        }

        if(repository.existsById(id)){
            throw new RuntimeException("Player already exists");
        }

        var player = PlayerResponseDto.toEntity(playerDto);
        repository.save(player);
    }

    public PlayerResponseDto getPlayerTournamentById(UUID id){
        var player = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        return PlayerResponseDto.fromEntity(player);
    }

    public List<PlayerResponseDto> getAllPlayersOfTournament(){
        var players = repository.findAll();
        return players.stream().map(PlayerResponseDto::fromEntity).toList();
    }

    public void removePlayer(UUID id){
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
        throw new RuntimeException("Player not found");
    }

    public void updatePlayer(UUID id){
        var playerDto = client.getPlayerById(id);
        if(playerDto != null && repository.existsById(id)){
            var player = PlayerResponseDto.toEntity(playerDto);
            repository.save(player);
        }
        throw new RuntimeException("Player not found");
    }

    public void updateSeed(UUID id, Boolean newSeed){

        var ifMaxSeeds = repository.findByIsSeed(true);
        if(newSeed && ifMaxSeeds.size() >= 8){
            throw new RuntimeException("Max seeds reached");
        }

        var player = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        player.setSeed(newSeed);
        repository.save(player);
    }
}
