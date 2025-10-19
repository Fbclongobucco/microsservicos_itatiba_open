package com.buccodev.app.entities;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "players_tb")
public class Player {

    @Id
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(unique = true, nullable = false)
    private Integer ranking;
    @ManyToOne
    private Tournament tournament;

    private Boolean isSeed;

    public Player() {
    }

    public Player(UUID id, String name, Integer ranking, Boolean isSeed) {
        this.id = id;
        this.name = name;
        this.ranking = ranking;
        this.isSeed = isSeed;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Boolean getSeed() {
        return isSeed;
    }

    public void setSeed(Boolean seed) {
        isSeed = seed;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
