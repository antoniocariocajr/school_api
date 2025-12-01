package com.school.controllers.dto.room;

import java.util.UUID;

import com.school.persistence.entities.Room.TypeRoom;

public record RoomDto(UUID id, String code, Integer capacity, TypeRoom type, Boolean active) {

}
