package com.buccodev.app.dtos.player;

import com.buccodev.app.entities.Player;

import java.util.UUID;

public record PlayerResponseDto(UUID id, String name, Integer ranking) {
    public static Player toEntity(PlayerResponseDto dto) {
        return new Player(dto.id(), dto.name(), dto.ranking(), false);
    }

    public static PlayerResponseDto fromEntity(Player player) {
        return new PlayerResponseDto(player.getId(), player.getName(), player.getRanking());
    }
}
