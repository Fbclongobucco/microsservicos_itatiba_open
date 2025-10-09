package com.buccodev.app.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "matches_tb")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer round;
    @ManyToOne
    @JoinColumn(name = "player1_id", nullable = false)
    private Player player1;
    @ManyToOne
    @JoinColumn(name = "player2_id", nullable = false)
    private Player player2;
    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Player winner;
    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;
    @Column(nullable = false)
    private String side;
    private String score;

    public Match(){
    }

    public Match(Long id, Integer round, Player player1, Player player2, Player winner,
                 Tournament tournament, String side, String score) {
        this.id = id;
        this.round = round;
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
        this.tournament = tournament;
        this.side = side;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(id, match.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
