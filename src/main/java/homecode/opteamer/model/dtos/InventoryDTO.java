package homecode.opteamer.model.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryDTO {
    private Long assetId;

    private AssetDTO asset;

    @Min(value = 1, message = "Count must be at least 1")
    private Integer count;

}