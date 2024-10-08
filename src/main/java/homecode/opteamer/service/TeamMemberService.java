package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.mapper.OperationProviderMapper;
import homecode.opteamer.mapper.TeamMemberMapper;
import homecode.opteamer.model.OperationProvider;
import homecode.opteamer.model.TeamMember;
import homecode.opteamer.model.dtos.TeamMemberDTO;
import homecode.opteamer.repository.OperationProviderRepository;
import homecode.opteamer.repository.TeamMemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final OperationProviderRepository operationProviderRepository;

    public TeamMemberService(TeamMemberRepository teamMemberRepository,
                             OperationProviderRepository operationProviderRepository) {
        this.teamMemberRepository = teamMemberRepository;
        this.operationProviderRepository = operationProviderRepository;
    }

    public TeamMemberDTO findTeamMemberById(Long id) {
        TeamMember teamMember = teamMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team Member not found with ID: " + id));
        return TeamMemberMapper.INSTANCE.toTeamMemberDTO(teamMember);
    }

    public List<TeamMemberDTO> getAllTeamMembers() {
        List<TeamMemberDTO> teamMemberDTOList = new ArrayList<>();
        teamMemberRepository.findAll().forEach(teamMember ->
                teamMemberDTOList.add(TeamMemberMapper.INSTANCE.toTeamMemberDTO(teamMember))
        );
        return teamMemberDTOList;
    }

    public TeamMemberDTO createTeamMember(TeamMemberDTO teamMemberDTO) {
        OperationProvider operationProvider = operationProviderRepository.findByType(teamMemberDTO.getOperationProviderDTO().getType())
                .orElseGet(() -> {
                    OperationProvider newOperationProvider = OperationProviderMapper.INSTANCE.toOperationProvider(teamMemberDTO.getOperationProviderDTO());
                    return operationProviderRepository.save(newOperationProvider);
                });

        TeamMember teamMember = TeamMemberMapper.INSTANCE.toTeamMember(teamMemberDTO);
        teamMember.setOperationProvider(operationProvider);
        teamMember = teamMemberRepository.save(teamMember);

        return TeamMemberMapper.INSTANCE.toTeamMemberDTO(teamMember);
    }

    public TeamMemberDTO updateTeamMember(Long id, TeamMemberDTO teamMemberDTO) {
        TeamMember teamMember = teamMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team Member not found with ID: " + id));

        teamMember.setName(teamMemberDTO.getName());

        OperationProvider operationProvider = OperationProviderMapper.INSTANCE.toOperationProvider(teamMemberDTO.getOperationProviderDTO());
        teamMember.setOperationProvider(operationProvider);

        teamMemberRepository.save(teamMember);
        return TeamMemberMapper.INSTANCE.toTeamMemberDTO(teamMember);
    }

    public void deleteTeamMember(Long id) {
        TeamMember teamMember = teamMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team Member not found with ID: " + id));
        teamMemberRepository.delete(teamMember);
    }
}
