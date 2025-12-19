package org.msa.skillforge_backend.course.repository;

import org.msa.skillforge_backend.course.entity.Phase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhaseRepository extends JpaRepository<Phase, String> {

    List<Phase> findByCourse_CourseId(String courseId);

    boolean existsByCourse_CourseIdAndTitle(String courseId, String title);
}
