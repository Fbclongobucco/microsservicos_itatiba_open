package com.buccodev.app.repositories;

import com.buccodev.app.entities.Match;
import com.buccodev.app.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByPlayer1(Player player);
    List<Match> findByPlayer2(Player player);

    Integer countByRoundAndTournamentId(Integer round, Long tournamentId);
}
