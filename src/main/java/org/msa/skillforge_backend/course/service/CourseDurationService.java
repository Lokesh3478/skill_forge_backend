package org.msa.skillforge_backend.course.service;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.course.entity.Content;
import org.msa.skillforge_backend.course.entity.Course;
import org.msa.skillforge_backend.course.entity.Phase;
import org.msa.skillforge_backend.course.repository.CourseRepository;
import org.msa.skillforge_backend.course.repository.PhaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CourseDurationService {
    private final CourseRepository courseRepository;
    @Transactional(readOnly = true)
    public Integer findDuration(String courseId) {
        Integer duration = 0;
        Course course = courseRepository.findById(courseId).orElseThrow(
                ()-> new NoSuchElementException("course not found: " + courseId)
        );
        if(course.getPhases()!=null) {
            for (Phase phase : course.getPhases()) {
                if(phase.getContentsList()!=null) {
                for (Content content : phase.getContentsList()) {
                    duration += content.getDurationInMinutes();
                }
                if (phase.getTest() != null) {
                    duration += phase.getTest().getDurationInMinutes();
                }
                }
            }
            if (course.getFinalAssessment() != null) {
                duration += course.getFinalAssessment().getDurationInMinutes();
            }
        }
        return duration;
    }
}
