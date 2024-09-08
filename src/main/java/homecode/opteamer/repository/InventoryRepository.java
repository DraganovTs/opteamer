package homecode.opteamer.repository;

import homecode.opteamer.model.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory,Long> {
}
