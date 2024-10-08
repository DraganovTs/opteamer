package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.mapper.OperationTypeMapper;
import homecode.opteamer.model.Asset;
import homecode.opteamer.model.OperationProvider;
import homecode.opteamer.model.OperationType;
import homecode.opteamer.model.PreOperativeAssessment;
import homecode.opteamer.model.dtos.OperationTypeDTO;
import homecode.opteamer.repository.AssetRepository;
import homecode.opteamer.repository.OperationProviderRepository;
import homecode.opteamer.repository.OperationTypeRepository;
import homecode.opteamer.repository.PreOperativeAssessmentRepository;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OperationTypeService {

    private final OperationTypeRepository operationTypeRepository;
    private final AssetRepository assetRepository;
    private final PreOperativeAssessmentRepository preOperativeAssessmentRepository;
    private final OperationProviderRepository operationProviderRepository;

    public OperationTypeService(OperationTypeRepository operationTypeRepository,
                                AssetRepository assetRepository,
                                PreOperativeAssessmentRepository preOperativeAssessmentRepository,
                                OperationProviderRepository operationProviderRepository) {
        this.operationTypeRepository = operationTypeRepository;
        this.assetRepository = assetRepository;
        this.preOperativeAssessmentRepository = preOperativeAssessmentRepository;
        this.operationProviderRepository = operationProviderRepository;
    }

    public OperationTypeDTO getOperationTypeByName(String name) {
        OperationType operationType = operationTypeRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Operation type not found with name: " + name));
        return OperationTypeMapper.INSTANCE.toOperationTypeDTO(operationType);
    }

    public List<OperationTypeDTO> getAllOperationTypes() {
        List<OperationType> allOperationTypes = (List<OperationType>) operationTypeRepository.findAll();
        return allOperationTypes.stream()
                .map(OperationTypeMapper.INSTANCE::toOperationTypeDTO)
                .collect(Collectors.toList());
    }

    public OperationTypeDTO createOperationType(@Valid OperationTypeDTO operationTypeDTO) {
        OperationType operationType = OperationTypeMapper.INSTANCE.toOperationType(operationTypeDTO);
        setChildEntities(operationTypeDTO, operationType);
        operationType = operationTypeRepository.save(operationType);
        return OperationTypeMapper.INSTANCE.toOperationTypeDTO(operationType);
    }

    public OperationTypeDTO updateOperationType(String name, @Valid OperationTypeDTO operationTypeDTO) {
        OperationType operationType = operationTypeRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Operation type not found with name: " + name));

        operationType.setName(operationTypeDTO.getName());
        operationType.setDurationHours(operationTypeDTO.getDurationHours());
        setChildEntities(operationTypeDTO, operationType);
        operationTypeRepository.save(operationType);
        return OperationTypeMapper.INSTANCE.toOperationTypeDTO(operationType);
    }

    public void deleteOperationType(String name) {
        OperationType operationType = operationTypeRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Operation type not found with name: " + name));
        operationTypeRepository.delete(operationType);
    }

    private void setChildEntities(OperationTypeDTO operationTypeDTO, OperationType operationType) {
        Set<Asset> assets = new HashSet<>();
        operationTypeDTO.getAssetDTOS().forEach(assetDTO -> {
            Asset asset = assetRepository.findById(assetDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + assetDTO.getId()));
            assets.add(asset);
        });

        Set<PreOperativeAssessment> preOperativeAssessments = operationTypeDTO.getPreOperativeAssessmentsDTO().stream()
                .map(preOperativeAssessmentDTO -> preOperativeAssessmentRepository.findByName(preOperativeAssessmentDTO.getName())
                        .orElseThrow(() -> new ResourceNotFoundException("Pre-operative assessment not found with name: " + preOperativeAssessmentDTO.getName())))
                .collect(Collectors.toSet());

        Set<OperationProvider> operationProviders = operationTypeDTO.getOperationProvidersDTO().stream()
                .map(operationProviderDTO -> operationProviderRepository.findById(operationProviderDTO.getType())
                        .orElseThrow(() -> new ResourceNotFoundException("Operation provider not found with type: " + operationProviderDTO.getType())))
                .collect(Collectors.toSet());

        operationType.setAssets(assets);
        operationType.setPreOperativeAssessments(preOperativeAssessments);
        operationType.setOperationProviders(operationProviders);
    }
}
