package homecode.opteamer.repository;

import homecode.opteamer.model.OperationProvider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationProviderRepository extends CrudRepository<OperationProvider, Long> {
}
