package homecode.opteamer.model.dtos;


import homecode.opteamer.model.enums.OperationState;
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

    private OperationTypeDTO operationTypeDTO;

    private OperationRoomDTO operationRoomDTO;

    private PatientDTO patientDTO;

    private OperationState state;

    private LocalDateTime startDate;

    private Set<TeamMemberDTO> teamMembersDTO = new HashSet<>();

}
