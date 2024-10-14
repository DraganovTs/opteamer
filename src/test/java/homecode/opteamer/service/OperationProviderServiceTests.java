package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.OperationProvider;
import homecode.opteamer.model.dtos.OperationProviderDTO;
import homecode.opteamer.model.enums.OperationProviderType;
import homecode.opteamer.repository.OperationProviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperationProviderServiceTests {

    @Mock
    private OperationProviderRepository operationProviderRepository;

    @InjectMocks
    private OperationProviderService operationProviderService;

     OperationProvider operationProvider;
     OperationProviderDTO operationProviderDTO;


    @BeforeEach
    public void setUp() {

        operationProvider = new OperationProvider();
        operationProvider.setType(OperationProviderType.ANESTHESIOLOGIST);

        operationProviderDTO = new OperationProviderDTO();
        operationProviderDTO.setType(OperationProviderType.ANESTHESIOLOGIST);

    }


    @Test
    void createOperationProvider_ShouldSaveAndReturnOperationProviderDTO() {
        when(operationProviderRepository.save(any(OperationProvider.class))).thenReturn(operationProvider);

        OperationProviderDTO savedOperationProviderDTO = operationProviderService.createOperationProvider(operationProviderDTO);

        assertNotNull(savedOperationProviderDTO);
        assertEquals(operationProvider.getType(), savedOperationProviderDTO.getType());
        verify(operationProviderRepository,times(1)).save(any(OperationProvider.class));

    }

    @Test
    void getOperationProvider_ShouldReturnOperationProviderDTO() {
        when(operationProviderRepository.findByType(any(OperationProviderType.class))).thenReturn(Optional.of(operationProvider));

        OperationProviderDTO foundOperationProvider = operationProviderService.getOperationProviderById("ANESTHESIOLOGIST");

        assertNotNull(foundOperationProvider);
        assertEquals(operationProviderDTO.getType(), foundOperationProvider.getType());
        verify(operationProviderRepository,times(1)).findByType(any(OperationProviderType.class));
    }

    @Test
    void getOperationProvider_ShouldReturnNotFoundIfOperationProviderNotFound() {
        when(operationProviderRepository.findByType(any(OperationProviderType.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,()->{
            operationProviderService.getOperationProviderById("ANESTHESIOLOGIST");
        });

        verify(operationProviderRepository,times(1)).findByType(any(OperationProviderType.class));
    }

    @Test
    void updateOperationProvider_ShouldUpdateAndSaveOperationProviderDTO() {
        when(operationProviderRepository.findByType(any(OperationProviderType.class))).thenReturn(Optional.of(operationProvider));
        when(operationProviderRepository.save(any(OperationProvider.class))).thenReturn(operationProvider);

       operationProviderDTO.setType(OperationProviderType.CP_ROOM_NURSE);

        OperationProviderDTO updatedOperationProviderDTO = operationProviderService.updateOperationProvider("ANESTHESIOLOGIST",
                operationProviderDTO);

        assertNotNull(updatedOperationProviderDTO);
        assertEquals(operationProviderDTO.getType(), updatedOperationProviderDTO.getType());
        verify(operationProviderRepository,times(1)).findByType(any(OperationProviderType.class));
        verify(operationProviderRepository,times(1)).save(any(OperationProvider.class));
    }

    @Test
    void deleteOperationProvider_ShouldDeleteOperationProviderDTO() {
        when(operationProviderRepository.findByType(any(OperationProviderType.class))).thenReturn(Optional.of(operationProvider));
        doNothing().when(operationProviderRepository).deleteById(any(OperationProviderType.class));

        operationProviderService.deleteOperationProvider("ANESTHESIOLOGIST");

        verify(operationProviderRepository,times(1)).findByType(any(OperationProviderType.class));
        verify(operationProviderRepository,times(1)).deleteById(any(OperationProviderType.class));
    }

    @Test
    void deleteOperationProvider_ShouldReturnFalseIfOperationProviderNotFound() {
        when(operationProviderRepository.findByType(any(OperationProviderType.class))).thenReturn(Optional.empty());

        boolean result = operationProviderService.deleteOperationProvider("ANESTHESIOLOGIST");

        assertFalse(result);

        verify(operationProviderRepository, times(1)).findByType(any(OperationProviderType.class));
    }



}
