package com.buccodev.app.dtos.match;

import com.buccodev.app.entities.Match;

import java.util.UUID;

public record MatchRequestDto(Integer round, UUID playerOneId, UUID playerTwoId,
                              Long tournamentId, String side, String score) {
    public static Match toEntity(MatchRequestDto matchRequestDto) {
        var score = "";
        if (matchRequestDto.score() == null || matchRequestDto.score().isBlank()) {
           score = "";
        }
        else {
           score = matchRequestDto.score();
        }
       return new Match(null, matchRequestDto.round(), null, null,
               null, null, matchRequestDto.side(), score);
    }

    public static MatchRequestDto fromEntity(Match match) {
        return new MatchRequestDto(match.getRound(),
                match.getPlayer1().getId(), match.getPlayer2().getId(),
                match.getTournament().getId(),
                match.getSide(), match.getScore());
    }
}
