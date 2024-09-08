package homecode.opteamer.repository;

import homecode.opteamer.model.Assessment;
import homecode.opteamer.model.embededIds.AssessmentId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssessmentRepository extends CrudRepository<Assessment, AssessmentId> {
    @Override
    Optional<Assessment> findById(AssessmentId assessmentId);
}
