package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.PreOperativeAssessment;
import homecode.opteamer.model.dtos.PreOperativeAssessmentDTO;
import homecode.opteamer.repository.PreOperativeAssessmentRepository;
import homecode.opteamer.util.MapperUtility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PreOperativeAssessmentService {

    private final PreOperativeAssessmentRepository preOperativeAssessmentRepository;

    public PreOperativeAssessmentService(PreOperativeAssessmentRepository preOperativeAssessmentRepository) {
        this.preOperativeAssessmentRepository = preOperativeAssessmentRepository;
    }

    public PreOperativeAssessmentDTO getPreOperativeAssessment(String name) {
        PreOperativeAssessment preOperativeAssessment = preOperativeAssessmentRepository.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException("Pre-operative assessment not found with name: " + name));
        return MapperUtility.mapEntityToDTO(preOperativeAssessment, PreOperativeAssessmentDTO.class);
    }

    public List<PreOperativeAssessmentDTO> getAllPreOperativeAssessments() {
        List<PreOperativeAssessmentDTO> list = new ArrayList<>();
        Iterable<PreOperativeAssessment> allPreOperativeAssessments = preOperativeAssessmentRepository.findAll();
        allPreOperativeAssessments.forEach(preOperativeAssessment ->
                list.add(MapperUtility.mapEntityToDTO(preOperativeAssessment, PreOperativeAssessmentDTO.class))
        );
        return list;
    }

    public PreOperativeAssessmentDTO createPreOperativeAssessment(PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        PreOperativeAssessment preOperativeAssessment = MapperUtility.mapDTOToEntity(preOperativeAssessmentDTO, PreOperativeAssessment.class);
        PreOperativeAssessment savedAssessment = preOperativeAssessmentRepository.save(preOperativeAssessment);
        return MapperUtility.mapEntityToDTO(savedAssessment, PreOperativeAssessmentDTO.class);
    }

    public PreOperativeAssessmentDTO updatePreOperativeAssessment(String name, PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        PreOperativeAssessment existingAssessment = preOperativeAssessmentRepository.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException("Pre-operative assessment not found with name: " + name));

        existingAssessment.setName(preOperativeAssessmentDTO.getName());
        PreOperativeAssessment updatedAssessment = preOperativeAssessmentRepository.save(existingAssessment);
        return MapperUtility.mapEntityToDTO(updatedAssessment, PreOperativeAssessmentDTO.class);
    }

    public void deletePreOperativeAssessment(String name) {
        PreOperativeAssessment assessment = preOperativeAssessmentRepository.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException("Pre-operative assessment not found with name: " + name));
        preOperativeAssessmentRepository.delete(assessment);
    }
}
