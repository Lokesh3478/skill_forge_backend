package org.msa.skillforge_backend.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="test")
public class Test extends Assessment {
    private String testName;
    @OneToOne(mappedBy = "test", fetch = FetchType.LAZY)
    private Phase phase;

}
