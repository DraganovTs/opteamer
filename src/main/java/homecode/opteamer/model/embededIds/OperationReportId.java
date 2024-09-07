package homecode.opteamer.model.embededIds;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class OperationReportId {

    @Column(name = "team_member_id")
    private Long teamMemberId;

    @Column(name = "opration_id")
    private Long operationId;



}
