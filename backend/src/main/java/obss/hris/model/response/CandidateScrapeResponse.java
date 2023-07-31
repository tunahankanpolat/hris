package obss.hris.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateScrapeResponse {
    private List<String> skills;
    private String about;
}
