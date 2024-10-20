package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.mapper.OperationReportMapper;
import homecode.opteamer.model.Operation;
import homecode.opteamer.model.OperationReport;
import homecode.opteamer.model.TeamMember;
import homecode.opteamer.model.dtos.OperationReportDTO;
import homecode.opteamer.model.embededIds.OperationReportId;
import homecode.opteamer.repository.OperationReportRepository;
import homecode.opteamer.repository.OperationRepository;
import homecode.opteamer.repository.TeamMemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OperationReportService {

    private final OperationReportRepository operationReportRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final OperationRepository operationRepository;

    public OperationReportService(OperationReportRepository operationReportRepository,
                                  TeamMemberRepository teamMemberRepository,
                                  OperationRepository operationRepository) {
        this.operationReportRepository = operationReportRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.operationRepository = operationRepository;
    }

    public Optional<OperationReportDTO> findById(Long teamMemberId, Long operationId) {
        OperationReportId operationReportId = new OperationReportId(teamMemberId, operationId);
        OperationReport operationReport = operationReportRepository.findById(operationReportId).orElse(null);

        if (operationReport == null) {
            return Optional.empty();
        }

        return Optional.of(OperationReportMapper.INSTANCE.toOperationReportDTO(operationReport));
    }


    public List<OperationReportDTO> findAll() {
        List<OperationReportDTO> operationReportDTOS = new ArrayList<>();
        Iterable<OperationReport> operationReports = operationReportRepository.findAll();
        operationReports.forEach(operationReport -> {
            operationReportDTOS.add(OperationReportMapper.INSTANCE.toOperationReportDTO(operationReport));
        });
        return operationReportDTOS;
    }

    public OperationReportDTO createOperationReport(OperationReportDTO operationReportDTO) {
        TeamMember teamMember = teamMemberRepository.findById(operationReportDTO.getTeamMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Team member not found"));
        Operation operation = operationRepository.findById(operationReportDTO.getOperationId())
                .orElseThrow(() -> new ResourceNotFoundException("Operation not found"));

        OperationReport operationReport = new OperationReport();
        operationReport.setOperationReportId(new OperationReportId(teamMember.getId(), operation.getId()));
        operationReport.setTeamMember(teamMember);
        operationReport.setOperation(operation);
        operationReport.setReport(operationReportDTO.getReport());

        operationReport = operationReportRepository.save(operationReport);
        return OperationReportMapper.INSTANCE.toOperationReportDTO(operationReport);
    }


    public Optional<OperationReportDTO> updateOperationReport(Long teamMemberId, Long operationId, OperationReportDTO operationReportDTO) {
        OperationReportId operationReportId = new OperationReportId(teamMemberId, operationId);
        return operationReportRepository.findById(operationReportId).map(operationReport -> {
            operationReport.setReport(operationReportDTO.getReport());
            operationReportRepository.save(operationReport);
            return OperationReportMapper.INSTANCE.toOperationReportDTO(operationReport);
        });
    }

    public boolean deleteOperationReport(Long teamMemberId, Long operationId) {
        OperationReportId operationReportId = new OperationReportId(teamMemberId, operationId);
        return operationReportRepository.findById(operationReportId).map(operationReport -> {
            operationReportRepository.delete(operationReport);
            return true;
        }).orElse(false);
    }
}
