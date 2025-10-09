package com.buccodev.app.repository;

import com.buccodev.app.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID>, JpaSpecificationExecutor<Player> {
}
