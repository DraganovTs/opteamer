package homecode.opteamer.repository;

import homecode.opteamer.model.OperationProvider;
import homecode.opteamer.model.enums.OperationProviderType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationProviderRepository extends CrudRepository<OperationProvider, OperationProviderType> {
    Optional<OperationProvider> findByType(OperationProviderType type);
}
