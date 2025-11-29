package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "course")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(unique = true, length = 20)
    private String code; // ADM, EM3A

    @Column(name = "total_hours")
    private Integer totalHours;

    @Column(columnDefinition = "TEXT")
    private String syllabus;

    private Integer minimumGrade; // nota mínima para aprovação

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Discipline> disciplines = new ArrayList<>();

    public void addDiscipline(Discipline discipline) {
        this.disciplines.add(discipline);
        discipline.setCourse(this);
    }
}