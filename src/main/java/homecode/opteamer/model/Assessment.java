package homecode.opteamer.model;

import homecode.opteamer.model.embededIds.AssessmentId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Assessments")
@NoArgsConstructor
@Getter
@Setter
public class Assessment {

    @EmbeddedId
    private AssessmentId assessmentId;

    @ManyToOne
    @MapsId("teamMemberId")
    @JoinColumn(name = "team_member_id",columnDefinition = "BIGINT")
    private TeamMember teamMember;

    @ManyToOne
    @MapsId("patientId")
    @JoinColumn(name = "patient_id",columnDefinition = "BIGINT")
    private Patient patient;

    @ManyToOne
    @MapsId("preOpAId")
    @JoinColumn(name = "pre_op_a_id")
    private PreOperativeAssessment preOperativeAssessment;

    @Column(name = "start_date")
    private LocalDateTime startDate;
}
