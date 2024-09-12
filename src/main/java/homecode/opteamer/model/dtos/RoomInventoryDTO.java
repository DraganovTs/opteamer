package homecode.opteamer.model.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomInventoryDTO {

    private Long roomInventoryId;
    private Long operationRoomId;
    private AssetDTO assetDTO;
    private OperationRoomDTO operationRoomDTO;
    private Integer count;

}
