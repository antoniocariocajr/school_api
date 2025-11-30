package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "guardian")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Guardian {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /* OBRIGATÓRIO: responsável precisa acessar o sistema */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", unique = true, nullable = false)
    private Person person;

    @Column(name = "kinship", length = 30)
    private String kinship; // pai, mãe, avó, tutor, etc.

    private Boolean financialResponsible = false;

    private Boolean legalResponsible = false;

    /* bidirecional opcional – lista de alunos que é responsável */
    @ManyToMany(mappedBy = "legalGuardians")
    private Set<Student> students = new HashSet<>();

    private Boolean active = true;
}
