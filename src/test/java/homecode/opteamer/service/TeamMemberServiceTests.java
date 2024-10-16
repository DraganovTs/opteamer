package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.OperationProvider;
import homecode.opteamer.model.TeamMember;
import homecode.opteamer.model.dtos.OperationProviderDTO;
import homecode.opteamer.model.dtos.TeamMemberDTO;
import homecode.opteamer.model.enums.OperationProviderType;
import homecode.opteamer.repository.OperationProviderRepository;
import homecode.opteamer.repository.TeamMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeamMemberServiceTests {

    @Mock
    private TeamMemberRepository teamMemberRepository;
    @Mock
    private OperationProviderRepository operationProviderRepository;

    @InjectMocks
    private TeamMemberService teamMemberService;

    TeamMember teamMember;
    TeamMember teamMember1;
    TeamMemberDTO teamMemberDTO;
    OperationProvider operationProvider;
    OperationProviderDTO operationProviderDTO;


    @BeforeEach
    public void setUp() {
        operationProvider = new OperationProvider();
        operationProvider.setType(OperationProviderType.CP_ROOM_NURSE);

        operationProviderDTO = new OperationProviderDTO();
        operationProviderDTO.setType(OperationProviderType.CP_ROOM_NURSE);

        teamMember = new TeamMember();
        teamMember.setOperationProvider(operationProvider);

        teamMember1 = new TeamMember();
        teamMember1.setOperationProvider(operationProvider);

        teamMemberDTO = new TeamMemberDTO();
        teamMemberDTO.setOperationProviderDTO(operationProviderDTO);

    }

    @Test
    void createTeamMember_ShouldSaveAndReturnTeamMemberDTO() {
        when(teamMemberRepository.save(any(TeamMember.class))).thenReturn(teamMember);
        when(operationProviderRepository.findByType(any(OperationProviderType.class))).thenReturn(Optional.of(operationProvider));

        TeamMemberDTO savedTeamMemberDTO = teamMemberService.createTeamMember(teamMemberDTO);

        assertNotNull(savedTeamMemberDTO);
        assertEquals(teamMember.getName(), savedTeamMemberDTO.getName());

        verify(teamMemberRepository, times(1)).save(any(TeamMember.class));
        verify(operationProviderRepository, times(1)).findByType(any(OperationProviderType.class));

    }

    @Test
    void getTeamMember_ShouldReturnTeamMemberDTO() {
        when(teamMemberRepository.findById(anyLong())).thenReturn(Optional.of(teamMember));

        TeamMemberDTO foundTeamMemberDTO = teamMemberService.findTeamMemberById(1L);

        assertNotNull(foundTeamMemberDTO);
        assertEquals(teamMember.getName(), foundTeamMemberDTO.getName());
        verify(teamMemberRepository, times(1)).findById(anyLong());
    }

    @Test
    void getTeamMember_ShouldReturnNullIfTeamMemberNotFound() {
        when(teamMemberRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> teamMemberService.findTeamMemberById(1L));

        verify(teamMemberRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAllTeamMembers_ShouldReturnListOfTeamMemberDTOs() {
        when(teamMemberRepository.findAll()).thenReturn(Arrays.asList(teamMember,teamMember1));

        List<TeamMemberDTO> foundTeamMemberDTOs = teamMemberService.getAllTeamMembers();

        assertNotNull(foundTeamMemberDTOs);
        assertEquals(teamMember.getName(), foundTeamMemberDTOs.get(0).getName());
        assertEquals(teamMember1.getName(), foundTeamMemberDTOs.get(1).getName());
        verify(teamMemberRepository, times(1)).findAll();
    }

    @Test
    void updateTeamMember_ShouldUpdateTeamMemberDTO() {
        when(teamMemberRepository.findById(1L)).thenReturn(Optional.of(teamMember));

        teamMemberDTO.setName("Updated Name");

        TeamMemberDTO updatedTeamMemberDTO = teamMemberService.updateTeamMember(1L, teamMemberDTO);

        assertNotNull(updatedTeamMemberDTO);
        assertEquals(teamMemberDTO.getName(), updatedTeamMemberDTO.getName());
        verify(teamMemberRepository, times(1)).findById(anyLong());

    }

    @Test
    void updateTeamMember_ShouldUpdateNullIfTeamMemberNotFound() {
        when(teamMemberRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> teamMemberService.updateTeamMember(1L, teamMemberDTO));
        verify(teamMemberRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteTeamMember_ShouldDeleteTeamMemberDTO() {
       when(teamMemberRepository.findById(anyLong())).thenReturn(Optional.of(teamMember));
        doNothing().when(teamMemberRepository).delete(any(TeamMember.class));

        teamMemberService.deleteTeamMember(1L);

        verify(teamMemberRepository, times(1)).findById(anyLong());
        verify(teamMemberRepository, times(1)).delete(any(TeamMember.class));
    }


    @Test
    void deleteTeamMember_ShouldDeleteNullIfTeamMemberNotFound() {
        when(teamMemberRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> teamMemberService.deleteTeamMember(1L));
        verify(teamMemberRepository, times(1)).findById(anyLong());
    }


}
