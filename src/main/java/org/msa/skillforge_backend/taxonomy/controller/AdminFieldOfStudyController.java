package org.msa.skillforge_backend.taxonomy.controller;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.taxonomy.dto.fieldOfStudy.FieldCreateRequest;
import org.msa.skillforge_backend.taxonomy.dto.fieldOfStudy.FieldResponse;
import org.msa.skillforge_backend.taxonomy.dto.fieldOfStudy.FieldUpdateRequest;
import org.msa.skillforge_backend.taxonomy.service.FieldOfStudyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/taxonomy/fields")
@RequiredArgsConstructor
public class AdminFieldOfStudyController {

    private final FieldOfStudyService fieldService;

    /* ---------------- CREATE ---------------- */

    @PostMapping
    public FieldResponse createField(
            @RequestBody FieldCreateRequest request
    ) {
        return fieldService.createField(request);
    }

    /* ---------------- READ ---------------- */

    @GetMapping("/{fieldId}")
    public FieldResponse getFieldById(
            @PathVariable String fieldId
    ) {
        return fieldService.getFieldById(fieldId);
    }

    @GetMapping
    public List<FieldResponse> getAllFields() {
        return fieldService.getAllFields();
    }

    /* ---------------- UPDATE ---------------- */

    @PutMapping("/{fieldId}")
    public FieldResponse updateField(
            @PathVariable String fieldId,
            @RequestBody FieldUpdateRequest request
    ) {
        return fieldService.updateField(fieldId, request);
    }

    /* ---------------- DELETE (SOFT) ---------------- */

    @DeleteMapping("/{fieldId}")
    public void deactivateField(
            @PathVariable String fieldId
    ) {
        fieldService.deactivateField(fieldId);
    }
}
