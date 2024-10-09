package homecode.opteamer.model.dtos;

import homecode.opteamer.model.enums.OperationRoomType;
import homecode.opteamer.validation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(
        name = "OperationType",
        description = "Schema to hold details about operation types"
)
public class OperationTypeDTO {

    @NotBlank(message = "Name cannot be blank")
    @Schema(
            description = "Name of the operation type",
            example = "Appendectomy"
    )
    private String name;

    @ValidEnum(enumClass = OperationRoomType.class, message = "Invalid operation room type")
    @NotNull(message = "Room type cannot be null")
    @Schema(
            description = "Type of the operation room required for this operation",
            example = "SURGERY"
    )
    private OperationRoomType roomType;

    @NotNull(message = "Duration hours cannot be null")
    @Schema(
            description = "Duration of the operation in hours",
            example = "2"
    )
    private Integer durationHours;

    @Schema(
            description = "Set of assets required for this operation type",
            implementation = AssetDTO.class
    )
    private Set<AssetDTO> assetDTOS = new HashSet<>();

    @Schema(
            description = "Set of operation providers associated with this operation type",
            implementation = OperationProviderDTO.class
    )
    private Set<OperationProviderDTO> operationProvidersDTO = new HashSet<>();

    @Schema(
            description = "Set of pre-operative assessments associated with this operation type",
            implementation = PreOperativeAssessmentDTO.class
    )
    private Set<PreOperativeAssessmentDTO> preOperativeAssessmentsDTO = new HashSet<>();
}
