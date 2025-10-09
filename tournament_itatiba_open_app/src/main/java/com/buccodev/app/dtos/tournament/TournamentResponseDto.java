package com.buccodev.app.dtos.tournament;

import com.buccodev.app.dtos.match.MatchResponseDto;
import com.buccodev.app.entities.Tournament;

import java.time.LocalDate;
import java.util.List;

public record TournamentResponseDto(Long id, String name, LocalDate startDate,
                                    LocalDate endDate, String location, List<MatchResponseDto> matches) {
    public static Tournament toEntity(TournamentRequestDto tournamentRequestDto) {
        return new Tournament(null, tournamentRequestDto.name(), tournamentRequestDto.startDate(),
                tournamentRequestDto.endDate(), tournamentRequestDto.location(), null);
    }

    public static TournamentResponseDto fromEntity(Tournament tournament) {
        var matches = tournament.getMatches().stream()
                .map(MatchResponseDto::fromEntity)
                .toList();
        return new TournamentResponseDto(tournament.getId(), tournament.getName(),
                tournament.getStartDate(), tournament.getEndDate(),
                tournament.getLocation(), matches);
    }
}
