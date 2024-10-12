package homecode.opteamer.repository;

import homecode.opteamer.model.Asset;
import homecode.opteamer.model.enums.AssetType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends CrudRepository<Asset, Long> {

    Optional<Asset> findById(Long id);

    List<Asset> findByType(AssetType type);
}
