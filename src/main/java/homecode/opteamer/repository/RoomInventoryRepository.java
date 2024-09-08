package homecode.opteamer.repository;

import homecode.opteamer.model.RoomInventory;
import homecode.opteamer.model.embededIds.RoomInventoryId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomInventoryRepository extends CrudRepository<RoomInventory, RoomInventoryId> {
    Optional<RoomInventory> findById(RoomInventoryId roomInventoryId);
}
