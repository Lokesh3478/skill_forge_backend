package org.msa.skillforge_backend.course.service;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.course.dto.courseDto.CourseCreateRequest;
import org.msa.skillforge_backend.course.dto.courseDto.CourseResponse;
import org.msa.skillforge_backend.course.dto.courseDto.CourseSummary;
import org.msa.skillforge_backend.course.dto.courseDto.CourseUpdateRequest;
import org.msa.skillforge_backend.course.dto.phase.PhaseSummary;
import org.msa.skillforge_backend.course.entity.Content;
import org.msa.skillforge_backend.course.entity.Course;
import org.msa.skillforge_backend.course.entity.Phase;
import org.msa.skillforge_backend.course.repository.CourseRepository;
import org.msa.skillforge_backend.course.repository.PhaseRepository;
import org.msa.skillforge_backend.taxonomy.entity.Topic;
import org.msa.skillforge_backend.taxonomy.repository.TopicRepository;
import org.msa.skillforge_backend.taxonomy.service.TopicService;
import org.msa.skillforge_backend.user.entity.Instructor;
import org.msa.skillforge_backend.user.repository.InstructorRepository;
import org.msa.skillforge_backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final CourseDurationService courseDurationService;

    /* ---------------- CREATE ---------------- */

    public CourseResponse createCourse(CourseCreateRequest request) {

        System.out.println("entered "+request);
        if(request.topicIds()!=null&&!request.topicIds().isEmpty()) {
            for(String topic : request.topicIds()) {
                if(!topicRepository.existsById(topic)) {
                    throw new NoSuchElementException("One or more topics not found");
                }
            }
            Set<Topic>fetchedIds = new HashSet<>(topicRepository.findAllById(request.topicIds()));
            System.out.println(fetchedIds+" "+topicRepository.findAllById(request.topicIds()));
            Course course = Course.builder()
                    .courseName(request.courseName())
                    .topics(fetchedIds)
                    .build();
            return mapToResponse(courseRepository.save(course));
        }

        throw new IllegalArgumentException("Topics cannot be empty");
    }

    /* ---------------- READ ---------------- */

    public CourseResponse getCourseById(String courseId) {
        return mapToResponse(
                courseRepository.findById(courseId)
                        .orElseThrow(() -> new NoSuchElementException("Course not found"))
        );
    }

    public List<CourseSummary> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(course -> new CourseSummary(
                        course.getCourseId(),
                        course.getCourseName(),
                        courseDurationService.findDuration(course.getCourseId())
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CourseSummary> getCoursesByTopics(String topicname){
        Topic topic = topicRepository.findByName(topicname
        ).orElseThrow(() -> new NoSuchElementException("Topic not found"));
        return topic.
                getCourses()
                .stream()
                .map(course -> new CourseSummary(
                        course.getCourseId(),
                        course.getCourseName(),
                        courseDurationService.findDuration(course.getCourseId())
                ))
                .toList();
    }


    /* ---------------- UPDATE ---------------- */

    public CourseResponse updateCourse(
            String courseId,
            CourseUpdateRequest request
    ) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        course.setCourseName(request.courseName());

        return mapToResponse(courseRepository.save(course));
    }

    /*-------------ADD INSTRUCTOR--------------------------*/

    @Transactional
    public void assignInstructorToCourse(
            String courseId,
            String instructorId
    ) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        Instructor instructor = instructorRepository.
                findById(
                        userRepository.findByEmail(
                                instructorId
                        ).orElseThrow(
                                ()-> new NoSuchElementException("Instructor Mail not found")
                        ).getId()
                ).orElseThrow(
                        ()-> new NoSuchElementException("Instructor not linked in user")
                );
        if (instructor.getCourseSet().contains(course)) {
            throw new IllegalStateException("Instructor already assigned to this course");
        }

        instructor.getCourseSet().add(course);
        course.getInstructorSet().add(instructor);
    }

    @Transactional
    public void updateCourseTopics(String courseId, Set<String> topicIds) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        Set<Topic> topics = new HashSet<>(topicRepository.findAllById(topicIds));

        if (topics.size() != topicIds.size()) {
            throw new IllegalArgumentException("One or more topics not found");
        }

        course.setTopics(topics);
    }

    /* ---------------- DELETE ---------------- */

    public void deleteCourse(String courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        courseRepository.delete(course);
    }


    /* ---------------- MAPPER ---------------- */

    private CourseResponse mapToResponse(Course course) {

        List<PhaseSummary> phaseSummaries =
                course.getPhases() == null
                        ? List.of()
                        : course.getPhases()
                        .stream()
                        .map(phase -> new PhaseSummary(
                                phase.getPhaseId(),
                                phase.getTitle()
                        ))
                        .toList();

        return new CourseResponse(
                course.getCourseId(),
                course.getCourseName(),
                phaseSummaries,
                course.getFinalAssessment() != null
                        ? course.getFinalAssessment().getAssessmentId()
                        : null,
                courseDurationService.findDuration(course.getCourseId())
        );
    }
}
