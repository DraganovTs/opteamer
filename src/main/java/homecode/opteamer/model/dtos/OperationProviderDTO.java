package homecode.opteamer.model.dtos;

import homecode.opteamer.model.enums.OperationProviderType;
import homecode.opteamer.validation.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OperationProviderDTO {
    @ValidEnum(enumClass = OperationProviderType.class, message = "Invalid operation provider type")
    private OperationProviderType type;

}
