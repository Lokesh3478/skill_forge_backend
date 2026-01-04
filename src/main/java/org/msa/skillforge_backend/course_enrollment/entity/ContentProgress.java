package org.msa.skillforge_backend.course_enrollment.entity;
import jakarta.persistence.*;
import lombok.*;
import org.msa.skillforge_backend.course.entity.Content;

@Entity
@Table(
        name = "content_progress",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"enrollment_id", "content_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String contentProgressId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private CourseEnrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    private long secondsSpent;
}

