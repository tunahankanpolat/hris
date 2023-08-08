package obss.hris.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HumanResourceLoginRequest {
    @NotBlank
    @NotNull
    private String userName;
    @NotBlank
    @NotNull
    private String password;
}
