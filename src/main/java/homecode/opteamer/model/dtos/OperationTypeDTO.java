package homecode.opteamer.model.dtos;

import homecode.opteamer.model.enums.OperationRoomType;
import homecode.opteamer.validation.ValidEnum;
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
public class OperationTypeDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @ValidEnum(enumClass = OperationRoomType.class, message = "Invalid operation room type")
    @NotNull(message = "Room type cannot be null")
    private OperationRoomType roomType;

    @NotNull(message = "Duration hours cannot be null")
    private Integer durationHours;

    private Set<AssetDTO> assetDTOS = new HashSet<>();

    private Set<OperationProviderDTO> operationProvidersDTO = new HashSet<>();

    private Set<PreOperativeAssessmentDTO> preOperativeAssessmentsDTO = new HashSet<>();}
