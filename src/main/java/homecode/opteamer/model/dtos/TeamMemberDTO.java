package homecode.opteamer.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(
        name = "TeamMember",
        description = "Schema to hold team member information"
)
public class TeamMemberDTO {


    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
    @Schema(
            description = "Name of the team member",
            example = "Jane Doe"
    )
    private String name;

    @NotNull(message = "Operation Provider cannot be null")
    @Schema(
            description = "Details of the operation provider associated with the team member",
            implementation = OperationProviderDTO.class
    )
    private OperationProviderDTO operationProviderDTO;
}
