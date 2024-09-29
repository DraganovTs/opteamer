package homecode.opteamer.service;

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
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public Optional<RoomInventoryDTO> getRoomInventoryById(Long assetId, Long roomId)
            throws NoSuchElementException {
        try {
            RoomInventoryId rId = new RoomInventoryId(assetId, roomId);
            RoomInventory roomInventory = roomInventoryRepository.findById(rId).orElse(null);
            return Optional.of(getRoomInventoryDTO(roomInventory,
                    roomInventory.getAsset(),
                    roomInventory.getOperationRoom()));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<RoomInventoryDTO> getAllRoomInventory() {
        List<RoomInventoryDTO> roomInventoryDTOList = new ArrayList<>();
        Iterable<RoomInventory> roomInventoryIterable = roomInventoryRepository.findAll();
        roomInventoryIterable.forEach(roomInventory ->
            roomInventoryDTOList.add(getRoomInventoryDTO(roomInventory,
                    roomInventory.getAsset(),
                    roomInventory.getOperationRoom()))
        );
        return roomInventoryDTOList;
    }

    public RoomInventoryDTO createRoomInventory(RoomInventoryDTO roomInventoryDTO) {
        RoomInventory roomInventory = new RoomInventory();
        System.out.println(roomInventoryDTO.getAssetDTO().getId());
        System.out.println(roomInventoryDTO.getOperationRoomId());
        Asset asset = assetRepository.findById(roomInventoryDTO.getAssetDTO().getId()).get();
        OperationRoom operationRoom = operationRoomRepository.findById(roomInventoryDTO.getOperationRoomId()).get();
        if (asset == null || operationRoom == null)
            throw new NoSuchElementException();
        RoomInventoryId roomInventoryId = new RoomInventoryId();
        roomInventoryId.setAssetId(asset.getId());
        roomInventoryId.setRoomId(operationRoom.getId());

        roomInventory.setRoomInventoryId(roomInventoryId);
        roomInventory.setAsset(asset);
        roomInventory.setOperationRoom(operationRoom);
        roomInventory.setCount(roomInventoryDTO.getCount());
        roomInventory = roomInventoryRepository.save(roomInventory);
        return getRoomInventoryDTO(roomInventory,
                roomInventory.getAsset(),
                roomInventory.getOperationRoom());
    }

    public Optional<RoomInventoryDTO> updateRoomInventory(Long assetId, Long roomId,
                                                          RoomInventoryDTO roomInventoryDTO) {

        RoomInventoryId roomInventoryId = new RoomInventoryId(assetId, roomId);
        return roomInventoryRepository.findById(roomInventoryId).map(roomInventory -> {
            roomInventory.setCount(roomInventoryDTO.getCount());
            roomInventoryRepository.save(roomInventory);
            return getRoomInventoryDTO(roomInventory,
                    roomInventory.getAsset(),
                    roomInventory.getOperationRoom());
        });
    }

    public boolean deleteRoomInventory(Long assetId, Long roomId) {
        RoomInventoryId roomInventoryId = new RoomInventoryId(assetId, roomId);
        return roomInventoryRepository.findById(roomInventoryId).map(roomInventory -> {
            roomInventoryRepository.delete(roomInventory);
            return true;
        }).orElse(false);
    }


    private static RoomInventoryDTO getRoomInventoryDTO(RoomInventory roomInventory, Asset asset, OperationRoom operationRoom) {
        return new RoomInventoryDTO(roomInventory.getAsset().getId(),
                roomInventory.getOperationRoom().getId(),
                new AssetDTO(asset.getId(), asset.getType(), asset.getName()),
                new OperationRoomDTO(operationRoom.getId(),
                        operationRoom.getRoomNr(),
                        operationRoom.getBuildingBlock(),
                        operationRoom.getFloor(),
                        operationRoom.getType(),
                        operationRoom.getState()),
                roomInventory.getCount());
    }

}
