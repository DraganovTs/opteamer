package homecode.opteamer.mapper;

import homecode.opteamer.model.Patient;
import homecode.opteamer.model.dtos.PatientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    PatientDTO PatientToPatientDTO(Patient patient);

    Patient PatientDTOToPatient(PatientDTO patientDTO);
}
