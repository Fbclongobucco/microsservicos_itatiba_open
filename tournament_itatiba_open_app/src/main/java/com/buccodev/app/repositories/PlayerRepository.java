package com.buccodev.app.repositories;

import com.buccodev.app.entities.Player;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {

    List<Player> findByIsSeed(Boolean seed);
    List<Player> findByTournamentId(Long tournamentId);
    Boolean existsByTournamentId(Long tournamentId);
}
