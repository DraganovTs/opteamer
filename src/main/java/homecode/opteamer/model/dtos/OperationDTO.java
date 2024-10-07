package homecode.opteamer.model.dtos;


import homecode.opteamer.model.enums.AssetType;
import homecode.opteamer.model.enums.OperationState;
import homecode.opteamer.validation.ValidEnum;
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
public class OperationDTO {

    private Long id;

    @NotNull(message = "Operation Type cannot be null")
    private OperationTypeDTO operationTypeDTO;

    @NotNull(message = "Operation Room cannot be null")
    private OperationRoomDTO operationRoomDTO;

    @NotNull(message = "Patient cannot be null")
    private PatientDTO patientDTO;
    @ValidEnum(enumClass = OperationState.class, message = "Invalid operation state")
    private OperationState state;
    @FutureOrPresent(message = "Operation date must be future or present")
    private LocalDateTime startDate;

    private Set<TeamMemberDTO> teamMembersDTO = new HashSet<>();

}
