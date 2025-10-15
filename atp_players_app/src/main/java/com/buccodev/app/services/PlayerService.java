package com.buccodev.app.services;

import com.buccodev.app.dtos.PlayerRequestDto;
import com.buccodev.app.dtos.PlayerResponseDto;
import com.buccodev.app.entities.Player;
import com.buccodev.app.repository.PlayerRepository;
import com.buccodev.app.utils.PlayerSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    private static final Logger logger = Logger.getLogger(Player.class.getName());

    @Autowired
    private WebServerApplicationContext webServerAppCxt;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerResponseDto createPlayer(PlayerRequestDto playerDto) {
        var playerEntity = PlayerRequestDto.toEntity(playerDto);
        var savedPlayer = playerRepository.save(playerEntity);
        return PlayerResponseDto.fromEntity(savedPlayer);
    }

    public List<PlayerResponseDto> createManyPlayers(List<PlayerRequestDto> playerDtos) {
        var playerEntities = playerDtos.stream()
                .map(PlayerRequestDto::toEntity)
                .toList();
        var savedPlayers = playerRepository.saveAll(playerEntities);
        return savedPlayers.stream()
                .map(PlayerResponseDto::fromEntity)
                .toList();
    }

    public PlayerResponseDto getPlayerById(UUID id) {
        var player = playerRepository.findById(id)
                .orElse(new Player(null, "", 0, "",
                        "", "", LocalDate.now(), 0, 0.0) );

        return PlayerResponseDto.fromEntity(player);
    }

    public void deletePlayer(UUID id) {
        if (!playerRepository.existsById(id)) {
            throw new RuntimeException("Player not found");
        }
        playerRepository.deleteById(id);
    }

    public PlayerResponseDto updatePlayer(UUID id, PlayerRequestDto playerDto) {
        var existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        existingPlayer.setName(playerDto.name());
        existingPlayer.setRanking(playerDto.ranking());
        existingPlayer.setCountry(playerDto.country());
        existingPlayer.setForehand(playerDto.forehand());
        existingPlayer.setBackhand(playerDto.backhand());
        existingPlayer.setBirthDate(playerDto.birthDate());
        existingPlayer.setHeight(playerDto.height());
        existingPlayer.setWeight(playerDto.weight());

        var updatedPlayer = playerRepository.save(existingPlayer);
        return PlayerResponseDto.fromEntity(updatedPlayer);
    }
    public List<PlayerResponseDto> getPlayerByName(String name){
        var prayer = playerRepository.findAll(PlayerSpecifications.hasNome(name));
        return prayer.stream().map(PlayerResponseDto::fromEntity).toList();
    }

}
