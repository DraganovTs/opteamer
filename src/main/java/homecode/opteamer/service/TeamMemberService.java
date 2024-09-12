package homecode.opteamer.service;

import homecode.opteamer.model.OperationProvider;
import homecode.opteamer.model.TeamMember;
import homecode.opteamer.model.dtos.TeamMemberDTO;
import homecode.opteamer.model.enums.OperationProviderType;
import homecode.opteamer.repository.TeamMemberRepository;
import homecode.opteamer.util.MapperUtility;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;

    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }


    public Optional<TeamMemberDTO> findTeamMemberById(Long id) {
        try {
            TeamMember teamMember = teamMemberRepository.findById(id).orElse(null);
            return Optional.of(MapperUtility.mapEntityToDTO(teamMember, TeamMemberDTO.class));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<TeamMemberDTO> findAllTeamMembers() {
        List<TeamMemberDTO> teamMemberDTOList = new ArrayList<>();
        Iterable<TeamMember> teamMembersOptional = teamMemberRepository.findAll();
        teamMembersOptional.forEach(teamMember ->
                teamMemberDTOList.add(MapperUtility.mapEntityToDTO(teamMember, TeamMemberDTO.class))
        );
        return teamMemberDTOList;
    }

    public TeamMemberDTO createTeamMember(TeamMemberDTO teamMemberDTO) {
        OperationProvider operationProvider = MapperUtility.mapDTOToEntity(teamMemberDTO.getOperationProvider(),
                OperationProvider.class);
        TeamMember teamMember = MapperUtility.mapDTOToEntity(teamMemberDTO, TeamMember.class);
        teamMember.setOperationProvider(operationProvider);
        teamMember = teamMemberRepository.save(teamMember);
        return MapperUtility.mapEntityToDTO(teamMember, TeamMemberDTO.class);
    }

    public Optional<TeamMemberDTO> updateTeamMember(Long id, TeamMemberDTO teamMemberDTO) {
        OperationProvider operationProvider = new OperationProvider();
        operationProvider.setType(OperationProviderType.valueOf(teamMemberDTO.getOperationProvider().getType().name()));
        return teamMemberRepository.findById(id).map(teamMember -> {
            teamMember.setName(teamMemberDTO.getName());
            teamMember.setOperationProvider(operationProvider);
            return MapperUtility.mapEntityToDTO(teamMemberRepository.save(teamMember), TeamMemberDTO.class);
        });

    }

    public boolean deleteTeamMember(Long id) {
        return teamMemberRepository.findById(id).map(teamMember -> {
            teamMemberRepository.delete(teamMember);
            return true;
        }).orElse(false);
    }


}
