package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.Patient;
import homecode.opteamer.model.dtos.PatientDTO;
import homecode.opteamer.repository.PatientRepository;
import homecode.opteamer.util.MapperUtility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        return MapperUtility.mapEntityToDTO(patient, PatientDTO.class);
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

    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        patient.setName(patientDTO.getName());
        patient.setNin(patientDTO.getNin());
        patient = patientRepository.save(patient);
        return MapperUtility.mapEntityToDTO(patient, PatientDTO.class);
    }

    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        patientRepository.delete(patient);
    }
}
