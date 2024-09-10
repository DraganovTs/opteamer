package homecode.opteamer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pre_operative_assessments")
@Getter
@Setter
public class PreOperativeAssessment {
    @Id
    private String name;
}
