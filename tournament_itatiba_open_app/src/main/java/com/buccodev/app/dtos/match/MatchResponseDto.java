package com.buccodev.app.dtos.match;

import com.buccodev.app.dtos.player.PlayerResponseDto;
import com.buccodev.app.dtos.tournament.TournamentResponseDto;
import com.buccodev.app.entities.Match;

import java.util.UUID;

public record MatchResponseDto(
        Long id,
        Integer round,
        PlayerResponseDto playerOneResponseDto,
        PlayerResponseDto playerTwoResponseDto,
        String winner,
        String side,
        String score
) {
    public static Match fromEntity(MatchRequestDto matchRequestDto) {
        return new Match(null, matchRequestDto.round(), null, null,
                null, null, matchRequestDto.side(), matchRequestDto.score());
    }

    public static MatchResponseDto fromEntity(Match match) {
        var playerOneDto = PlayerResponseDto.fromEntity(match.getPlayer1());
        var playerTwoDto = PlayerResponseDto.fromEntity(match.getPlayer2());
        var winner = match.getWinner() != null ? match.getWinner().getName() : "";
        var score = match.getScore() != null ? match.getScore() : "";
        return new MatchResponseDto(
                match.getId(),
                match.getRound(),
                playerOneDto,
                playerTwoDto,
                winner,
                match.getSide(),
                score
        );
    }

}
