package obss.hris.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateJobPostRequest {
    @NotNull(message = "İş ilanı id'si boş olamaz")
    private Long jobPostId;
    @NotBlank(message = "Başlık girmek zorundasınız")
    @NotNull(message = "Başlık boş olamaz")
    @Size(min = 3, message = "Başlık en az 3 karakter olmalıdır")
    private String title;
    @NotNull(message = "Açıklama girmek zorundasınız")
    @NotBlank(message = "Açıklama boş olamaz")
    @Size(min = 10, message = "Açıklama en az 10 karakter olmalıdır")
    private String description;
    @NotNull(message = "Şirket adını girmek zorundasınız.")
    @NotBlank(message = "Şirket adı boş olamaz")
    private String company;
    @NotNull(message = "Konumu girmek zorundasınız")
    @NotBlank(message = "Konum boş olamaz")
    private String location;
    @Size(min = 1, message = "En az 1 beceri gerekli")
    private List<String> requiredSkills;
    @NotNull(message = "Aktiflik durumu boş olamaz")
    private boolean isActive;
}
