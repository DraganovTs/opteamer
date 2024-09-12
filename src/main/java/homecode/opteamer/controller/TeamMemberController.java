package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.TeamMemberDTO;
import homecode.opteamer.service.TeamMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teamMembers")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    public TeamMemberController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamMemberDTO> getTeamMember(@PathVariable Long id) {
        Optional<TeamMemberDTO> teamMemberDTOOptional = teamMemberService.findTeamMemberById(id);
        return teamMemberDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<TeamMemberDTO>> getTeamMembers() {
        List<TeamMemberDTO> teamMemberDTOList = teamMemberService.findAllTeamMembers();
        return ResponseEntity.status(HttpStatus.OK).body(teamMemberDTOList);
    }

    @PostMapping
    public ResponseEntity<TeamMemberDTO> createTeamMember(@RequestBody TeamMemberDTO teamMemberDTO) {
        TeamMemberDTO teamMemberDTOSaved = teamMemberService.createTeamMember(teamMemberDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(teamMemberDTOSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamMemberDTO> updateTeamMember(@PathVariable Long id,
                                                          @RequestBody TeamMemberDTO teamMemberDTO) {
        Optional<TeamMemberDTO> teamMemberDTOOptional = teamMemberService.updateTeamMember(id, teamMemberDTO);
        return teamMemberDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TeamMemberDTO> deleteTeamMember(@PathVariable Long id) {
        boolean teamMemberDeleted = teamMemberService.deleteTeamMember(id);
        if (teamMemberDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
