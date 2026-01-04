package org.msa.skillforge_backend.course_enrollment.dto.contentprogress;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentProgressDTO {

    private String contentId;
    private String contentTitle;

    private long durationInSeconds;
    private long secondsSpent;

    private double completionPercentage;
}

