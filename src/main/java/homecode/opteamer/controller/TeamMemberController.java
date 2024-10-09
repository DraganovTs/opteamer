package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.ErrorResponseDTO;
import homecode.opteamer.model.dtos.TeamMemberDTO;
import homecode.opteamer.service.TeamMemberService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "CRUD REST APIs for Team Member",
        description = "CRUD REST APIs for managing team members in the Opteamer Application"
)
@RestController
@RequestMapping("/api/teamMembers")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    public TeamMemberController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @Operation(
            summary = "Get Team Member by ID",
            description = "REST API to fetch a specific team member by their ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Team member found and retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Team member not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<TeamMemberDTO> getTeamMember(@PathVariable Long id) {
        TeamMemberDTO teamMemberDTO = teamMemberService.findTeamMemberById(id);
        return ResponseEntity.ok(teamMemberDTO);
    }

    @Operation(
            summary = "Get All Team Members",
            description = "REST API to fetch all team members"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all team members"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<TeamMemberDTO>> getTeamMembers() {
        List<TeamMemberDTO> teamMemberDTOList = teamMemberService.getAllTeamMembers();
        return ResponseEntity.ok(teamMemberDTOList);
    }

    @Operation(
            summary = "Create a New Team Member",
            description = "REST API to create a new team member"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Team member created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<TeamMemberDTO> createTeamMember(@Valid @RequestBody TeamMemberDTO teamMemberDTO) {
        TeamMemberDTO teamMemberDTOSaved = teamMemberService.createTeamMember(teamMemberDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(teamMemberDTOSaved);
    }

    @Operation(
            summary = "Update an Existing Team Member",
            description = "REST API to update a team member by their ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Team member updated successfully"),
            @ApiResponse(responseCode = "404", description = "Team member not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<TeamMemberDTO> updateTeamMember(@PathVariable Long id,
                                                          @Valid @RequestBody TeamMemberDTO teamMemberDTO) {
        TeamMemberDTO updatedTeamMemberDTO = teamMemberService.updateTeamMember(id, teamMemberDTO);
        return ResponseEntity.ok(updatedTeamMemberDTO);
    }

    @Operation(
            summary = "Delete a Team Member",
            description = "REST API to delete a team member by their ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Team member deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Team member not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamMember(@PathVariable Long id) {
        teamMemberService.deleteTeamMember(id);
        return ResponseEntity.noContent().build();
    }
}
