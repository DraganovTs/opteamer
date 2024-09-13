package homecode.opteamer.mapper;

import homecode.opteamer.model.OperationProvider;
import homecode.opteamer.model.dtos.OperationProviderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OperationProviderMapper {

    OperationProviderMapper INSTANCE = Mappers.getMapper(OperationProviderMapper.class);

    OperationProviderDTO toOperationProviderDTO(OperationProvider operationProvider);

    OperationProvider toOperationProvider(OperationProviderDTO operationProviderDTO);
}
