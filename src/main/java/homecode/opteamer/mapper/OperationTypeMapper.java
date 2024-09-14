package homecode.opteamer.mapper;

import homecode.opteamer.model.OperationType;
import homecode.opteamer.model.dtos.OperationTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AssetMapper.class, OperationTypeMapper.class ,PreOperativeAssessmentMapper.class, OperationProviderMapper.class})
public interface OperationTypeMapper {

    OperationTypeMapper INSTANCE = Mappers.getMapper(OperationTypeMapper.class);

    @Mapping(source = "assets", target = "assetDTOS")
    @Mapping(source = "operationProviders", target = "operationProvidersDTO")
    @Mapping(source = "preOperativeAssessments", target = "preOperativeAssessmentsDTO")
    OperationTypeDTO toOperationTypeDTO(OperationType operationType);

    @Mapping(source = "assetDTOS",target = "assets")
    @Mapping(source = "operationProvidersDTO", target = "operationProviders")
    @Mapping(source = "preOperativeAssessmentsDTO",target = "preOperativeAssessments")
    OperationType toOperationType(OperationTypeDTO operationTypeDTO);
}
