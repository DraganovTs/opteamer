package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.Asset;
import homecode.opteamer.model.OperationRoom;
import homecode.opteamer.model.RoomInventory;
import homecode.opteamer.model.dtos.AssetDTO;
import homecode.opteamer.model.dtos.OperationRoomDTO;
import homecode.opteamer.model.dtos.RoomInventoryDTO;
import homecode.opteamer.model.embededIds.RoomInventoryId;
import homecode.opteamer.model.enums.AssetType;
import homecode.opteamer.model.enums.OperationRoomState;
import homecode.opteamer.model.enums.OperationRoomType;
import homecode.opteamer.repository.AssetRepository;
import homecode.opteamer.repository.OperationRoomRepository;
import homecode.opteamer.repository.RoomInventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomInventoryServiceTests {

    @Mock
    private RoomInventoryRepository roomInventoryRepository;
    @Mock
    private AssetRepository assetRepository;
    @Mock
    private OperationRoomRepository operationRoomRepository;

    @InjectMocks
    private RoomInventoryService roomInventoryService;

    RoomInventory roomInventory;
    RoomInventory roomInventory2;
    RoomInventoryDTO roomInventoryDTO;
    Asset asset;
    AssetDTO assetDTO;
    OperationRoom operationRoom;
    OperationRoomDTO operationRoomDTO;
    RoomInventoryId roomInventoryId;

    @BeforeEach
    public void setUp() {
        operationRoom = new OperationRoom();
        operationRoom.setId(2L);
        operationRoom.setRoomNr(101);
        operationRoom.setBuildingBlock("A");
        operationRoom.setFloor("33");
        operationRoom.setType(OperationRoomType.EMERGENCY_SURGERY);
        operationRoom.setState(OperationRoomState.MAINTENANCE);

        operationRoomDTO = new OperationRoomDTO();
        operationRoomDTO.setId(2L);
        operationRoomDTO.setRoomNr(202);
        operationRoomDTO.setBuildingBlock("B");
        operationRoomDTO.setFloor("66");
        operationRoomDTO.setType(OperationRoomType.EMERGENCY_SURGERY);
        operationRoomDTO.setState(OperationRoomState.MAINTENANCE);

        asset = new Asset();
        asset.setId(1L);
        asset.setType(AssetType.MACHINE);
        asset.setName("Test Asset");

        assetDTO = new AssetDTO();
        assetDTO.setId(1L);
        assetDTO.setType(AssetType.EQUIPMENT);
        assetDTO.setName("Test DTO Asset");

        roomInventoryId = new RoomInventoryId();
        roomInventoryId.setAssetId(1L);
        roomInventoryId.setRoomId(2L);

        roomInventory2 = new RoomInventory();
        roomInventory2.setRoomInventoryId(roomInventoryId);
        roomInventory2.setAsset(asset);
        roomInventory2.setOperationRoom(operationRoom);
        roomInventory2.setCount(11);

        roomInventory = new RoomInventory();
        roomInventory.setRoomInventoryId(roomInventoryId);
        roomInventory.setAsset(asset);
        roomInventory.setOperationRoom(operationRoom);
        roomInventory.setCount(22);

        roomInventoryDTO = new RoomInventoryDTO();
        roomInventoryDTO.setOperationRoomId(1L);
        roomInventoryDTO.setAssetDTO(assetDTO);
        roomInventoryDTO.setOperationRoomDTO(operationRoomDTO);
        roomInventoryDTO.setCount(22);
    }

    @Test
    void createRoomInventory_ShouldSaveAndReturnRoomInventoryDTO() {
        when(roomInventoryRepository.save(any(RoomInventory.class))).thenReturn(roomInventory);
        when(assetRepository.findById(anyLong())).thenReturn(Optional.of(asset));
        when(operationRoomRepository.findById(anyLong())).thenReturn(Optional.of(operationRoom));

        RoomInventoryDTO savedRoomInventoryDTO = roomInventoryService.createRoomInventory(roomInventoryDTO);

        assertNotNull(savedRoomInventoryDTO);

        assertEquals(savedRoomInventoryDTO.getCount(), roomInventory.getCount());
        assertEquals(savedRoomInventoryDTO.getAssetDTO().getId(), roomInventory.getAsset().getId());
        assertEquals(savedRoomInventoryDTO.getOperationRoomDTO().getId(), roomInventory.getOperationRoom().getId());
        verify(roomInventoryRepository,times(1)).save(any(RoomInventory.class));
    }

    @Test
    void createRoomInventory_ShouldThrowResourceNotFoundExceptionWhenAssetNotFound() {
        when(assetRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roomInventoryService.createRoomInventory(roomInventoryDTO));

        verify(assetRepository,times(1)).findById(anyLong());
    }

    @Test
    void createRoomInventory_ShouldThrowResourceNotFoundExceptionWhenOperationRoomNotFound() {
        when(operationRoomRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(assetRepository.findById(anyLong())).thenReturn(Optional.of(asset));

        assertThrows(ResourceNotFoundException.class, () -> roomInventoryService.createRoomInventory(roomInventoryDTO));

        verify(operationRoomRepository,times(1)).findById(anyLong());
        verify(assetRepository,times(1)).findById(anyLong());
    }

    @Test
    void updateRoomInventory_ShouldSaveUpdateAndReturnRoomInventoryDTO() {
        when(roomInventoryRepository.save(any(RoomInventory.class))).thenReturn(roomInventory);
        when(roomInventoryRepository.findById(any(RoomInventoryId.class))).thenReturn(Optional.of(roomInventory));  // Match any RoomInventoryId

        roomInventoryDTO.setCount(33);

        RoomInventoryDTO updateRoomInventoryDTO = roomInventoryService.updateRoomInventory(1L, 2L, roomInventoryDTO);


        assertNotNull(updateRoomInventoryDTO);
        assertEquals(updateRoomInventoryDTO.getCount(), roomInventoryDTO.getCount());

        verify(roomInventoryRepository, times(1)).save(any(RoomInventory.class));
        verify(roomInventoryRepository, times(1)).findById(any(RoomInventoryId.class));  // Use any RoomInventoryId
    }

    @Test
    void updateRoomInventory_ShouldThrowResourceNotFoundExceptionWhenRoomInventoryNotFound() {
        when(roomInventoryRepository.findById(any(RoomInventoryId.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roomInventoryService.updateRoomInventory(1L, 2L, roomInventoryDTO));

        verify(roomInventoryRepository,times(1)).findById(any(RoomInventoryId.class));
    }

    @Test
    void getRoomInventory_ShouldReturnRoomInventoryDTO() {
        when(roomInventoryRepository.findById(any(RoomInventoryId.class))).thenReturn(Optional.of(roomInventory));

        RoomInventoryDTO foundRoomInventoryDTO = roomInventoryService.getRoomInventoryById(1L,2L);

        assertNotNull(foundRoomInventoryDTO);
        assertEquals(foundRoomInventoryDTO.getCount(), roomInventory.getCount());

        verify(roomInventoryRepository,times(1)).findById(any(RoomInventoryId.class));
    }

    @Test
    void getRoomInventory_ShouldThrowResourceNotFoundExceptionWhenRoomInventoryNotFound() {
        when(roomInventoryRepository.findById(any(RoomInventoryId.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roomInventoryService.getRoomInventoryById(1L,2L));

        verify(roomInventoryRepository,times(1)).findById(any(RoomInventoryId.class));

    }

    @Test
    void getAllRoomInventory_ShouldReturnListOfRoomInventoryDTO() {
        when(roomInventoryRepository.findAll()).thenReturn(Arrays.asList(roomInventory,roomInventory2));

        List<RoomInventoryDTO> allRoomInventoryDTO = roomInventoryService.getAllRoomInventories();

        assertNotNull(allRoomInventoryDTO);
        assertEquals(allRoomInventoryDTO.size(), 2);
        assertEquals(allRoomInventoryDTO.get(0).getCount(), roomInventory.getCount());
        assertEquals(allRoomInventoryDTO.get(1).getCount(), roomInventory2.getCount());
        verify(roomInventoryRepository,times(1)).findAll();
    }

    @Test
    void deleteRoomInventory_ShouldDeleteRoomInventory() {
        when(roomInventoryRepository.findById(any(RoomInventoryId.class))).thenReturn(Optional.of(roomInventory));
        doNothing().when(roomInventoryRepository).delete(any(RoomInventory.class));

        roomInventoryService.deleteRoomInventory(1L, 2L);

        verify(roomInventoryRepository,times(1)).findById(any(RoomInventoryId.class));
        verify(roomInventoryRepository,times(1)).delete(any(RoomInventory.class));

    }

    @Test
    void deleteRoomInventory_ShouldThrowResourceNotFoundExceptionWhenRoomInventoryNotFound() {
        when(roomInventoryRepository.findById(any(RoomInventoryId.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roomInventoryService.deleteRoomInventory(1L, 2L));

        verify(roomInventoryRepository,times(1)).findById(any(RoomInventoryId.class));
    }
}
