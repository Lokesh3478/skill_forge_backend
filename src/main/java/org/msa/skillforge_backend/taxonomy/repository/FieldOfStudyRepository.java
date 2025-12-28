package org.msa.skillforge_backend.taxonomy.repository;

import org.msa.skillforge_backend.taxonomy.entity.FieldOfStudy;
import org.msa.skillforge_backend.taxonomy.entity.TaxonomyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FieldOfStudyRepository extends JpaRepository<FieldOfStudy, String> {

    boolean existsByNameIgnoreCase(String name);

    Optional<FieldOfStudy> findByNameIgnoreCase(String name);

    List<FieldOfStudy> findAllByStatus(TaxonomyStatus status);
}

