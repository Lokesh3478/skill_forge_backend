package org.msa.skillforge_backend.taxonomy.controller;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.course.entity.Course;
import org.msa.skillforge_backend.course.repository.CourseRepository;
import org.msa.skillforge_backend.taxonomy.dto.domain.DomainResponse;
import org.msa.skillforge_backend.taxonomy.dto.fieldOfStudy.FieldResponse;
import org.msa.skillforge_backend.taxonomy.dto.topic.TopicResponse;
import org.msa.skillforge_backend.taxonomy.entity.FieldOfStudy;
import org.msa.skillforge_backend.taxonomy.repository.DomainRepository;
import org.msa.skillforge_backend.taxonomy.repository.FieldOfStudyRepository;
import org.msa.skillforge_backend.taxonomy.repository.TopicRepository;
import org.msa.skillforge_backend.taxonomy.service.DomainService;
import org.msa.skillforge_backend.taxonomy.service.FieldOfStudyService;
import org.msa.skillforge_backend.taxonomy.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/taxonomy")
@RequiredArgsConstructor
public class TaxonomyController {
    private final TopicService topicService;
    private final DomainService domainService;
    private final FieldOfStudyService fieldOfStudyService;

    @GetMapping("/topics")
    @ResponseStatus(HttpStatus.OK)
    public List<TopicResponse> getAllTopics() {
        return topicService.getAll();
    }

    @GetMapping("/domains")
    public List<DomainResponse> getAllDomains() {
        return domainService.getAll();
    }

    @GetMapping("/field_of_studies")
    public List<FieldResponse> getAllFieldOfStudies() {
        return fieldOfStudyService.getAllFields();
    }
}
