package homecode.opteamer.service;

import homecode.opteamer.model.PreOperativeAssessment;
import homecode.opteamer.model.dtos.PreOperativeAssessmentDTO;
import homecode.opteamer.repository.PreOperativeAssessmentRepository;
import homecode.opteamer.util.MapperUtility;
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
            return Optional.of(MapperUtility.mapEntityToDTO(preOperativeAssessment, PreOperativeAssessmentDTO.class));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<PreOperativeAssessmentDTO> getAllPreOperativeAssessments() {
        List<PreOperativeAssessmentDTO> list = new ArrayList<>();
        Iterable<PreOperativeAssessment> allPreOperativeAssessments = preOperativeAssessmentRepository.findAll();
        allPreOperativeAssessments.forEach(preOperativeAssessment -> list.add(MapperUtility.mapEntityToDTO(preOperativeAssessment, PreOperativeAssessmentDTO.class)));
        return list;
    }

    public PreOperativeAssessmentDTO createPreOperativeAssessment(PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        PreOperativeAssessment preOperativeAssessment = MapperUtility.mapDTOToEntity(preOperativeAssessmentDTO, PreOperativeAssessment.class);
        PreOperativeAssessment preOperativeAssessmentSave = preOperativeAssessmentRepository.save(preOperativeAssessment);
        return MapperUtility.mapEntityToDTO(preOperativeAssessmentSave,PreOperativeAssessmentDTO.class);
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

}
