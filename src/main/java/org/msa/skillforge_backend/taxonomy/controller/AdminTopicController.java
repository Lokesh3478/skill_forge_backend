package org.msa.skillforge_backend.taxonomy.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.taxonomy.dto.topic.TopicCreateRequest;
import org.msa.skillforge_backend.taxonomy.dto.topic.TopicResponse;
import org.msa.skillforge_backend.taxonomy.dto.topic.TopicUpdateRequest;
import org.msa.skillforge_backend.taxonomy.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/taxonomy/topics")
@RequiredArgsConstructor
public class AdminTopicController {

    private final TopicService topicService;

    /* ---------------- CREATE ---------------- */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TopicResponse createTopic(
            @Valid @RequestBody TopicCreateRequest request
    ) {
        return topicService.create(request);
    }

    /* ---------------- READ ---------------- */

    @GetMapping("/{topicId}")
    @ResponseStatus(HttpStatus.OK)
    public TopicResponse getTopicById(
            @PathVariable String topicId
    ) {
        return topicService.getById(topicId);
    }

    @GetMapping("domain/{domainId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TopicResponse> getAllTopicsByDomainId(
            @PathVariable String domainId
    ) {
        return topicService.getByDomain(domainId);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TopicResponse> getAllTopics() {
        return topicService.getAll();
    }

    /* ---------------- UPDATE ---------------- */

    @PutMapping("/{topicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public TopicResponse updateTopic(
            @PathVariable String topicId,
            @Valid @RequestBody TopicUpdateRequest request
    ) {
        return topicService.update(topicId, request);
    }

    /* ---------------- DELETE ---------------- */

    @DeleteMapping("/{topicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(
            @PathVariable String topicId
    ) {
        topicService.delete(topicId);
    }
}
