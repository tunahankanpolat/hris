package obss.hris.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BanRequest {
    @NotNull
    private Long id;
    @NotNull
    @NotBlank
    private String reason;
}
