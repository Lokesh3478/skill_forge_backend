package org.msa.skillforge_backend.user.service;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.course.dto.courseDto.CourseSummary;
import org.msa.skillforge_backend.user.entity.Instructor;
import org.msa.skillforge_backend.user.repository.InstructorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;
    @Transactional(readOnly = true)
    public List<CourseSummary> getCoursesByInstructorId(String instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        return instructor.getCourseSet()
                .stream()
                .map(course -> new CourseSummary(
                        course.getCourseId(),
                        course.getCourseName()
                ))
                .toList();
    }
}
