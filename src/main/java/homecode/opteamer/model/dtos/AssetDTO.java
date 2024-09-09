package homecode.opteamer.model.dtos;

import homecode.opteamer.model.enums.AssetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssetDTO {
    private Long id;
    private AssetType type;
    private String name;

}
