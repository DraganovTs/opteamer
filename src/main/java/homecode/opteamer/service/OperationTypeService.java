package homecode.opteamer.service;

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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OperationTypeService {

    private final OperationTypeRepository operationTypeRepository;
    private final AssetRepository assetRepository;
    private final PreOperativeAssessmentRepository preOperativeAssessmentRepository;
    private final OperationProviderRepository operationProviderRepository;


    public OperationTypeService(OperationTypeRepository operationTypeRepository, AssetRepository assetRepository, PreOperativeAssessmentRepository preOperativeAssessmentRepository, OperationProviderRepository operationProviderRepository) {
        this.operationTypeRepository = operationTypeRepository;
        this.assetRepository = assetRepository;
        this.preOperativeAssessmentRepository = preOperativeAssessmentRepository;
        this.operationProviderRepository = operationProviderRepository;
    }

    public Optional<OperationTypeDTO> getOperationTypeById(String name) {
        try {
            OperationType operationType = operationTypeRepository.findByName(name).orElseThrow();
            return Optional.of(OperationTypeMapper.INSTANCE.toOperationTypeDTO(operationType));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }


    public List<OperationTypeDTO> getAllOperationTypes() {
        List<OperationTypeDTO> list = new ArrayList<>();
        Iterable<OperationType> allOperationTypes = operationTypeRepository.findAll();
        allOperationTypes.forEach(operationTypes -> {
            list.add(OperationTypeMapper.INSTANCE.toOperationTypeDTO(operationTypes));
        });
        return list;
    }

    public OperationTypeDTO createOperationType(OperationTypeDTO operationTypeDTO) {

        if (operationTypeDTO.getName() == null || operationTypeDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("OperationType name cannot be null or empty");
        }

        OperationType operationType = OperationTypeMapper.INSTANCE.toOperationType(operationTypeDTO);

        setChildEntities(operationTypeDTO, operationType);
        operationType = operationTypeRepository.save(operationType);
        return OperationTypeMapper.INSTANCE.toOperationTypeDTO(operationType);
    }

    public Optional<OperationTypeDTO> updateOperationType(String name, OperationTypeDTO operationTypeDTO) {
        return operationTypeRepository.findByName(name).map(operationType -> {
            operationType.setName(operationTypeDTO.getName());
            operationType.setName(operationTypeDTO.getName());
            operationType.setDurationHours(operationTypeDTO.getDurationHours());
            setChildEntities(operationTypeDTO, operationType);
            operationTypeRepository.save(operationType);
            return OperationTypeMapper.INSTANCE.toOperationTypeDTO(operationType);
        });
    }


    public boolean deleteOperationType(String name) {
        return operationTypeRepository.findByName(name).map(operationType -> {
            operationTypeRepository.delete(operationType);
            return true;
        }).orElse(false);
    }


    private void setChildEntities(OperationTypeDTO operationTypeDTO, OperationType operationType) {
        Set<Asset> assets = new HashSet<>();
        operationTypeDTO.getAssetDTOS().forEach(assetDTO -> {
            Asset asset = assetRepository.findById(assetDTO.getId()).get();
            assets.add(asset);
        });

        Set<PreOperativeAssessment> preOperativeAssessments = operationTypeDTO.getPreOperativeAssessmentsDTO().stream()
                .map(preOperativeAssessmentDTO -> preOperativeAssessmentRepository.findByName(preOperativeAssessmentDTO.getName())
                        .orElseThrow(NoSuchElementException::new))
                .collect(Collectors.toSet());
        Set<OperationProvider> operationProviders = operationTypeDTO.getOperationProvidersDTO().stream()
                .map(operationProviderDTO -> operationProviderRepository.findById(operationProviderDTO.getType())
                        .orElseThrow(NoSuchElementException::new))
                .collect(Collectors.toSet());

        operationType.setAssets(assets);
        operationType.setPreOperativeAssessments(preOperativeAssessments);
        operationType.setOperationProviders(operationProviders);
    }
}
