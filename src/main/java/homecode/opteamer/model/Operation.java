package homecode.opteamer.model;

import homecode.opteamer.model.enums.OperationState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "operations")
@NoArgsConstructor
@Getter
@Setter
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private OperationType operationType;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private OperationRoom operationRoom;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private OperationState state;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "optype_team_member",
            joinColumns = @JoinColumn(name = "operation_id"),
            inverseJoinColumns = @JoinColumn(name = "team_member_id"))
    private Set<TeamMember> teamMembers = new HashSet<>();

}
