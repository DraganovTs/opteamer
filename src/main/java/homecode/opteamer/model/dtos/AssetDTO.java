package homecode.opteamer.model.dtos;

import homecode.opteamer.validation.ValidEnum;
import homecode.opteamer.model.enums.AssetType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "Asset",
        description = "Schema to hold Asset information"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssetDTO {

    private Long id;
    @Schema(
            description = "Asset Type information" ,
            example = "SURGICAL_INSTRUMENT"
    )
    @ValidEnum(enumClass = AssetType.class, message = "Invalid asset type")
    private AssetType type;
    @Size(min = 1, max = 50)
    @Schema(
            description = "Asset name information",
            example = "SCALPEL"
    )
    private String name;

}
