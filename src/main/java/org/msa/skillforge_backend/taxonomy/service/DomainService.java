package org.msa.skillforge_backend.taxonomy.service;

import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.taxonomy.dto.domain.CreateDomainRequest;
import org.msa.skillforge_backend.taxonomy.dto.domain.DomainResponse;
import org.msa.skillforge_backend.taxonomy.dto.domain.UpdateDomainRequest;
import org.msa.skillforge_backend.taxonomy.entity.Domain;
import org.msa.skillforge_backend.taxonomy.entity.FieldOfStudy;
import org.msa.skillforge_backend.taxonomy.repository.DomainRepository;
import org.msa.skillforge_backend.taxonomy.repository.FieldOfStudyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class DomainService {

    private final DomainRepository domainRepository;
    private final FieldOfStudyRepository fieldOfStudyRepository;

    /* ---------------- CREATE ---------------- */

    public DomainResponse create(CreateDomainRequest request) {

        if (domainRepository.existsByField_FieldIdAndNameIgnoreCase(
                request.fieldId(),
                request.name()
        )) {
            throw new IllegalStateException(
                    "Domain already exists in this field"
            );
        }

        FieldOfStudy field = fieldOfStudyRepository.findById(request.fieldId())
                .orElseThrow(() ->
                        new NoSuchElementException("Field of study not found")
                );

        Domain domain = new Domain();
        domain.setName(request.name());
        domain.setField(field);

        Domain saved = domainRepository.save(domain);

        return mapToResponse(saved);
    }

    /* ---------------- READ ---------------- */

    @Transactional(readOnly = true)
    public DomainResponse getById(String domainId) {
        return mapToResponse(
                domainRepository.findById(domainId)
                        .orElseThrow(() ->
                                new NoSuchElementException("Domain not found")
                        )
        );
    }

    @Transactional(readOnly = true)
    public List<DomainResponse> getByField(String fieldId) {

        if (!fieldOfStudyRepository.existsById(fieldId)) {
            throw new NoSuchElementException("Field of study not found");
        }

        return domainRepository.findByField_FieldId(fieldId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<DomainResponse>getAll(){
        return domainRepository.findAll()
                .stream().map(
                        domain -> {
                            return mapToResponse(domain);
                        }
                ).toList();
    }

    /* ---------------- UPDATE ---------------- */

    public DomainResponse update(
            String domainId,
            UpdateDomainRequest request
    ) {

        Domain domain = domainRepository.findById(domainId)
                .orElseThrow(() ->
                        new NoSuchElementException("Domain not found")
                );

        if (domainRepository.existsByField_FieldIdAndNameIgnoreCase(
                domain.getField().getFieldId(),
                request.name()
        )) {
            throw new IllegalStateException(
                    "Domain with same name already exists in this field"
            );
        }

        domain.setName(request.name());

        return mapToResponse(domain);
    }

    /* ---------------- DELETE ---------------- */

    public void delete(String domainId) {

        Domain domain = domainRepository.findById(domainId)
                .orElseThrow(() ->
                        new NoSuchElementException("Domain not found")
                );

        domainRepository.delete(domain);
    }

    /* ---------------- MAPPER ---------------- */

    private DomainResponse mapToResponse(Domain domain) {
        return new DomainResponse(
                domain.getDomainId(),
                domain.getName(),
                domain.getField().getFieldId(),
                domain.getField().getName()
        );
    }
}
