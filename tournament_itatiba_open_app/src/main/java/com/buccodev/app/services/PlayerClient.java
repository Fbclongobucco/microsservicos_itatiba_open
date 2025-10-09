package com.buccodev.app.services;

import com.buccodev.app.dtos.player.PlayerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name="ATP-PLAYERS-APP")
public interface PlayerClient {

    @GetMapping("/players/search")
    List<PlayerResponseDto> getPlayerByName(@RequestParam String name);

    @GetMapping("/players/{id}")
    PlayerResponseDto getPlayerById(@PathVariable UUID id);

}
