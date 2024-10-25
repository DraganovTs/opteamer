package homecode.opteamer.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
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
@Schema(
        name = "OperationReport",
        description = "Schema to hold operation report information"
)
public class OperationReportDTO {

    @NotNull(message = "Team member ID cannot be null")
    @Schema(
            description = "Unique identifier for the team member",
            example = "1"
    )
    private Long teamMemberId;

    @NotNull(message = "Operation ID cannot be null")
    @Schema(
            description = "Unique identifier for the operation",
            example = "1"
    )
    private Long operationId;

    @NotNull(message = "TeamMemberDTO cannot be null")
    @Schema(
            description = "Details of the team member responsible for the report",
            implementation = TeamMemberDTO.class
    )
    @Valid
    private TeamMemberDTO teamMemberDTO;

    @NotNull(message = "OperationDTO cannot be null")
    @Schema(
            description = "Details of the operation associated with the report",
            implementation = OperationDTO.class
    )
    @Valid
    private OperationDTO operationDTO;

    @NotBlank(message = "Report cannot be blank")
    @Size(min = 10, message = "Report must be at least 10 characters long")
    @Schema(
            description = "Content of the operation report",
            example = "The operation was successful with no complications."
    )
    private String report;
}
