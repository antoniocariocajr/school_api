package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TypeEvent typeEvent = TypeEvent.GENERAL;

    public enum TypeEvent {
        EXAM, MEETING, HOLIDAY, VACATION, GENERAL
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_term_id")
    private SchoolTerm schoolTerm;

    @Builder.Default
    private Boolean active = true;
}