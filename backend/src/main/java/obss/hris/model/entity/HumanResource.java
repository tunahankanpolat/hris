package obss.hris.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "human_resources")
@NoArgsConstructor
@AllArgsConstructor
public class HumanResource {
    @Id
    @Column(name = "human_resource_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "firstname")
    private String firstName;

    @NotNull
    @NotBlank
    @Column(name = "lastname")
    private String lastName;

    @OneToMany(mappedBy = "humanResource")
    private List<JobPost> jobPosts;
}
