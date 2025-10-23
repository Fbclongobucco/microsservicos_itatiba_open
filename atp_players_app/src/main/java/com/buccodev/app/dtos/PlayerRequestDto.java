package com.buccodev.app.dtos;

import com.buccodev.app.entities.Player;

import java.time.LocalDate;

public record PlayerRequestDto(String name, Integer ranking, String country, String forehand, String backhand,
                               LocalDate birthDate, Integer height, Double weight, String urlPhoto) {
    public static Player toEntity(PlayerRequestDto dto) {
        return new Player(null, dto.name(), dto.ranking(), dto.country(), dto.forehand(), dto.backhand(),
                dto.birthDate(), dto.height(), dto.weight(), dto.urlPhoto());
    }
}
