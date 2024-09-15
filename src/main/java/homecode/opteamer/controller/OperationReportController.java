package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.OperationReportDTO;
import homecode.opteamer.service.OperationReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/operationReport")
public class OperationReportController {

    private final OperationReportService operationReportService;

    public OperationReportController(OperationReportService operationReportService) {
        this.operationReportService = operationReportService;
    }

    @GetMapping("/{teamMemberId}/{operationId}")
    public ResponseEntity<OperationReportDTO> getOperationReportById(@PathVariable Long teamMemberId,
                                                                     @PathVariable Long operationId) {
        Optional<OperationReportDTO> operationReportDTOOptional = operationReportService.findById(teamMemberId, operationId);
        return operationReportDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @GetMapping
    public ResponseEntity<List<OperationReportDTO>> getAllOperationReports() {
        List<OperationReportDTO> operationReportDTOList = operationReportService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(operationReportDTOList);
    }


    @PostMapping
    public ResponseEntity<OperationReportDTO> createOperationReport(@RequestBody OperationReportDTO operationReportDTO) {
        OperationReportDTO operationReport = operationReportService.createOperationReport(operationReportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(operationReport);
    }

    @PutMapping("/{teamMemberId}/{operationId}")
    public ResponseEntity<OperationReportDTO> updateOperationReport(@PathVariable Long teamMemberId,
                                                                    @PathVariable Long operationId,
                                                                    @RequestBody OperationReportDTO operationReportDTO) {

        Optional<OperationReportDTO> operationReportDTOOptional = operationReportService.updateOperationReport(teamMemberId,
                operationId, operationReportDTO);
        return operationReportDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{teamMemberId}/{operationId}")
    public ResponseEntity<Void> deleteOperationReport(@PathVariable Long teamMemberId,
                                                      @PathVariable Long operationId) {
        boolean isDeleted = operationReportService.deleteOperationReport(teamMemberId, operationId);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
