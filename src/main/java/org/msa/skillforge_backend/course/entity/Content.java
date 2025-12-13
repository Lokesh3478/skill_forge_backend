package org.msa.skillforge_backend.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="content")
public class Content {
    @Id
            @GeneratedValue(strategy = GenerationType.UUID)
    String contentId;
    String contentUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phase_id")
    private Phase phase;

}
