package homecode.opteamer.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(
        name = "RoomInventory",
        description = "Schema to hold room inventory information"
)
public class RoomInventoryDTO {

    @NotNull(message = "Room Inventory ID cannot be null")
    @Schema(
            description = "Unique identifier for the room inventory",
            example = "1"
    )
    private Long roomInventoryId;

    @NotNull(message = "Operation Room ID cannot be null")
    @Schema(
            description = "Unique identifier for the operation room",
            example = "1"
    )
    private Long operationRoomId;

    @NotNull(message = "Asset cannot be null")
    @Schema(
            description = "Details of the asset in the room inventory",
            implementation = AssetDTO.class
    )
    @Valid
    private AssetDTO assetDTO;

    @NotNull(message = "Operation Room cannot be null")
    @Schema(
            description = "Details of the operation room",
            implementation = OperationRoomDTO.class
    )
    @Valid
    private OperationRoomDTO operationRoomDTO;

    @NotNull(message = "Count cannot be null")
    @Schema(
            description = "Number of assets in the room inventory",
            example = "10"
    )
    private Integer count;
}
