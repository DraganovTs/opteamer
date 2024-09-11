package homecode.opteamer.service;

import homecode.opteamer.model.Patient;
import homecode.opteamer.model.dtos.PatientDTO;
import homecode.opteamer.repository.PatientRepository;
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
            return Optional.of(mapEntityToDTO(patient));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<PatientDTO> getAllPatients() {
        List<PatientDTO> patients = new ArrayList<>();
        Iterable<Patient> patientIterable = patientRepository.findAll();
        patientIterable.forEach(patient -> {
            patients.add(mapEntityToDTO(patient));
        });
        return patients;
    }

    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = mapDTOToEntity(patientDTO);
        patient = patientRepository.save(patient);
        return mapEntityToDTO(patient);
    }

    public Optional<PatientDTO> updatePatient(Long id, PatientDTO patientDTO) {
        return patientRepository.findById(id).map(patient -> {
            patient.setName(patientDTO.getName());
            patient.setNin(patientDTO.getNin());
            patientRepository.save(patient);
            return mapEntityToDTO(patient);
        });
    }

    public boolean deletePatient(Long id) {
        return patientRepository.findById(id).map(patient -> {
            patientRepository.delete(patient);
            return true;
        }).orElse(false);
    }


    private Patient mapDTOToEntity(PatientDTO patientDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(patientDTO, Patient.class);
    }

    private PatientDTO mapEntityToDTO(Patient patient) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(patient, PatientDTO.class);
    }
}
