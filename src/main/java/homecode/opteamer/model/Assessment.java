package homecode.opteamer.model;

import homecode.opteamer.model.embededIds.AssessmentId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Assessment")
@NoArgsConstructor
@Getter
@Setter
public class Assessment {

    @EmbeddedId
    private AssessmentId assessmentId;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "team_member_id")
    private TeamMember teamMember;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @MapsId("preOperativeAssessment")
    @JoinColumn(name = "pre_op_a_id")
    private PreOperativeAssessment preOperativeAssessment;

    @Column(name = "start_date")
    private LocalDateTime startDate;
}
