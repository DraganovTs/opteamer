package homecode.opteamer.mapper;

import homecode.opteamer.model.OperationRoom;
import homecode.opteamer.model.dtos.OperationRoomDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OperationRoomMapper {

    OperationRoomMapper INSTANCE = Mappers.getMapper(OperationRoomMapper.class);

    OperationRoomDTO operationRoomToOperationRoomDTO(OperationRoom operationRoom);

    OperationRoom operationRoomDTOToOperationRoom(OperationRoomDTO operationRoomDTO);
}
