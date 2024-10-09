package homecode.opteamer.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(
        name = "Assessment",
        description = "Schema to hold assessment information for team members"
)
public class AssessmentDTO {

    @Schema(
            description = "Team member ID associated with this assessment",
            example = "1"
    )
    @NotNull(message = "Team member ID cannot be null")
    private Long teamMemberId;

    @Schema(
            description = "Pre-operative assessment ID associated with this assessment",
            example = "1"
    )
    @NotNull(message = "Pre-operative assessment ID cannot be null")
    private String preOpAssessmentId;

    @Schema(
            description = "Patient ID associated with this assessment",
            example = "1"
    )
    @NotNull(message = "Patient ID cannot be null")
    private Long patientId;

    @Schema(
            description = "Details of the team member associated with this assessment"
    )
    private TeamMemberDTO teamMemberDTO;

    @Schema(
            description = "Details of the pre-operative assessment associated with this assessment"
    )
    private PreOperativeAssessmentDTO preOperativeAssessmentDTO;

    @Schema(
            description = "Details of the patient associated with this assessment"
    )
    private PatientDTO patientDTO;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be in the present or future")
    @Schema(
            description = "The start date of the assessment",
            example = "2024-10-09T10:00:00"
    )
    private LocalDateTime startDate;
}
