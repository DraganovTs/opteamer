package homecode.opteamer.model.dtos;

import homecode.opteamer.model.enums.OperationRoomType;
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

    private String name;

    private OperationRoomType roomType;

    private Integer durationHours;

    private Set<AssetDTO> assetDTOS = new HashSet<>();

    private Set<OperationProviderDTO> operationProvidersDTO = new HashSet<>(); ;

    private Set<PreOperativeAssessmentDTO> preOperativeAssessmentsDTO = new HashSet<>(); ;
}
