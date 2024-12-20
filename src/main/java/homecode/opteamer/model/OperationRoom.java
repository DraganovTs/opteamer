package homecode.opteamer.model;

import homecode.opteamer.model.enums.OperationRoomState;
import homecode.opteamer.model.enums.OperationRoomType;
import homecode.opteamer.validation.ValidEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "operation_rooms")
@NoArgsConstructor
@Getter
@Setter
public class OperationRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_nr")
    private Integer roomNr;

    @Column(name = "building_block")
    private String buildingBlock;

    @Column(name = "floor")
    private String floor;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = OperationRoomType.class)
    private OperationRoomType type;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = OperationRoomState.class)
    private OperationRoomState state;

}
