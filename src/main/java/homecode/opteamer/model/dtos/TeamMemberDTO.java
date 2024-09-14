package homecode.opteamer.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamMemberDTO {


    private Long id;

    private String name;

    private OperationProviderDTO operationProviderDTO;
}
