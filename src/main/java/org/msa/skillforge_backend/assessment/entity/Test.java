package org.msa.skillforge_backend.assessment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.msa.skillforge_backend.course.entity.Phase;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name="test")
public class Test extends Assessment {
    private String testName;
    @OneToOne(mappedBy = "test", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Phase phase;
}
