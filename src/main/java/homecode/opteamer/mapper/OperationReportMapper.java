package homecode.opteamer.mapper;

import homecode.opteamer.model.OperationReport;
import homecode.opteamer.model.dtos.OperationReportDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {OperationMapper.class, TeamMemberMapper.class})
public interface OperationReportMapper {

    OperationReportMapper INSTANCE = Mappers.getMapper(OperationReportMapper.class);


    @Mapping(source = "operation", target = "operationDTO")
    @Mapping(source = "teamMember" , target = "teamMemberDTO")
    OperationReportDTO toOperationReportDTO(OperationReport operationReport);

    OperationReport toOperationReport(OperationReportDTO operationReportDTO);
}
