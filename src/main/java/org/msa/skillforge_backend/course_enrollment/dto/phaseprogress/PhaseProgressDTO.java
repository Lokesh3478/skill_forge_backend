package org.msa.skillforge_backend.course_enrollment.dto.phaseprogress;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhaseProgressDTO {

    private String phaseId;
    private String phaseName;

    private double completionPercentage;

    private boolean phaseTestPassed;
}

