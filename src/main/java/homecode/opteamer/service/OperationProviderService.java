package homecode.opteamer.service;

import homecode.opteamer.model.OperationProvider;
import homecode.opteamer.model.dtos.OperationProviderDTO;
import homecode.opteamer.model.enums.OperationProviderType;
import homecode.opteamer.repository.OperationProviderRepository;
import org.modelmapper.ModelMapper;
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
            return Optional.of(mapEntityToDTO(operationProvider));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }


    public List<OperationProviderDTO> getAllOperationProviders() {
        List<OperationProviderDTO> operationProviderDTOList = new ArrayList<>();
        operationProviderRepository.findAll().forEach(operationProvider -> {
            operationProviderDTOList.add(mapEntityToDTO(operationProvider));
        });
        return operationProviderDTOList;
    }

    public OperationProviderDTO createOperationProvider(OperationProviderDTO operationProviderDTO) {
        OperationProvider operationProvider = mapDTOToEntity(operationProviderDTO);
        operationProvider = operationProviderRepository.save(operationProvider);
        return mapEntityToDTO(operationProvider);
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


    private OperationProvider mapDTOToEntity(OperationProviderDTO operationProviderDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(operationProviderDTO, OperationProvider.class);
    }

    private OperationProviderDTO mapEntityToDTO(OperationProvider operationProvider) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(operationProvider, OperationProviderDTO.class);
    }
}
