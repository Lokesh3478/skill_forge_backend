package org.msa.skillforge_backend.course.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.course.dto.PhaseCreateRequest;
import org.msa.skillforge_backend.course.dto.PhaseResponse;
import org.msa.skillforge_backend.course.dto.PhaseSummary;
import org.msa.skillforge_backend.course.service.PhaseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/phases")
@RequiredArgsConstructor
public class PhaseController {

    private final PhaseService phaseService;

    /* ---------------- READ ---------------- */

    @GetMapping("/{phaseId}")
    public PhaseResponse getPhaseById(@PathVariable String phaseId) {
        return phaseService.getPhaseById(phaseId);
    }

    @GetMapping("/course/{courseId}")
    public List<PhaseSummary> getPhasesByCourse(@PathVariable String courseId) {
        return phaseService.getPhasesByCourse(courseId);
    }

    /* ---------------- UPDATE ---------------- */

    @PutMapping("/{phaseId}")
    public PhaseResponse updatePhase(
            @PathVariable String phaseId,
            @RequestParam String title
    ) {
        return phaseService.updatePhase(phaseId, title);
    }

    /* ---------------- DELETE ---------------- */

    @DeleteMapping("/{phaseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhase(@PathVariable String phaseId) {
        phaseService.deletePhase(phaseId);
    }
}
