package org.msa.skillforge_backend.course.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "content",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"phase_id", "content_name"}
                )
        }
)
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String contentId;

    @NotBlank
    private String contentUrl;

    @NotBlank
    @Column(name = "content_name")
    private String contentName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phase_id", nullable = false)
    private Phase phase;

}
