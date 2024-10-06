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

    public OperationService(OperationRepository operationRepository, OperationTypeRepository operationTypeRepository, OperationRoomRepository operationRoomRepository, PatientRepository patientRepository, TeamMemberRepository teamMemberRepository) {
        this.operationRepository = operationRepository;
        this.operationTypeRepository = operationTypeRepository;
        this.operationRoomRepository = operationRoomRepository;
        this.patientRepository = patientRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    public Optional<OperationDTO> getOperationById(Long id) {
        try {
            Operation operation = operationRepository.findById(id).orElseThrow();
            return Optional.of(OperationMapper.INSTANCE.toOperationDTO(operation));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<OperationDTO> getAllOperations() {
        List<OperationDTO> OperationsDTOs = new ArrayList<>();
        Iterable<Operation> allOperations = operationRepository.findAll();
        allOperations.forEach(operation -> {
            OperationsDTOs.add(OperationMapper.INSTANCE.toOperationDTO(operation));
        });
        return OperationsDTOs;
    }

    public OperationDTO createOperation(OperationDTO operationDTO) {
        Operation operation = OperationMapper.INSTANCE.toOperation(operationDTO);
        setChildEntities(operationDTO, operation);

        boolean isValidIoTypeOperationProvider = operation.getOperationType().getOperationProviders().stream()
                .map(op -> op.getType().toString())
                .collect(Collectors.toSet()) // Use Set to avoid duplicates
                .containsAll(operation.getTeamMembers().stream()
                        .map(teamMember -> teamMember.getOperationProvider().getType().toString())
                        .collect(Collectors.toList()));

        if (!isValidIoTypeOperationProvider) {
            throw new InvalidOperationException("Based on operation type, the operation team is incomplete");
        }


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

        OperationType operationType = operationTypeRepository.findByName(operationDTO.getOperationTypeDTO().getName()).get();
        OperationRoom operationRoom = operationRoomRepository.findById(operationDTO.getOperationRoomDTO().getId()).get();
        Patient patient = patientRepository.findById(operationDTO.getPatientDTO().getId()).get();

        Set<TeamMember> teamMembers = operationDTO.getTeamMembersDTO().stream()
                .map(teamMemberDTO -> teamMemberRepository.findById(teamMemberDTO.getId())
                        .orElseThrow(NoSuchElementException::new))
                .collect(Collectors.toSet());

        operation.setOperationType(operationType);
        operation.setOperationRoom(operationRoom);
        operation.setPatient(patient);
        operation.setTeamMembers(teamMembers);

    }


}
