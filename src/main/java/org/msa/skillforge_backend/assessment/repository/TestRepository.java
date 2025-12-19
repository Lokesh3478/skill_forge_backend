package org.msa.skillforge_backend.assessment.repository;

import org.msa.skillforge_backend.assessment.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, String> {

    Optional<Test> findByPhase_PhaseId(String phaseId);

    boolean existsByPhase_PhaseId(String phaseId);
}
