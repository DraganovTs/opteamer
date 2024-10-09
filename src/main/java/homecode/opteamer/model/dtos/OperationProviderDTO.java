package homecode.opteamer.model.dtos;

import homecode.opteamer.model.enums.OperationProviderType;
import homecode.opteamer.validation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(
        name = "OperationProvider",
        description = "Schema for the operation provider details"
)
public class OperationProviderDTO {

    @ValidEnum(enumClass = OperationProviderType.class, message = "Invalid operation provider type")
    @Schema(
            description = "Type of operation provider",
            example = "SURGEON"
    )
    private OperationProviderType type;
}
