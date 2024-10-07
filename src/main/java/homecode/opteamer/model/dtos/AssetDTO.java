package homecode.opteamer.model.dtos;

import homecode.opteamer.validation.ValidEnum;
import homecode.opteamer.model.enums.AssetType;
import jakarta.validation.constraints.Size;
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
    @ValidEnum(enumClass = AssetType.class, message = "Invalid asset type")
    private AssetType type;
    @Size(min = 1, max = 50)
    private String name;

}
