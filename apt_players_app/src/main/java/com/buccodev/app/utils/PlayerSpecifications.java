package com.buccodev.app.utils;

import com.buccodev.app.entities.Player;
import org.springframework.data.jpa.domain.Specification;

public class PlayerSpecifications {

    public static Specification<Player> hasNome(String nome) {
        return (root, query, cb) ->
                nome == null ? null : cb.like(cb.lower(root.get("name")), "%" + nome.toLowerCase() + "%");
    }
}
