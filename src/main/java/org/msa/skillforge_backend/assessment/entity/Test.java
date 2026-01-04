package org.msa.skillforge_backend.assessment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.msa.skillforge_backend.course.entity.Phase;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name="test")
public class Test extends Assessment {
    private String testName;
    @ToString.Exclude
    @OneToOne(mappedBy = "test", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Phase phase;
}
