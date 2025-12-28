package org.msa.skillforge_backend.taxonomy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "domain",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"field_id", "name"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String domainId;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "field_id")
    private FieldOfStudy field;

    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL)
    private List<Topic> topics = new ArrayList<>();
}
