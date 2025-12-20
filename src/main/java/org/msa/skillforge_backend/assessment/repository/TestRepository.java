package org.msa.skillforge_backend.assessment.repository;

import org.msa.skillforge_backend.assessment.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, String> {


    boolean existsByPhase_PhaseId(String phaseId);
}
