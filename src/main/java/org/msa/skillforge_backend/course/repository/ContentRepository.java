package org.msa.skillforge_backend.course.repository;

import org.msa.skillforge_backend.course.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, String> {

    boolean existsByPhase_PhaseIdAndContentName(String phaseId, String contentName);

    boolean existsByPhase_PhaseIdAndContentNameAndContentIdNot(
            String phaseId,
            String contentName,
            String contentId
    );

    List<Content> findByPhase_PhaseId(String phaseId);
}
