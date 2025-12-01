package com.school.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.school.controllers.dto.room.RoomCreateDto;
import com.school.controllers.dto.room.RoomDto;
import com.school.controllers.dto.room.RoomUpdateDto;

public interface RoomService {

    public RoomDto createRoom(RoomCreateDto roomCreateDto);

    public RoomDto updateRoom(UUID id, RoomUpdateDto roomUpdateDto);

    public void deleteRoom(UUID id);

    public RoomDto getRoom(UUID id);

    public Page<RoomDto> getAllRooms(Pageable pageable);
}
