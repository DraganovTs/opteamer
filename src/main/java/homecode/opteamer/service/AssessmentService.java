package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.Assessment;
import homecode.opteamer.model.Patient;
import homecode.opteamer.model.PreOperativeAssessment;
import homecode.opteamer.model.TeamMember;
import homecode.opteamer.model.dtos.*;
import homecode.opteamer.model.embededIds.AssessmentId;
import homecode.opteamer.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final PreOperativeAssessmentRepository preOperativeAssessmentRepository;
    private final PatientRepository patientRepository;

    public AssessmentService(AssessmentRepository assessmentRepository,
                             TeamMemberRepository teamMemberRepository,
                             PreOperativeAssessmentRepository preOperativeAssessmentRepository,
                             PatientRepository patientRepository) {
        this.assessmentRepository = assessmentRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.preOperativeAssessmentRepository = preOperativeAssessmentRepository;
        this.patientRepository = patientRepository;
    }

    public Optional<AssessmentDTO> getAssessmentById(Long teamMemberId, String preOpId, Long patientId) {
        AssessmentId assessmentId = new AssessmentId(teamMemberId, patientId, preOpId);
        return assessmentRepository.findById(assessmentId).map(this::convertToDTO);
    }

    public List<AssessmentDTO> getAllAssessments() {
        List<AssessmentDTO> assessmentDTOs = new ArrayList<>();
        assessmentRepository.findAll().forEach(assessment -> assessmentDTOs.add(convertToDTO(assessment)));
        return assessmentDTOs;
    }

    public AssessmentDTO createAssessment(AssessmentDTO assessmentDTO) {
        Assessment assessment = new Assessment();
        TeamMember teamMember = teamMemberRepository.findById(assessmentDTO.getTeamMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Team Member not found with ID: " + assessmentDTO.getTeamMemberId()));
        PreOperativeAssessment preOperativeAssessment = preOperativeAssessmentRepository
                .findByName(assessmentDTO.getPreOpAssessmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Pre-Op Assessment not found with name: " + assessmentDTO.getPreOpAssessmentId()));
        Patient patient = patientRepository.findById(assessmentDTO.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + assessmentDTO.getPatientId()));

        assessment.setAssessmentId(new AssessmentId(teamMember.getId(), patient.getId(), preOperativeAssessment.getName()));
        assessment.setTeamMember(teamMember);
        assessment.setPreOperativeAssessment(preOperativeAssessment);
        assessment.setPatient(patient);
        assessment.setStartDate(assessmentDTO.getStartDate());
        assessment = assessmentRepository.save(assessment);
        return convertToDTO(assessment);
    }

    public Optional<AssessmentDTO> updateAssessment(Long teamMemberId, Long patientId, String preOpName, AssessmentDTO assessmentDTO) {
        AssessmentId assessmentId = new AssessmentId(teamMemberId, patientId, preOpName);
        return assessmentRepository.findById(assessmentId).map(assessment -> {
            assessment.setStartDate(assessmentDTO.getStartDate());
            return convertToDTO(assessmentRepository.save(assessment));
        });
    }

    public boolean deleteAssessment(Long teamMemberId, Long patientId, String preOpName) {
        AssessmentId assessmentId = new AssessmentId(teamMemberId, patientId, preOpName);
        return assessmentRepository.findById(assessmentId).map(assessment -> {
            assessmentRepository.delete(assessment);
            return true;
        }).orElse(false);
    }


    private TeamMember fetchTeamMember(Long teamMemberId) {
        return teamMemberRepository.findById(teamMemberId).orElseThrow(() -> new NoSuchElementException("Team Member not found"));
    }

    private Patient fetchPatient(Long patientId) {
        return patientRepository.findById(patientId).orElseThrow(() -> new NoSuchElementException("Patient not found"));
    }

    private PreOperativeAssessment fetchPreOpAssessment(String preOpName) {
        return preOperativeAssessmentRepository.findByName(preOpName).orElseThrow(() -> new NoSuchElementException("Pre-Op Assessment not found"));
    }


    private AssessmentDTO convertToDTO(Assessment assessment) {
        TeamMemberDTO teamMemberDTO = new TeamMemberDTO(
                assessment.getTeamMember().getId(),
                assessment.getTeamMember().getName(),
                new OperationProviderDTO(assessment.getTeamMember().getOperationProvider().getType())
        );
        PreOperativeAssessmentDTO preOperativeAssessmentDTO = new PreOperativeAssessmentDTO(assessment.getPreOperativeAssessment().getName());
        PatientDTO patientDTO = new PatientDTO(assessment.getPatient().getId(), assessment.getPatient().getName(), assessment.getPatient().getNin());

        return new AssessmentDTO(
                assessment.getTeamMember().getId(),
                assessment.getPreOperativeAssessment().getName(),
                assessment.getPatient().getId(),
                teamMemberDTO,
                preOperativeAssessmentDTO,
                patientDTO,
                assessment.getStartDate()
        );
    }
}
