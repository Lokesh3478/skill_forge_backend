package org.msa.skillforge_backend.taxonomy.dto.topic;

public record TopicResponse(
        String topicId,
        String name,
        String domainId,
        String domainName,
        String fieldId,
        String fieldName
) {}

