package homecode.opteamer.model.dtos;

import homecode.opteamer.model.enums.OperationRoomState;
import homecode.opteamer.model.enums.OperationRoomType;
import homecode.opteamer.validation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(
        name = "OperationRoom",
        description = "Schema to hold operation room details"
)
public class OperationRoomDTO {

    @Schema(
            description = "Unique identifier for the operation room",
            example = "1"
    )
    private Long id;

    @NotNull(message = "Room number cannot be null")
    @Min(value = 1, message = "Room number must be greater than 0")
    @Schema(
            description = "Room number of the operation room",
            example = "101"
    )
    private Integer roomNr;

    @NotBlank(message = "Building block cannot be blank")
    @Size(min = 1, max = 50, message = "Building block must be between 1 and 50 characters")
    @Schema(
            description = "Building block where the operation room is located",
            example = "Main Block"
    )
    private String buildingBlock;

    @NotBlank(message = "Floor cannot be blank")
    @Size(min = 1, max = 2, message = "Floor must be between 1 and 2 characters")
    @Schema(
            description = "Floor number of the building where the operation room is located",
            example = "1"
    )
    private String floor;

    @ValidEnum(enumClass = OperationRoomType.class, message = "Invalid operation room type")
    @NotNull(message = "Operation room type cannot be null")
    @Schema(
            description = "Type of the operation room",
            example = "SURGERY"
    )
    private OperationRoomType type;

    @ValidEnum(enumClass = OperationRoomState.class, message = "Invalid operation room state")
    @NotNull(message = "Operation room state cannot be null")
    @Schema(
            description = "Current state of the operation room",
            example = "AVAILABLE"
    )
    private OperationRoomState state;
}
