package homecode.opteamer.mapper;

import homecode.opteamer.model.Operation;
import homecode.opteamer.model.dtos.OperationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {OperationTypeMapper.class ,OperationRoomMapper.class , PatientMapper.class , TestMemberMapper.class})
public interface OperationMapper {

    OperationMapper INSTANCE = Mappers.getMapper(OperationMapper.class);

    @Mapping(source = "operationRoom" , target = "operationRoomDTO")
    @Mapping(source = "patient" , target = "patientDTO")
    @Mapping(source = "teamMembers" , target = "teamMembersDTO")
    OperationDTO toOperationDTO(Operation operation);

    Operation toOperation(OperationDTO operationDTO);
}
