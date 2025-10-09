package com.buccodev.app.dtos.match;

import com.buccodev.app.entities.Match;

import java.util.UUID;

public record MatchUpdateDto(Integer round,
                             String side,
                             String score,
                             UUID winnerId) {
}
