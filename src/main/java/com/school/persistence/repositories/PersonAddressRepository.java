package com.school.persistence.repositories;

import com.school.persistence.entities.PersonAddress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonAddressRepository extends JpaRepository<PersonAddress, UUID> {

    @Query("select pa from PersonAddress pa " +
            "where pa.person.id = :personId " +
            "and pa.typeAddress = :typeAddress " +
            "and pa.current = true")
    Optional<PersonAddress> findCurrentByPersonAndTypeAddress(UUID personId, PersonAddress.TypeAddress typeAddress);

    @org.springframework.data.jpa.repository.Modifying
    @Query("update PersonAddress pa set pa.current = false " +
            "where pa.person.id = :personId " +
            "and pa.typeAddress = :typeAddress " +
            "and pa.current = true")
    void deleteCurrentByPersonAndTypeAddress(UUID personId, PersonAddress.TypeAddress typeAddress);

    List<PersonAddress> findByPersonId(UUID personId);

}