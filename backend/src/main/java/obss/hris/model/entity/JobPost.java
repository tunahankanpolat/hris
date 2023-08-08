package obss.hris.model.entity;

import jakarta.persistence.*;
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
    private String code;
    private String company;
    private String location;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "activation_time")
    private Date activationTime;

    @Column(name = "closure_time")
    private Date closureTime;

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
        setRequiredSkills(updateJobPostRequest.getRequiredSkills());
    }

    public boolean getActive() {
        Date now = new Date(System.currentTimeMillis());
        return activationTime != null && closureTime != null && now.after(activationTime) && now.before(closureTime);
    }
}
