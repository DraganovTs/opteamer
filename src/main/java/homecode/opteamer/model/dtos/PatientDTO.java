package homecode.opteamer.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientDTO {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2 , max = 20)
    private String name;

    @NotBlank(message = "NIN cannot be blank")
    @Size(min = 2 , max = 20)
    private String nin;
}
