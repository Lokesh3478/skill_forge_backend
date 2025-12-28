package org.msa.skillforge_backend.taxonomy.service;
import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.taxonomy.dto.topic.TopicCreateRequest;
import org.msa.skillforge_backend.taxonomy.dto.topic.TopicResponse;
import org.msa.skillforge_backend.taxonomy.dto.topic.TopicUpdateRequest;
import org.msa.skillforge_backend.taxonomy.entity.Domain;
import org.msa.skillforge_backend.taxonomy.entity.Topic;
import org.msa.skillforge_backend.taxonomy.repository.DomainRepository;
import org.msa.skillforge_backend.taxonomy.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class TopicService {

    private final TopicRepository topicRepository;
    private final DomainRepository domainRepository;

    /* ---------------- CREATE ---------------- */

    public TopicResponse create(TopicCreateRequest request) {

        if (topicRepository.existsByDomain_DomainIdAndNameIgnoreCase(
                request.domainId(),
                request.name()
        )) {
            throw new IllegalStateException(
                    "Topic already exists in this domain"
            );
        }

        Domain domain = domainRepository.findById(request.domainId())
                .orElseThrow(() ->
                        new NoSuchElementException("Domain not found")
                );

        Topic topic = new Topic();
        topic.setName(request.name());
        topic.setDomain(domain);

        Topic saved = topicRepository.save(topic);

        return mapToResponse(saved);
    }

    /* ---------------- READ ---------------- */

    @Transactional(readOnly = true)
    public TopicResponse getById(String topicId) {
        return mapToResponse(
                topicRepository.findById(topicId)
                        .orElseThrow(() ->
                                new NoSuchElementException("Topic not found")
                        )
        );
    }

    @Transactional(readOnly = true)
    public List<TopicResponse> getByDomain(String domainId) {

        if (!domainRepository.existsById(domainId)) {
            throw new NoSuchElementException("Domain not found");
        }

        return topicRepository.findByDomain_DomainId(domainId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public List<TopicResponse>getAll(){
        return topicRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /* ---------------- UPDATE ---------------- */

    public TopicResponse update(
            String topicId,
            TopicUpdateRequest request
    ) {

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() ->
                        new NoSuchElementException("Topic not found")
                );

        if (topicRepository.existsByDomain_DomainIdAndNameIgnoreCase(
                topic.getDomain().getDomainId(),
                request.name()
        )) {
            throw new IllegalStateException(
                    "Topic with same name already exists in this domain"
            );
        }

        topic.setName(request.name());

        return mapToResponse(topic);
    }

    /* ---------------- DELETE ---------------- */

    public void delete(String topicId) {

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() ->
                        new NoSuchElementException("Topic not found")
                );

        topicRepository.delete(topic);
    }

    /* ---------------- MAPPER ---------------- */

    private TopicResponse mapToResponse(Topic topic) {

        Domain domain = topic.getDomain();

        return new TopicResponse(
                topic.getTopicId(),
                topic.getName(),
                domain.getDomainId(),
                domain.getName(),
                domain.getField().getFieldId(),
                domain.getField().getName()
        );
    }
}

