package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.TeamMemberDTO;
import homecode.opteamer.service.TeamMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/teamMembers")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    public TeamMemberController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamMemberDTO> getTeamMember(@PathVariable Long id) {
        TeamMemberDTO teamMemberDTO = teamMemberService.findTeamMemberById(id);
        return ResponseEntity.ok(teamMemberDTO);
    }

    @GetMapping
    public ResponseEntity<List<TeamMemberDTO>> getTeamMembers() {
        List<TeamMemberDTO> teamMemberDTOList = teamMemberService.getAllTeamMembers();
        return ResponseEntity.ok(teamMemberDTOList);
    }

    @PostMapping
    public ResponseEntity<TeamMemberDTO> createTeamMember(@Valid @RequestBody TeamMemberDTO teamMemberDTO) {
        TeamMemberDTO teamMemberDTOSaved = teamMemberService.createTeamMember(teamMemberDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(teamMemberDTOSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamMemberDTO> updateTeamMember(@PathVariable Long id,
                                                          @Valid @RequestBody TeamMemberDTO teamMemberDTO) {
        TeamMemberDTO updatedTeamMemberDTO = teamMemberService.updateTeamMember(id, teamMemberDTO);
        return ResponseEntity.ok(updatedTeamMemberDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamMember(@PathVariable Long id) {
        teamMemberService.deleteTeamMember(id);
        return ResponseEntity.noContent().build();
    }
}
