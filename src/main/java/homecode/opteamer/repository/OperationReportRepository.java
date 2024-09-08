package homecode.opteamer.repository;

import homecode.opteamer.model.OperationReport;
import homecode.opteamer.model.embededIds.OperationReportId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationReportRepository extends CrudRepository<OperationReport, OperationReportId> {
    Optional<OperationReport> findById(OperationReportId operationReportId);
}
