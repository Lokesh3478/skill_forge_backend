package org.msa.skillforge_backend.course.service;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.course.dto.content.ContentResponse;
import org.msa.skillforge_backend.course.dto.content.CreateContentRequest;
import org.msa.skillforge_backend.course.entity.Content;
import org.msa.skillforge_backend.course.entity.Phase;
import org.msa.skillforge_backend.course.repository.ContentRepository;
import org.msa.skillforge_backend.course.repository.PhaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;
    private final PhaseRepository phaseRepository;

    public ContentResponse createContent(
            CreateContentRequest createContentRequest
    ) {
        String phaseId = createContentRequest.phaseId();
        String contentName = createContentRequest.contentName();
        String contentUrl = createContentRequest.contentUrl();
        Integer durationInMinutes = createContentRequest.durationInMinutes();
        Phase phase = phaseRepository.findById(phaseId)
                .orElseThrow(() -> new NoSuchElementException("Phase not found"));

        if (contentRepository.existsByPhase_PhaseIdAndContentName(phaseId, contentName)) {
            throw new IllegalStateException(
                    "Content name already exists in this phase"
            );
        }

        Content content = Content.builder()
                .contentName(contentName)
                .contentUrl(contentUrl)
                .phase(phase)
                .build();

        return mapToResponse(contentRepository.save(content));
    }

    public ContentResponse getContentById(String contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new NoSuchElementException("Content not found"));

        return mapToResponse(content);
    }

    public List<ContentResponse> getContentsByPhase(String phaseId) {
        return contentRepository.findByPhase_PhaseId(phaseId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ContentResponse updateContent(
            String contentId,
            String contentName,
            String contentUrl
    ) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new NoSuchElementException("Content not found"));

        String phaseId = content.getPhase().getPhaseId();

        if (contentRepository.existsByPhase_PhaseIdAndContentNameAndContentIdNot(
                phaseId,
                contentName,
                contentId
        )) {
            throw new IllegalStateException(
                    "Content name already exists in this phase"
            );
        }

        content.setContentName(contentName);
        content.setContentUrl(contentUrl);

        return mapToResponse(contentRepository.save(content));
    }


    public void deleteContent(String contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new NoSuchElementException("Content not found"));

        contentRepository.delete(content);
    }


    private ContentResponse mapToResponse(Content content) {
        return new ContentResponse(
                content.getContentId(),
                content.getContentName(),
                content.getContentUrl(),
                content.getPhase().getPhaseId()
        );
    }
}
