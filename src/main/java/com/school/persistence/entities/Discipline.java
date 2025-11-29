package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "discipline")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(unique = true, length = 30)
    private String code; // MAT-I, FIS-II

    @Column(name = "total_hours")
    private Integer totalHours;

    @Column(columnDefinition = "TEXT")
    private String syllabus;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SchoolClass> schoolClasses = new ArrayList<>();
}
