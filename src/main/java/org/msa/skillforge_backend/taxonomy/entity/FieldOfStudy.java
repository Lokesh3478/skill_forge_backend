package org.msa.skillforge_backend.taxonomy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "field_of_study")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldOfStudy {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String fieldId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Domain> domains = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaxonomyStatus status;
}

