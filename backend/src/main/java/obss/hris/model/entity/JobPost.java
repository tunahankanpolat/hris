package obss.hris.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import obss.hris.model.request.UpdateJobPostRequest;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "job_posts")
@NoArgsConstructor
@AllArgsConstructor
public class JobPost {
    @Id
    @Column(name = "job_post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobPostId;

    @NotNull
    @NotBlank
    private String code;

    @NotNull
    @NotBlank
    private String company;

    @NotNull
    @NotBlank
    private String location;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Column(name = "activation_time")
    private Date activationTime;

    @NotNull
    @Column(name = "closure_time")
    private Date closureTime;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "human_resource_id")
    private HumanResource humanResource;

    @OneToMany(mappedBy = "jobPost", fetch = FetchType.LAZY)
    private List<JobApplication> jobApplications;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> requiredSkills;

    public void updateJobPost(UpdateJobPostRequest updateJobPostRequest){
        setCompany(updateJobPostRequest.getCompany());
        setLocation(updateJobPostRequest.getLocation());
        setTitle(updateJobPostRequest.getTitle());
        setDescription(updateJobPostRequest.getDescription());
        setActivationTime(updateJobPostRequest.getActivationTime());
        setClosureTime(updateJobPostRequest.getClosureTime());
        setRequiredSkills(updateJobPostRequest.getRequiredSkills());
        setActive(updateJobPostRequest.isActive());
    }
}
