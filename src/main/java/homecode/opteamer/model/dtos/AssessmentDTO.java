package homecode.opteamer.model.dtos;

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
    private Long teamMemberId;
    private String preOpAssessmentId;
    private Long patientId;

    private TeamMemberDTO teamMemberDTO;
    private PreOperativeAssessmentDTO preOperativeAssessmentDTO;
    private PatientDTO patientDTO;
    private LocalDateTime startDate;
}
