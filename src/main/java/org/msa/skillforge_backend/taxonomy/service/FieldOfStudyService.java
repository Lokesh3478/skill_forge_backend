package org.msa.skillforge_backend.taxonomy.service;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.taxonomy.dto.fieldOfStudy.FieldCreateRequest;
import org.msa.skillforge_backend.taxonomy.dto.fieldOfStudy.FieldSelectResponse;
import org.msa.skillforge_backend.taxonomy.dto.fieldOfStudy.FieldUpdateRequest;
import org.msa.skillforge_backend.taxonomy.dto.fieldOfStudy.FieldResponse;
import org.msa.skillforge_backend.taxonomy.entity.FieldOfStudy;
import org.msa.skillforge_backend.taxonomy.entity.TaxonomyStatus;
import org.msa.skillforge_backend.taxonomy.repository.FieldOfStudyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class FieldOfStudyService {

    private final FieldOfStudyRepository fieldRepository;

    /* ---------------- CREATE ---------------- */

    public FieldResponse createField(FieldCreateRequest request) {

        if (fieldRepository.existsByNameIgnoreCase(request.name())) {
            throw new IllegalStateException("Field of study already exists");
        }

        FieldOfStudy field = FieldOfStudy.builder()
                .name(request.name())
                .description(request.description())
                .status(TaxonomyStatus.ACTIVE)
                .build();

        return mapToResponse(fieldRepository.save(field));
    }

    /* ---------------- READ ---------------- */

    @Transactional(readOnly = true)
    public FieldResponse getFieldById(String fieldId) {

        FieldOfStudy field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new NoSuchElementException("Field not found"));

        return mapToResponse(field);
    }

    @Transactional(readOnly = true)
    public List<FieldResponse> getAllFields() {
        return fieldRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FieldSelectResponse> getActiveFieldsForSelection() {
        return fieldRepository.findAllByStatus(TaxonomyStatus.ACTIVE)
                .stream()
                .map(field -> new FieldSelectResponse(
                        field.getFieldId(),
                        field.getName()
                ))
                .toList();
    }

    /* ---------------- UPDATE ---------------- */

    public FieldResponse updateField(
            String fieldId,
            FieldUpdateRequest request
    ) {

        FieldOfStudy field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new NoSuchElementException("Field not found"));

        if (!field.getName().equalsIgnoreCase(request.name())
                && fieldRepository.existsByNameIgnoreCase(request.name())) {
            throw new IllegalStateException("Field name already exists");
        }

        field.setName(request.name());
        field.setDescription(request.description());

        if (request.status() != null) {
            field.setStatus(request.status());
        }

        return mapToResponse(field);
    }

    /* ---------------- DELETE (SOFT) ---------------- */

    public void deactivateField(String fieldId) {

        FieldOfStudy field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new NoSuchElementException("Field not found"));

        field.setStatus(TaxonomyStatus.DEPRECATED);
    }

    /* ---------------- MAPPER ---------------- */

    private FieldResponse mapToResponse(FieldOfStudy field) {
        return new FieldResponse(
                field.getFieldId(),
                field.getName(),
                field.getDescription(),
                field.getStatus()
        );
    }
}

