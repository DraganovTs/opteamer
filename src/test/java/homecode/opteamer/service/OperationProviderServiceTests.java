package homecode.opteamer.service;

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

    }


}
