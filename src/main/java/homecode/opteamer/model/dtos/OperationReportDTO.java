package homecode.opteamer.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OperationReportDTO {

    @NotNull(message = "Team member ID cannot be null")
    private Long teamMemberId;

    @NotNull(message = "Operation ID cannot be null")
    private Long operationId;

    @NotNull(message = "TeamMemberDTO cannot be null")
    private TeamMemberDTO teamMemberDTO;

    @NotNull(message = "OperationDTO cannot be null")
    private OperationDTO operationDTO;

    @NotBlank(message = "Report cannot be blank")
    @Size(min = 10, message = "Report must be at least 10 characters long")
    private String report;
}
