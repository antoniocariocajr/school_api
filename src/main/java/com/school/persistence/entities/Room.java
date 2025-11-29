package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "room")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 20)
    private String code; // 101, LAB-FIS, AUD-1

    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TypeRoom type = TypeRoom.REGULAR;

    public enum TypeRoom { REGULAR, LAB, COMPUTER, AUDITORIUM, GYM }

    private Boolean active = true;
}
