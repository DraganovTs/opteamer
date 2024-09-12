package homecode.opteamer.service;

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

    public Optional<OperationProviderDTO> getOperationProviderById(String id) {
        try {
            OperationProvider operationProvider = operationProviderRepository.findById(OperationProviderType.valueOf(id)).orElse(null);
            return Optional.of(MapperUtility.mapEntityToDTO(operationProvider , OperationProviderDTO.class));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }


    public List<OperationProviderDTO> getAllOperationProviders() {
        List<OperationProviderDTO> operationProviderDTOList = new ArrayList<>();
        operationProviderRepository.findAll().forEach(operationProvider ->
            operationProviderDTOList.add(MapperUtility.mapEntityToDTO(operationProvider , OperationProviderDTO.class))
        );
        return operationProviderDTOList;
    }

    public OperationProviderDTO createOperationProvider(OperationProviderDTO operationProviderDTO) {
        OperationProvider operationProvider = MapperUtility.mapDTOToEntity(operationProviderDTO ,  OperationProvider.class);
        operationProvider = operationProviderRepository.save(operationProvider);
        return MapperUtility.mapEntityToDTO(operationProvider , OperationProviderDTO.class);
    }

    public Optional<OperationProviderDTO> updateOperationProvider(String id, OperationProviderDTO operationProviderDTO) {
        deleteOperationProvider(id);
        return Optional.of(createOperationProvider(operationProviderDTO));
    }


    public boolean deleteOperationProvider(String id) {
        return operationProviderRepository.findById(OperationProviderType.valueOf(id)).map(operationProvider -> {
            operationProviderRepository.deleteById(OperationProviderType.valueOf(id));
            return true;
        }).orElse(false);
    }

}
