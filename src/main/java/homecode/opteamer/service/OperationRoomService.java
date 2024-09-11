package homecode.opteamer.service;

import homecode.opteamer.model.OperationRoom;
import homecode.opteamer.model.dtos.OperationRoomDTO;
import homecode.opteamer.repository.OperationRoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OperationRoomService {


    private final OperationRoomRepository operationRoomRepository;

    public OperationRoomService(OperationRoomRepository operationRoomRepository) {
        this.operationRoomRepository = operationRoomRepository;
    }

    public Optional<OperationRoomDTO> getOperationRoomById(Long id) {
        try {
            OperationRoom operationRoom = operationRoomRepository.findById(id).orElse(null);
            return Optional.of(mapEntityToDTO(operationRoom));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }


    public List<OperationRoomDTO> getAllOperationRooms() {
        List<OperationRoomDTO> operationRooms = new ArrayList<>();
        Iterable<OperationRoom> allOperationRooms = operationRoomRepository.findAll();
        allOperationRooms.forEach(operationRoom ->
                operationRooms.add(mapEntityToDTO(operationRoom))
        );
        return operationRooms;
    }

    public OperationRoomDTO createOperationRoom(OperationRoomDTO operationRoomDTO) {
        OperationRoom operationRoom = mapDTOToEntity(operationRoomDTO);
        operationRoom = operationRoomRepository.save(operationRoom);
        return mapEntityToDTO(operationRoom);
    }

    public Optional<OperationRoomDTO> updateOperationRoom(Long id, OperationRoomDTO operationRoomDTO) {
        return operationRoomRepository.findById(id).map(operationRoom -> {
            operationRoom.setRoomNr(operationRoomDTO.getRoomNr());
            operationRoom.setBuildingBlock(operationRoomDTO.getBuildingBlock());
            operationRoom.setFloor(operationRoomDTO.getFloor());
            operationRoom.setType(operationRoomDTO.getType());
            operationRoom.setState(operationRoomDTO.getState());

            operationRoomRepository.save(operationRoom);
            return mapEntityToDTO(operationRoom);
        });
    }

    public boolean deleteOperationRoomById(Long id) {
        return operationRoomRepository.findById(id).map(operationRoom -> {
            operationRoomRepository.delete(operationRoom);
            return true;
        }).orElse(false);
    }


    private OperationRoom mapDTOToEntity(OperationRoomDTO operationRoomDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(operationRoomDTO, OperationRoom.class);
    }

    private OperationRoomDTO mapEntityToDTO(OperationRoom operationRoom) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(operationRoom, OperationRoomDTO.class);
    }
}
