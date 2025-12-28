package org.msa.skillforge_backend.taxonomy.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.msa.skillforge_backend.taxonomy.dto.domain.CreateDomainRequest;
import org.msa.skillforge_backend.taxonomy.dto.domain.DomainResponse;
import org.msa.skillforge_backend.taxonomy.dto.domain.UpdateDomainRequest;
import org.msa.skillforge_backend.taxonomy.service.DomainService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/taxonomy/domains")
@RequiredArgsConstructor
public class AdminDomainController {

    private final DomainService domainService;

    /* ---------------- CREATE ---------------- */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DomainResponse createDomain(
            @Valid @RequestBody CreateDomainRequest request
    ) {
        return domainService.create(request);
    }

    /* ---------------- READ ---------------- */

    @GetMapping("/{domainId}")
    public DomainResponse getDomainById(
            @PathVariable String domainId
    ) {
        return domainService.getById(domainId);
    }

    @GetMapping("/field/{fieldId}")
    public List<DomainResponse> getDomainsByField(
            @PathVariable String fieldId
    ) {
        return domainService.getByField(fieldId);
    }

    /* ---------------- UPDATE ---------------- */

    @PutMapping("/{domainId}")
    public DomainResponse updateDomain(
            @PathVariable String domainId,
            @Valid @RequestBody UpdateDomainRequest request
    ) {
        return domainService.update(domainId, request);
    }

    /* ---------------- DELETE ---------------- */

    @DeleteMapping("/{domainId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDomain(
            @PathVariable String domainId
    ) {
        domainService.delete(domainId);
    }
}

