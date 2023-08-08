package obss.hris.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "candidates")
@Setting(settingPath = "/searchSettings.json")
public class ElkCandidate {
    @Id
    private Long candidateId;

    @Field(type = FieldType.Text)
    private String firstName;

    @Field(type = FieldType.Text)
    private String lastName;

    @Field(type = FieldType.Text)
    private String email;

    @Field(type = FieldType.Text)
    private String about;

    @Field(type = FieldType.Text)
    private List<String> skills;

    @Field(type = FieldType.Text)
    private String profilePicture;
}
