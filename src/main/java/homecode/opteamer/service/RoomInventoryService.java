package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.Asset;
import homecode.opteamer.model.OperationRoom;
import homecode.opteamer.model.RoomInventory;
import homecode.opteamer.model.dtos.AssetDTO;
import homecode.opteamer.model.dtos.OperationRoomDTO;
import homecode.opteamer.model.dtos.RoomInventoryDTO;
import homecode.opteamer.model.embededIds.RoomInventoryId;
import homecode.opteamer.repository.AssetRepository;
import homecode.opteamer.repository.OperationRoomRepository;
import homecode.opteamer.repository.RoomInventoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomInventoryService {

    private final RoomInventoryRepository roomInventoryRepository;
    private final AssetRepository assetRepository;
    private final OperationRoomRepository operationRoomRepository;

    public RoomInventoryService(RoomInventoryRepository roomInventoryRepository, AssetRepository assetRepository, OperationRoomRepository operationRoomRepository) {
        this.roomInventoryRepository = roomInventoryRepository;
        this.assetRepository = assetRepository;
        this.operationRoomRepository = operationRoomRepository;
    }

    public RoomInventoryDTO getRoomInventoryById(Long assetId, Long roomId) {
        RoomInventoryId roomInventoryId = new RoomInventoryId(assetId, roomId);
        RoomInventory roomInventory = roomInventoryRepository.findById(roomInventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Room Inventory not found with assetId: " + assetId + " and roomId: " + roomId));

        return convertToDTO(roomInventory);
    }

    public List<RoomInventoryDTO> getAllRoomInventories() {
        List<RoomInventoryDTO> roomInventoryDTOList = new ArrayList<>();
        roomInventoryRepository.findAll().forEach(roomInventory ->
                roomInventoryDTOList.add(convertToDTO(roomInventory))
        );
        return roomInventoryDTOList;
    }

    public RoomInventoryDTO createRoomInventory(RoomInventoryDTO roomInventoryDTO) {
        Asset asset = assetRepository.findById(roomInventoryDTO.getAssetDTO().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID: " + roomInventoryDTO.getAssetDTO().getId()));
        OperationRoom operationRoom = operationRoomRepository.findById(roomInventoryDTO.getOperationRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Operation Room not found with ID: " + roomInventoryDTO.getOperationRoomId()));

        RoomInventoryId roomInventoryId = new RoomInventoryId(asset.getId(), operationRoom.getId());
        RoomInventory roomInventory = new RoomInventory();
        roomInventory.setRoomInventoryId(roomInventoryId);
        roomInventory.setAsset(asset);
        roomInventory.setOperationRoom(operationRoom);
        roomInventory.setCount(roomInventoryDTO.getCount());

        roomInventory = roomInventoryRepository.save(roomInventory);
        return convertToDTO(roomInventory);
    }

    public RoomInventoryDTO updateRoomInventory(Long assetId, Long roomId, RoomInventoryDTO roomInventoryDTO) {
        RoomInventoryId roomInventoryId = new RoomInventoryId(assetId, roomId);
        RoomInventory roomInventory = roomInventoryRepository.findById(roomInventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Room Inventory not found with assetId: " + assetId + " and roomId: " + roomId));

        roomInventory.setCount(roomInventoryDTO.getCount());
        roomInventoryRepository.save(roomInventory);
        return convertToDTO(roomInventory);
    }

    public void deleteRoomInventory(Long assetId, Long roomId) {
        RoomInventoryId roomInventoryId = new RoomInventoryId(assetId, roomId);
        RoomInventory roomInventory = roomInventoryRepository.findById(roomInventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Room Inventory not found with assetId: " + assetId + " and roomId: " + roomId));

        roomInventoryRepository.delete(roomInventory);
    }

    private RoomInventoryDTO convertToDTO(RoomInventory roomInventory) {
        return new RoomInventoryDTO(
                roomInventory.getAsset().getId(),
                roomInventory.getOperationRoom().getId(),
                new AssetDTO(roomInventory.getAsset().getId(), roomInventory.getAsset().getType(), roomInventory.getAsset().getName()),
                new OperationRoomDTO(roomInventory.getOperationRoom().getId(),
                        roomInventory.getOperationRoom().getRoomNr(),
                        roomInventory.getOperationRoom().getBuildingBlock(),
                        roomInventory.getOperationRoom().getFloor(),
                        roomInventory.getOperationRoom().getType(),
                        roomInventory.getOperationRoom().getState()),
                roomInventory.getCount()
        );
    }
}
