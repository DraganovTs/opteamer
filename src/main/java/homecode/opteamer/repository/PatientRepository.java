package homecode.opteamer.repository;

import homecode.opteamer.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository  extends CrudRepository<Patient, Long> {
}
