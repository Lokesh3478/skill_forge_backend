package org.msa.skillforge_backend.taxonomy.repository;
import org.msa.skillforge_backend.taxonomy.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, String> {
    @Query("select t from Topic t where upper(t.name) = upper(?1)")
    Optional<Topic> findByName(String name);

    boolean existsByDomain_DomainIdAndNameIgnoreCase(
            String domainId,
            String name
    );

    List<Topic> findByDomain_DomainId(String domainId);
}

