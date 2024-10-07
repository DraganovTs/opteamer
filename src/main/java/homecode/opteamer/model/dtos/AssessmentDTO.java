package homecode.opteamer.model.dtos;

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
public class AssessmentDTO {
    @NotNull(message = "Team member ID cannot be null")
    private Long teamMemberId;

    @NotNull(message = "Pre-operative assessment ID cannot be null")
    private String preOpAssessmentId;

    @NotNull(message = "Patient ID cannot be null")
    private Long patientId;

    private TeamMemberDTO teamMemberDTO;
    private PreOperativeAssessmentDTO preOperativeAssessmentDTO;
    private PatientDTO patientDTO;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDateTime startDate;
}
