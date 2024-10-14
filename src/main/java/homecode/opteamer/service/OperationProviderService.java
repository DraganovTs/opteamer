package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.OperationProvider;
import homecode.opteamer.model.dtos.OperationProviderDTO;
import homecode.opteamer.model.enums.OperationProviderType;
import homecode.opteamer.repository.OperationProviderRepository;
import homecode.opteamer.util.MapperUtility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OperationProviderService {

    private final OperationProviderRepository operationProviderRepository;

    public OperationProviderService(OperationProviderRepository operationProviderRepository) {
        this.operationProviderRepository = operationProviderRepository;
    }

    public OperationProviderDTO getOperationProviderById(String type) {
        OperationProvider operationProvider = operationProviderRepository.findByType(OperationProviderType.valueOf(type))
                .orElseThrow(() -> new ResourceNotFoundException("Operation Provider whit type " + type + "was not found"));
        return MapperUtility.mapEntityToDTO(operationProvider, OperationProviderDTO.class);
    }


    public List<OperationProviderDTO> getAllOperationProviders() {
        List<OperationProviderDTO> operationProviderDTOList = new ArrayList<>();
        operationProviderRepository.findAll().forEach(operationProvider ->
                operationProviderDTOList.add(MapperUtility.mapEntityToDTO(operationProvider, OperationProviderDTO.class))
        );
        return operationProviderDTOList;
    }

    public OperationProviderDTO createOperationProvider(OperationProviderDTO operationProviderDTO) {
        OperationProvider operationProvider = MapperUtility.mapDTOToEntity(operationProviderDTO, OperationProvider.class);
        operationProvider = operationProviderRepository.save(operationProvider);
        return MapperUtility.mapEntityToDTO(operationProvider, OperationProviderDTO.class);
    }

    public OperationProviderDTO updateOperationProvider(String id, OperationProviderDTO operationProviderDTO) {
        OperationProvider existingProvider = operationProviderRepository.findByType(OperationProviderType.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("Operation Provider not found for type: " + id));

        existingProvider.setType(operationProviderDTO.getType());
        OperationProvider updatedProvider = operationProviderRepository.save(existingProvider);
        return MapperUtility.mapEntityToDTO(updatedProvider, OperationProviderDTO.class);
    }



    public boolean deleteOperationProvider(String id) {
        return operationProviderRepository.findByType(OperationProviderType.valueOf(id)).map(operationProvider -> {
            operationProviderRepository.deleteById(OperationProviderType.valueOf(id));
            return true;
        }).orElse(false);
    }

}
