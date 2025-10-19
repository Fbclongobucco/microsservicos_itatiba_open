package com.buccodev.app.dtos.player;

import java.util.List;
import java.util.UUID;

public record ListUuidsDto(List<UUID> uuids) {
    public static List<UUID> toList(ListUuidsDto dto){
        return dto.uuids();
    }
}
