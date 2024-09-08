package homecode.opteamer.model;

import homecode.opteamer.model.embededIds.OperationReportId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "operation_reports")
@NoArgsConstructor
@Getter
@Setter
public class OperationReport {

    @EmbeddedId
    private OperationReportId operationReportId;

    @ManyToOne
    @MapsId("teamMemberId")
    @JoinColumn(name = "team_member_id",columnDefinition = "BIGINT")
    private TeamMember teamMember;

    @ManyToOne
    @MapsId("operationId")
    @JoinColumn(name = "operation_id",columnDefinition = "BIGINT")
    private Operation operation;

    @Column(name = "report")
    private String report;
}
