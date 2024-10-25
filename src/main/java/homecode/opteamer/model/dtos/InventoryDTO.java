package homecode.opteamer.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "Inventory",
        description = "Schema to hold inventory information for assets"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryDTO {

    @Schema(
            description = "ID of the asset associated with this inventory item",
            example = "1"
    )
    private Long assetId;

    @Schema(
            description = "Details of the asset associated with this inventory item"
    )
    @Valid
    private AssetDTO asset;

    @Min(value = 1, message = "Count must be at least 1")
    @Schema(
            description = "The number of units available in the inventory",
            example = "5"
    )
    private Integer count;

}
