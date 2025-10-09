package com.buccodev.app.dtos;

import com.buccodev.app.entities.Player;

import java.util.UUID;

public record PlayerResponseDto(UUID id, String name, Integer ranking, String country, String forehand, String backhand,
                                String birthDate, Integer height, Double weight) {

    public static PlayerResponseDto fromEntity(Player entity) {
        return new PlayerResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getRanking(),
                entity.getCountry(),
                entity.getForehand(),
                entity.getBackhand(),
                entity.getBirthDate().toString(),
                entity.getHeight(),
                entity.getWeight()
        );
    }
}
