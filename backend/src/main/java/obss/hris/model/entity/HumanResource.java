package obss.hris.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "human_resources")
@NoArgsConstructor
@AllArgsConstructor
public class HumanResource {
    @Id
    @Column(name = "human_resource_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long humanResourceId;

    @NotNull
    @NotBlank
    @Column(name = "username")
    private String userName;

    @NotNull
    @NotBlank
    @Column(name = "firstname")
    private String firstName;

    @NotNull
    @NotBlank
    @Column(name = "lastname")
    private String lastName;

    @OneToMany(mappedBy = "humanResource", fetch = FetchType.LAZY)
    private List<JobPost> jobPosts;
}
