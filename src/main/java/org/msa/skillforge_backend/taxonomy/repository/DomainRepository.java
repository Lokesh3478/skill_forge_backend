package org.msa.skillforge_backend.taxonomy.repository;
import org.msa.skillforge_backend.taxonomy.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DomainRepository extends JpaRepository<Domain, String> {

    boolean existsByField_FieldIdAndNameIgnoreCase(
            String fieldId,
            String name
    );

    List<Domain> findByField_FieldId(String fieldId);
}

