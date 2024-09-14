package homecode.opteamer.mapper;

import homecode.opteamer.model.TeamMember;
import homecode.opteamer.model.dtos.TeamMemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {OperationProviderMapper.class})
public interface TestMemberMapper {

    TestMemberMapper INSTANCE = Mappers.getMapper(TestMemberMapper.class);

    @Mapping(source = "operationProvider", target = "operationProviderDTO")
    TeamMemberDTO toTeamMemberDTO(TeamMember teamMember);

    @Mapping(source = "operationProviderDTO", target = "operationProvider")
    TeamMember toTeamMember(TeamMemberDTO teamMemberDTO);
}
