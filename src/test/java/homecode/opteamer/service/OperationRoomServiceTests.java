package homecode.opteamer.service;

import homecode.opteamer.model.OperationRoom;
import homecode.opteamer.model.dtos.OperationRoomDTO;
import homecode.opteamer.model.enums.OperationRoomState;
import homecode.opteamer.model.enums.OperationRoomType;
import homecode.opteamer.repository.OperationRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperationRoomServiceTests {

    @Mock
    private OperationRoomRepository  operationRoomRepository;

    @InjectMocks
    private OperationRoomService operationRoomService;

    OperationRoom operationRoom;
    OperationRoom operationRoom2;
    OperationRoomDTO operationRoomDTO;


    @BeforeEach
    public void setUp() {
        operationRoom = new OperationRoom();
        operationRoom.setRoomNr(101);
        operationRoom.setBuildingBlock("A");
        operationRoom.setFloor("33");
        operationRoom.setType(OperationRoomType.EMERGENCY_SURGERY);
        operationRoom.setState(OperationRoomState.MAINTENANCE);

        operationRoom2 = new OperationRoom();
        operationRoom2.setRoomNr(101);
        operationRoom2.setBuildingBlock("A");
        operationRoom2.setFloor("33");
        operationRoom2.setType(OperationRoomType.EMERGENCY_SURGERY);
        operationRoom2.setState(OperationRoomState.MAINTENANCE);

        operationRoomDTO = new OperationRoomDTO();
        operationRoomDTO.setRoomNr(202);
        operationRoomDTO.setBuildingBlock("B");
        operationRoomDTO.setFloor("66");
        operationRoomDTO.setType(OperationRoomType.EMERGENCY_SURGERY);
        operationRoomDTO.setState(OperationRoomState.MAINTENANCE);
    }


    @Test
    void createOperationRoom_ShouldSaveAndReturnOperationRoomDTO() {
        when(operationRoomRepository.save(any(OperationRoom.class))).thenReturn(operationRoom);

        OperationRoomDTO savedOperationRoomDTO = operationRoomService.createOperationRoom(operationRoomDTO);

        assertNotNull(savedOperationRoomDTO);
        assertEquals(operationRoom.getRoomNr(), savedOperationRoomDTO.getRoomNr());
        verify(operationRoomRepository,times(1)).save(any(OperationRoom.class));

    }

    @Test
    void updateOperationRoom_ShouldSaveAndReturnOperationRoomDTO() {
        when(operationRoomRepository.save(any(OperationRoom.class))).thenReturn(operationRoom);
        when(operationRoomRepository.findById(anyLong())).thenReturn(Optional.of(operationRoom));

        operationRoomDTO.setRoomNr(333);

        Optional<OperationRoomDTO> savedOperationRoomDTO = operationRoomService.updateOperationRoom(1L,operationRoomDTO);

        assertNotNull(savedOperationRoomDTO);
        assertEquals(operationRoom.getRoomNr(), savedOperationRoomDTO.get().getRoomNr());
        verify(operationRoomRepository,times(1)).save(any(OperationRoom.class));
        verify(operationRoomRepository,times(1)).findById(anyLong());
    }

    @Test
    void getOperationRoom_ShouldReturnOperationRoomDTO() {
        when(operationRoomRepository.findById(anyLong())).thenReturn(Optional.of(operationRoom));

        Optional<OperationRoomDTO> foundOperationRoomDTO = operationRoomService.getOperationRoomById(1l);

        assertNotNull(foundOperationRoomDTO);
        verify(operationRoomRepository,times(1)).findById(anyLong());
    }

    @Test
    void getAllOperationRooms_ShouldReturnListOfOperationRoomDTOs() {
        when(operationRoomRepository.findAll()).thenReturn(Arrays.asList(operationRoom,operationRoom2));

        List<OperationRoomDTO> foundOperationRoomDTOs = operationRoomService.getAllOperationRooms();

        assertEquals(foundOperationRoomDTOs.size(), 2);
        assertEquals(foundOperationRoomDTOs.get(0).getRoomNr(), operationRoom.getRoomNr());
        assertEquals(foundOperationRoomDTOs.get(1).getRoomNr(), operationRoom2.getRoomNr());

        verify(operationRoomRepository,times(1)).findAll();
    }

    @Test
    void deleteOperationRoom_ShouldDeleteOperationRoomDTO() {
        when(operationRoomRepository.findById(anyLong())).thenReturn(Optional.of(operationRoom));
        doNothing().when(operationRoomRepository).delete(any(OperationRoom.class));

        operationRoomService.deleteOperationRoomById(1L);

        verify(operationRoomRepository,times(1)).findById(anyLong());
        verify(operationRoomRepository,times(1)).delete(any(OperationRoom.class));
    }
}
