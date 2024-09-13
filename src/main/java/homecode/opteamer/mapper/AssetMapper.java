package homecode.opteamer.mapper;

import homecode.opteamer.model.Asset;
import homecode.opteamer.model.dtos.AssetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssetMapper {

    AssetMapper INSTANCE = Mappers.getMapper(AssetMapper.class);

    AssetDTO assetToAssetDTO(Asset asset);

    Asset assetDTOToAsset(AssetDTO assetDTO);
}
