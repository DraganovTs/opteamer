package homecode.opteamer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pre_operative_assessment")
@Getter
@Setter
public class PreOperativeAssessment {
    @Id
    private String name;
}
