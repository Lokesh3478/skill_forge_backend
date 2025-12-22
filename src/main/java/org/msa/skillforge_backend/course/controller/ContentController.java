package org.msa.skillforge_backend.course.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.course.dto.ContentResponse;
import org.msa.skillforge_backend.course.service.ContentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contents")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @GetMapping("/{contentId}")
    public ContentResponse getContentById(@PathVariable String contentId) {
        return contentService.getContentById(contentId);
    }

    @GetMapping("/phase/{phaseId}")
    public List<ContentResponse> getContentsByPhase(
            @PathVariable String phaseId
    ) {
        return contentService.getContentsByPhase(phaseId);
    }

    @PutMapping("/{contentId}")
    public ContentResponse updateContent(
            @PathVariable String contentId,
            @RequestParam String contentName,
            @RequestParam String contentUrl
    ) {
        return contentService.updateContent(
                contentId,
                contentName,
                contentUrl
        );
    }

    @DeleteMapping("/{contentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContent(@PathVariable String contentId) {
        contentService.deleteContent(contentId);
    }
}
