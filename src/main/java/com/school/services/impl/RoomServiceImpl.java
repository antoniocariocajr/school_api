package com.school.services.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.school.controllers.dto.room.RoomCreateDto;
import com.school.controllers.dto.room.RoomDto;
import com.school.controllers.dto.room.RoomUpdateDto;
import com.school.persistence.entities.Room;
import com.school.persistence.repositories.RoomRepository;
import com.school.services.RoomService;
import com.school.services.mapper.RoomMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomMapper roomMapper;
    private final RoomRepository roomRepository;

    @Override
    public RoomDto createRoom(RoomCreateDto roomCreateDto) {
        return roomMapper.toDto(roomRepository.save(roomMapper.toEntity(roomCreateDto)));
    }

    @Override
    public RoomDto updateRoom(UUID id, RoomUpdateDto roomUpdateDto) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
        roomMapper.updateEntity(room, roomUpdateDto);
        return roomMapper.toDto(roomRepository.save(room));
    }

    @Override
    public void deleteRoom(UUID id) {
        roomRepository.deleteById(id);
    }

    @Override
    public RoomDto getRoom(UUID id) {
        return roomMapper.toDto(roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found")));
    }

    @Override
    public Page<RoomDto> getAllRooms(Pageable pageable) {
        return roomRepository.findAll(pageable).map(roomMapper::toDto);
    }
}
