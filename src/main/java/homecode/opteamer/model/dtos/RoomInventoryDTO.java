package homecode.opteamer.model.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomInventoryDTO {

    @NotNull(message = "Room Inventory ID cannot be null")
    private Long roomInventoryId;

    @NotNull(message = "Operation Room ID cannot be null")
    private Long operationRoomId;

    @NotNull(message = "Asset cannot be null")
    private AssetDTO assetDTO;

    @NotNull(message = "Operation Room cannot be null")
    private OperationRoomDTO operationRoomDTO;

    @NotNull(message = "Count cannot be null")
    private Integer count;
}
