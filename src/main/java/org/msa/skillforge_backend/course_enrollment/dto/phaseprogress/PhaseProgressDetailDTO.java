package org.msa.skillforge_backend.course_enrollment.dto.phaseprogress;

import lombok.*;
import org.msa.skillforge_backend.course_enrollment.dto.contentprogress.ContentProgressDTO;
import org.msa.skillforge_backend.course_enrollment.dto.phasetestattempt.PhaseTestAttemptDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhaseProgressDetailDTO {

    private String phaseProgressId;

    private String phaseId;
    private String phaseName;

    private double completionPercentage;

    private boolean phaseTestPassed;

    private List<ContentProgressDTO> contents;

    private List<PhaseTestAttemptDTO> testAttempts;
}

