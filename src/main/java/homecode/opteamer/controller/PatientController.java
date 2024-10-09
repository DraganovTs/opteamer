package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.PatientDTO;
import homecode.opteamer.service.PatientService;
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
        name = "CRUD REST APIs for Patients",
        description = "CRUD REST APIs for managing patients in the Opteamer Application"
)
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(
            summary = "Get Patient by ID",
            description = "REST API to fetch a specific patient by their ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient found and retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long id) {
        PatientDTO patientDTO = patientService.getPatientById(id);
        return ResponseEntity.ok(patientDTO);
    }

    @Operation(
            summary = "Get All Patients",
            description = "REST API to fetch all patients"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all patients"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<PatientDTO>> getPatients() {
        List<PatientDTO> patientDTOList = patientService.getAllPatients();
        return ResponseEntity.ok(patientDTOList);
    }

    @Operation(
            summary = "Create a New Patient",
            description = "REST API to create a new patient"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Patient created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO createdPatientDTO = patientService.createPatient(patientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatientDTO);
    }

    @Operation(
            summary = "Update an Existing Patient",
            description = "REST API to update a patient by their ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO updatedPatientDTO = patientService.updatePatient(id, patientDTO);
        return ResponseEntity.ok(updatedPatientDTO);
    }

    @Operation(
            summary = "Delete a Patient",
            description = "REST API to delete a patient by their ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
