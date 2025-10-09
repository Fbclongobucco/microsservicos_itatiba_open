package com.buccodev.app.dtos.tournament;

import java.time.LocalDate;

public record TournamentRequestDto(String name, LocalDate startDate,
                                   LocalDate endDate, String location) {
}
