package org.msa.skillforge_backend.course_enrollment.repository;

import org.msa.skillforge_backend.course_enrollment.entity.CourseEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseEnrollmentRepository
        extends JpaRepository<CourseEnrollment, String> {

    Optional<CourseEnrollment> findByStudent_IdAndCourse_CourseId(
            String studentId,
            String courseId
    );

    List<CourseEnrollment> findByStudent_Id(String studentId);

    @Query("""
        SELECT ce
        FROM CourseEnrollment ce
        WHERE ce.student.id = :studentId
          AND ce.status <> 'DROPPED'
    """)
    List<CourseEnrollment> findActiveEnrollmentsByStudent(
            @Param("studentId") String studentId
    );
}

