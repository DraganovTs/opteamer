package homecode.opteamer.repository;

import homecode.opteamer.model.OperationRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRoomRepository extends CrudRepository<OperationRoom, Long> {
}
