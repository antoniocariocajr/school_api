package com.school.controllers.dto.room;

import com.school.persistence.entities.Room.TypeRoom;

public record RoomCreateDto(String code, Integer capacity, TypeRoom type) {

}
