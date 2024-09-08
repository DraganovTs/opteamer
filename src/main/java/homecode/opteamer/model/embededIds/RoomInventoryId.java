package homecode.opteamer.model.embededIds;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomInventoryId implements Serializable {

    @Column(name = "asset_id")
    private Long assetId;

    @Column(name = "room_id")
    private Long roomId;

}
