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

    private TeamMemberRepository teamMemberRepository;

    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }


    public Optional<TeamMemberDTO> findTeamMemberById(Long id) {
        try {
            TeamMember teamMember = teamMemberRepository.findById(id).orElse(null);
            return Optional.of(mapEntityToDTO(teamMember));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<TeamMemberDTO> findAllTeamMembers() {
        List<TeamMemberDTO> teamMemberDTOList = new ArrayList<>();
        Iterable<TeamMember> teamMembersOptional = teamMemberRepository.findAll();
        teamMembersOptional.forEach(teamMember -> {
            teamMemberDTOList.add(mapEntityToDTO(teamMember));
        });
        return teamMemberDTOList;
    }

    public TeamMemberDTO createTeamMember(TeamMemberDTO teamMemberDTO) {
        OperationProvider operationProvider = MapperUtility.mapDTOToEntity(teamMemberDTO.getOperationProvider(),
                OperationProvider.class);
        TeamMember teamMember = MapperUtility.mapDTOToEntity(teamMemberDTO, TeamMember.class);
        teamMember.setOperationProvider(operationProvider);
        teamMember = teamMemberRepository.save(teamMember);
        return mapEntityToDTO(teamMember);
    }

    public Optional<TeamMemberDTO> updateTeamMember(Long id, TeamMemberDTO teamMemberDTO) {
        OperationProvider operationProvider = new OperationProvider();
        operationProvider.setType(OperationProviderType.valueOf(teamMemberDTO.getOperationProvider().getType().name()));
        return teamMemberRepository.findById(id).map(teamMember -> {
            teamMember.setName(teamMemberDTO.getName());
            teamMember.setOperationProvider(operationProvider);
            return mapEntityToDTO(teamMemberRepository.save(teamMember));
        });

    }

    public boolean deleteTeamMember(Long id) {
        return teamMemberRepository.findById(id).map(teamMember -> {
            teamMemberRepository.delete(teamMember);
            return true;
        }).orElse(false);
    }


    private TeamMember mapDTOToEntity(TeamMemberDTO teamMemberDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(teamMemberDTO, TeamMember.class);
    }

    private TeamMemberDTO mapEntityToDTO(TeamMember teamMember) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(teamMember, TeamMemberDTO.class);
    }
}
