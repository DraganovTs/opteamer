package homecode.opteamer.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OperationReportDTO {

    private Long teamMemberId;
    private Long operationId;

    private TeamMemberDTO teamMemberDTO;
    private OperationDTO operationDTO;
    private String report;
}
