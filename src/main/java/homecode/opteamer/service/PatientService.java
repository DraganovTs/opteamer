package homecode.opteamer.service;

import homecode.opteamer.model.Patient;
import homecode.opteamer.model.dtos.PatientDTO;
import homecode.opteamer.repository.PatientRepository;
import homecode.opteamer.util.MapperUtility;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Optional<PatientDTO> getPatientById(Long id) {
        try {
            Patient patient = patientRepository.findById(id).orElse(null);
            return Optional.of(MapperUtility.mapEntityToDTO(patient, PatientDTO.class));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<PatientDTO> getAllPatients() {
        List<PatientDTO> patients = new ArrayList<>();
        Iterable<Patient> patientIterable = patientRepository.findAll();
        patientIterable.forEach(patient ->
                patients.add(MapperUtility.mapEntityToDTO(patient, PatientDTO.class))
        );
        return patients;
    }

    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = MapperUtility.mapDTOToEntity(patientDTO, Patient.class);
        patient = patientRepository.save(patient);
        return MapperUtility.mapEntityToDTO(patient, PatientDTO.class);
    }

    public Optional<PatientDTO> updatePatient(Long id, PatientDTO patientDTO) {
        return patientRepository.findById(id).map(patient -> {
            patient.setName(patientDTO.getName());
            patient.setNin(patientDTO.getNin());
            patientRepository.save(patient);
            return MapperUtility.mapEntityToDTO(patient, PatientDTO.class);
        });
    }

    public boolean deletePatient(Long id) {
        return patientRepository.findById(id).map(patient -> {
            patientRepository.delete(patient);
            return true;
        }).orElse(false);
    }

}
