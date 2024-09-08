package homecode.opteamer.model.embededIds;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class OperationReportId implements Serializable {

    @Column(name = "team_member_id")
    private Long teamMemberId;

    @Column(name = "opration_id")
    private Long operationId;



}
