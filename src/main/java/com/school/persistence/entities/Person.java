package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter @Setter
@Entity
@Table(name = "persons")
public class Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;
    @Column(nullable = false, length = 120)
    private String name;
    @Column(name = "social_name")
    private String socialName;
    @Column(length = 14, unique = true) // 000.000.000-00
    private String cpf;
    private String rg;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phone;
    @Column(nullable = false)
    private String email;
    @Column(name = "picture_key", length = 150)
    private String pictureKey; // path no S3 ou disco
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PersonAddress> addresses = new HashSet<>();

    public void addAddress(Address address, PersonAddress.TypeAddress type) {
        // desativa o anterior
        addresses.stream()
                .filter(pa -> pa.getTypeAddress() == type && pa.isCurrent())
                .forEach(pa -> pa.setCurrent(false));

        PersonAddress newPa = PersonAddress.builder()
                .person(this)
                .address(address)
                .typeAddress(type)
                .current(true)
                .createdAt(Instant.now())
                .build();
        addresses.add(newPa);
    }


    @Getter
    @AllArgsConstructor
    public enum Gender {

        MALE(1L),
        FEMALE(2L),
        OTHER(3L),
        NOT_INFORM(4L);

        final long roleId;
    }

}
