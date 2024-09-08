package homecode.opteamer.model;

import homecode.opteamer.model.embededIds.RoomInventoryId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "room_inventories")
@NoArgsConstructor
@Getter
@Setter
public class RoomInventory {

    @EmbeddedId
    private RoomInventoryId roomInventoryId;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id",columnDefinition = "BIGINT")
    private OperationRoom operationRoom;

    @ManyToOne
    @MapsId("assetId")
    @JoinColumn(name = "asset_id",columnDefinition = "BIGINT")
    private Asset asset;


    private Integer count;
}
