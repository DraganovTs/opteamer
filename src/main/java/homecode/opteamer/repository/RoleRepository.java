package homecode.opteamer.repository;

import homecode.opteamer.model.Role;
import homecode.opteamer.model.enums.ERole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

}
