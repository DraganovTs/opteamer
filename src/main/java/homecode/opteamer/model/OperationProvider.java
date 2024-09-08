package homecode.opteamer.model;

import homecode.opteamer.model.enums.OperationProviderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "operation_providers")
@NoArgsConstructor
@Getter
@Setter
public class OperationProvider {

    @Id
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OperationProviderType type;



}
