package homecode.opteamer.service;

import homecode.opteamer.model.PreOperativeAssessment;
import homecode.opteamer.model.dtos.PreOperativeAssessmentDTO;
import homecode.opteamer.repository.PreOperativeAssessmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PreOperativeAssessmentService {

    private final PreOperativeAssessmentRepository preOperativeAssessmentRepository;


    public PreOperativeAssessmentService(PreOperativeAssessmentRepository preOperativeAssessmentRepository) {
        this.preOperativeAssessmentRepository = preOperativeAssessmentRepository;
    }

    public Optional<PreOperativeAssessmentDTO> getPreOperativeAssessment(String id) {
        try {
            PreOperativeAssessment preOperativeAssessment = preOperativeAssessmentRepository.findById(id).orElseThrow();
            return Optional.of(mapEntityToDTO(preOperativeAssessment));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<PreOperativeAssessmentDTO> getAllPreOperativeAssessments() {
        List<PreOperativeAssessmentDTO> list = new ArrayList<>();
        Iterable<PreOperativeAssessment> allPreOperativeAssessments = preOperativeAssessmentRepository.findAll();
        allPreOperativeAssessments.forEach(preOperativeAssessment -> list.add(mapEntityToDTO(preOperativeAssessment)));
        return list;
    }

    public PreOperativeAssessmentDTO createPreOperativeAssessment(PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        PreOperativeAssessment preOperativeAssessment = new PreOperativeAssessment();
        preOperativeAssessment.setName(preOperativeAssessmentDTO.getName());
        PreOperativeAssessment preOperativeAssessmentSave = preOperativeAssessmentRepository.save(preOperativeAssessment);
        return mapEntityToDTO(preOperativeAssessmentSave);
    }

    public Optional<PreOperativeAssessmentDTO> updatePreOperativeAssessment(String name, PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        deletePreOperativeAssessment(name);
        return Optional.of(createPreOperativeAssessment(preOperativeAssessmentDTO));
    }


    public boolean deletePreOperativeAssessment(String name) {
        return preOperativeAssessmentRepository.findByName(name).map(preOperativeAssessment -> {
            preOperativeAssessmentRepository.delete(preOperativeAssessment);
            return true;
        }).orElse(false);
    }


    private PreOperativeAssessment mapDTOToEntity(PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(preOperativeAssessmentDTO, PreOperativeAssessment.class);
    }

    private PreOperativeAssessmentDTO mapEntityToDTO(PreOperativeAssessment preOperativeAssessment) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(preOperativeAssessment, PreOperativeAssessmentDTO.class);
    }
}
