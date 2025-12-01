package com.school.services.mapper;

import org.springframework.stereotype.Component;

import com.school.controllers.dto.room.RoomCreateDto;
import com.school.controllers.dto.room.RoomDto;
import com.school.controllers.dto.room.RoomUpdateDto;
import com.school.persistence.entities.Room;

@Component
public class RoomMapper {

    public RoomDto toDto(Room room) {
        return new RoomDto(
                room.getId(),
                room.getCode(),
                room.getCapacity(),
                room.getType(),
                room.getActive());
    }

    public Room toEntity(RoomDto roomDto) {
        return Room.builder()
                .id(roomDto.id())
                .code(roomDto.code())
                .capacity(roomDto.capacity())
                .type(roomDto.type())
                .active(roomDto.active())
                .build();
    }

    public Room toEntity(RoomCreateDto roomCreateDto) {
        return Room.builder()
                .code(roomCreateDto.code())
                .capacity(roomCreateDto.capacity())
                .type(roomCreateDto.type())
                .build();
    }

    public void updateEntity(Room room, RoomUpdateDto roomUpdateDto) {
        room.setCode(roomUpdateDto.code());
        room.setCapacity(roomUpdateDto.capacity());
        room.setType(roomUpdateDto.type());
        room.setActive(roomUpdateDto.active());
    }
}
