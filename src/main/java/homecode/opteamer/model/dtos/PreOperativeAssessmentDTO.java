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
public class PreOperativeAssessmentDTO {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2 , max = 50 , message = "Name must be between 1 and 2 characters")
    private String name;
}
