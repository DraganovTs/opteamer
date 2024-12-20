package homecode.opteamer.model.dtos;

import homecode.opteamer.model.enums.OperationState;
import homecode.opteamer.validation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(
        name = "Operation",
        description = "Schema to hold operation information"
)
public class OperationDTO {

    @Schema(
            description = "Unique identifier for the operation",
            example = "1"
    )
    private Long id;

    @NotNull(message = "Operation Type cannot be null")
    @Schema(
            description = "Details of the type of operation",
            implementation = OperationTypeDTO.class
    )
    @Valid
    private OperationTypeDTO operationTypeDTO;

    @NotNull(message = "Operation Room cannot be null")
    @Schema(
            description = "Details of the room where the operation takes place",
            implementation = OperationRoomDTO.class
    )
    @Valid
    private OperationRoomDTO operationRoomDTO;

    @NotNull(message = "Patient cannot be null")
    @Schema(
            description = "Details of the patient undergoing the operation",
            implementation = PatientDTO.class
    )
    @Valid
    private PatientDTO patientDTO;

    @ValidEnum(enumClass = OperationState.class, message = "Invalid operation state")
    @Schema(
            description = "Current state of the operation",
            example = "SCHEDULED"
    )
    private OperationState state;

    @FutureOrPresent(message = "Operation date must be future or present")
    @Schema(
            description = "Scheduled start date of the operation",
            example = "2024-10-09T10:00:00"
    )
    private LocalDateTime startDate;

    @Schema(
            description = "Set of team members involved in the operation",
            implementation = TeamMemberDTO.class
    )
    @Valid
    private Set<TeamMemberDTO> teamMembersDTO = new HashSet<>();
}
