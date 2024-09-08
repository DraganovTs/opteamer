package homecode.opteamer.repository;

import homecode.opteamer.model.TeamMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository  extends CrudRepository<TeamMember, Long> {
}
