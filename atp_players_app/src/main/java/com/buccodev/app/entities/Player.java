package com.buccodev.app.entities;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

@Entity
@Table(name = "players_tb")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private Integer ranking;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String forehand;
    @Column(nullable = false)
    private String backhand;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private Integer height;
    @Column(nullable = false)
    private Double weight;
    @Column(columnDefinition = "TEXT")
    private String urlPhoto;


    public Player(){
    }

    public Player(UUID id, String name, Integer ranking, String country, String forehand, String backhand,
            LocalDate birthDate, Integer height, Double weight, String urlPhoto) {
        this.id = id;
        this.name = name;
        this.ranking = ranking;
        this.country = country;
        this.forehand = forehand;
        this.backhand = backhand;
        this.birthDate = birthDate;
        this.height = height;
        this.weight = weight;
        this.urlPhoto = urlPhoto;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getForehand() {
        return forehand;
    }

    public void setForehand(String forehand) {
        this.forehand = forehand;
    }

    public String getBackhand() {
        return backhand;
    }

    public void setBackhand(String backhand) {
        this.backhand = backhand;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
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
