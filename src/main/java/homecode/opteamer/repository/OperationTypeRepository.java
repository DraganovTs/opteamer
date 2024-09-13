package homecode.opteamer.repository;

import homecode.opteamer.model.OperationType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationTypeRepository extends CrudRepository<OperationType, String> {

    Optional<OperationType> findByName(String name);
}
