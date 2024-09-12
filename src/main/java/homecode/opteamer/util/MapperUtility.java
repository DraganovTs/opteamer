package homecode.opteamer.util;

import org.modelmapper.ModelMapper;


public class MapperUtility {
    private final static ModelMapper modelMapper = new ModelMapper();


    public static  <D, E> E mapDTOToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    public  static <E, D> D mapEntityToDTO(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
}
