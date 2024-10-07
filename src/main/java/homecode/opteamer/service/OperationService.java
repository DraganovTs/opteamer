package homecode.opteamer.service;

import homecode.opteamer.exception.InvalidOperationException;
import homecode.opteamer.mapper.OperationMapper;
import homecode.opteamer.model.*;
import homecode.opteamer.model.dtos.OperationDTO;
import homecode.opteamer.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OperationService {

    private final OperationRepository operationRepository;
    private final OperationTypeRepository operationTypeRepository;
    private final OperationRoomRepository operationRoomRepository;
    private final PatientRepository patientRepository;
    private final TeamMemberRepository teamMemberRepository;

    public OperationService(OperationRepository operationRepository,
                            OperationTypeRepository operationTypeRepository,
                            OperationRoomRepository operationRoomRepository,
                            PatientRepository patientRepository,
                            TeamMemberRepository teamMemberRepository) {
        this.operationRepository = operationRepository;
        this.operationTypeRepository = operationTypeRepository;
        this.operationRoomRepository = operationRoomRepository;
        this.patientRepository = patientRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    public Optional<OperationDTO> getOperationById(Long id) {
        return operationRepository.findById(id)
                .map(OperationMapper.INSTANCE::toOperationDTO);
    }

    public List<OperationDTO> getAllOperations() {
        return ((List<Operation>) operationRepository.findAll())
                .stream()
                .map(OperationMapper.INSTANCE::toOperationDTO)
                .collect(Collectors.toList());
    }

    public OperationDTO createOperation(OperationDTO operationDTO) {
        Operation operation = OperationMapper.INSTANCE.toOperation(operationDTO);
        setChildEntities(operationDTO, operation);

        validateOperationTeam(operation);

        Operation savedOperation = operationRepository.save(operation);
        return OperationMapper.INSTANCE.toOperationDTO(savedOperation);
    }

    public Optional<OperationDTO> updateOperation(Long id, OperationDTO operationDTO) {
        return operationRepository.findById(id).map(operation -> {
            operation.setState(operationDTO.getState());
            operation.setStartDate(operationDTO.getStartDate());
            setChildEntities(operationDTO, operation);
            operationRepository.save(operation);
            return OperationMapper.INSTANCE.toOperationDTO(operation);
        });
    }

    public boolean deleteOperationById(Long id) {
        return operationRepository.findById(id).map(operation -> {
            operationRepository.delete(operation);
            return true;
        }).orElse(false);
    }

    private void setChildEntities(OperationDTO operationDTO, Operation operation) {
        OperationType operationType = operationTypeRepository.findByName(operationDTO.getOperationTypeDTO().getName())
                .orElseThrow(() -> new NoSuchElementException("Operation type not found"));
        OperationRoom operationRoom = operationRoomRepository.findById(operationDTO.getOperationRoomDTO().getId())
                .orElseThrow(() -> new NoSuchElementException("Operation room not found"));
        Patient patient = patientRepository.findById(operationDTO.getPatientDTO().getId())
                .orElseThrow(() -> new NoSuchElementException("Patient not found"));

        Set<TeamMember> teamMembers = operationDTO.getTeamMembersDTO().stream()
                .map(teamMemberDTO -> teamMemberRepository.findById(teamMemberDTO.getId())
                        .orElseThrow(() -> new NoSuchElementException("Team member not found")))
                .collect(Collectors.toSet());

        operation.setOperationType(operationType);
        operation.setOperationRoom(operationRoom);
        operation.setPatient(patient);
        operation.setTeamMembers(teamMembers);
    }

    private void validateOperationTeam(Operation operation) {
        boolean isValidOperationTeam = operation.getOperationType().getOperationProviders().stream()
                .map(op -> op.getType().toString())
                .collect(Collectors.toSet())
                .containsAll(operation.getTeamMembers().stream()
                        .map(teamMember -> teamMember.getOperationProvider().getType().toString())
                        .collect(Collectors.toList()));

        if (!isValidOperationTeam) {
            throw new InvalidOperationException("Based on operation type, the operation team is incomplete");
        }
    }
}
