package homecode.opteamer.repository;

import homecode.opteamer.model.Privilege;
import homecode.opteamer.model.enums.EPrivilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {
    Optional<Privilege> findByName(EPrivilege name);

}
