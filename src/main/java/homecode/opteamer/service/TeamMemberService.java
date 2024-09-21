package homecode.opteamer.service;

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
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final OperationProviderRepository operationProviderRepository;

    public TeamMemberService(TeamMemberRepository teamMemberRepository,
                             OperationProviderRepository operationProviderRepository) {
        this.teamMemberRepository = teamMemberRepository;
        this.operationProviderRepository = operationProviderRepository;
    }


    public Optional<TeamMemberDTO> findTeamMemberById(Long id) {
        try {
            TeamMember teamMember = teamMemberRepository.findById(id).orElseThrow();
            return Optional.of(TeamMemberMapper.INSTANCE.toTeamMemberDTO(teamMember));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<TeamMemberDTO> getAllTeamMembers() {
        List<TeamMemberDTO> teamMemberDTOList = new ArrayList<>();
        Iterable<TeamMember> allTeamMembers = teamMemberRepository.findAll();
        allTeamMembers.forEach(teamMember ->
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

    public Optional<TeamMemberDTO> updateTeamMember(Long id, TeamMemberDTO teamMemberDTO) {
        return teamMemberRepository.findById(id).map(teamMember -> {
            teamMember.setName(teamMemberDTO.getName());

            OperationProvider operationProvider = OperationProviderMapper.INSTANCE.toOperationProvider(teamMemberDTO.getOperationProviderDTO());
            teamMember.setOperationProvider(operationProvider);

            teamMemberRepository.save(teamMember);
            return TeamMemberMapper.INSTANCE.toTeamMemberDTO(teamMember);
        });
    }

    public boolean deleteTeamMember(Long id) {
        return teamMemberRepository.findById(id).map(teamMember -> {
            teamMemberRepository.delete(teamMember);
            return true;
        }).orElse(false);
    }


}
