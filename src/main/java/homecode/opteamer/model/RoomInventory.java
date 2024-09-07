package homecode.opteamer.model;

import homecode.opteamer.model.embededIds.RoomInventoryId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "room_inventory")
@NoArgsConstructor
@Getter
@Setter
public class RoomInventory {

    @EmbeddedId
    private RoomInventoryId roomInventoryId;

    @ManyToOne
    @MapsId("asset")
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @ManyToOne
    @MapsId("operationRoom")
    @JoinColumn(name = "room_id")
    private OperationRoom operationRoom;

    private Integer count;
}
