package homecode.opteamer.model.dtos;

import homecode.opteamer.model.enums.OperationProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OperationProviderDTO {

    private OperationProviderType type;

}
