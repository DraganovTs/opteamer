package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.OperationRoom;
import homecode.opteamer.model.dtos.OperationRoomDTO;
import homecode.opteamer.repository.OperationRoomRepository;
import homecode.opteamer.util.MapperUtility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationRoomService {

    private final OperationRoomRepository operationRoomRepository;

    public OperationRoomService(OperationRoomRepository operationRoomRepository) {
        this.operationRoomRepository = operationRoomRepository;
    }


    public OperationRoomDTO save(OperationRoomDTO operationRoomDTO) {
        OperationRoom operationRoom = MapperUtility.mapDTOToEntity(operationRoomDTO, OperationRoom.class);
        operationRoom = operationRoomRepository.save(operationRoom);
        return MapperUtility.mapEntityToDTO(operationRoom, OperationRoomDTO.class);
    }


    public OperationRoomDTO updateOperationRoom(Long id, OperationRoomDTO operationRoomDTO) {
        OperationRoom operationRoom = operationRoomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Operation room not found with id: " + id));

        operationRoom.setRoomNr(operationRoomDTO.getRoomNr());
        operationRoom.setBuildingBlock(operationRoomDTO.getBuildingBlock());
        operationRoom.setFloor(operationRoomDTO.getFloor());
        operationRoom.setType(operationRoomDTO.getType());
        operationRoom.setState(operationRoomDTO.getState());

        operationRoomRepository.save(operationRoom);
        return MapperUtility.mapEntityToDTO(operationRoom, OperationRoomDTO.class);
    }


    public List<OperationRoomDTO> getAllOperationRooms() {
        List<OperationRoomDTO> operationRoomDTOList = new ArrayList<>();
        Iterable<OperationRoom> operationRooms = operationRoomRepository.findAll();
        operationRooms.forEach(operationRoom ->
                operationRoomDTOList.add(MapperUtility.mapEntityToDTO(operationRoom, OperationRoomDTO.class))
        );
        return operationRoomDTOList;
    }


    public OperationRoomDTO getOperationRoomById(Long id) {
        OperationRoom operationRoom = operationRoomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Operation room not found with id: " + id));
        return MapperUtility.mapEntityToDTO(operationRoom, OperationRoomDTO.class);
    }


    public void deleteOperationRoomById(Long id) {
        OperationRoom operationRoom = operationRoomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Operation room not found with id: " + id));
        operationRoomRepository.delete(operationRoom);
    }
}
