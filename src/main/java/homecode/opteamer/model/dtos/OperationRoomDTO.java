package homecode.opteamer.model.dtos;

import homecode.opteamer.model.enums.OperationRoomState;
import homecode.opteamer.model.enums.OperationRoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OperationRoomDTO {

    private Long id;

    private Integer roomNr;

    private String buildingBlock;

    private String floor;

    private OperationRoomType type;

    private OperationRoomState state;
}
