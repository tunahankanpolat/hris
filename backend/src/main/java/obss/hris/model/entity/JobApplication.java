package obss.hris.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "job_applications", uniqueConstraints = @UniqueConstraint(columnNames = {"job_post_id", "candidate_id"}))
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {
    @Id
    @Column(name = "job_application_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobApplicationId;

    @Enumerated(EnumType.STRING)
    private JobApplicationStatus status = JobApplicationStatus.PROCESSING;

    @Column(name = "application_time", nullable = false, updatable = false)
    @CreationTimestamp
    private Date applicationTime;

    @ManyToOne
    @JoinColumn(name = "job_post_id")
    private JobPost jobPost;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @Column(name = "eligibility")
    private Double eligibility;
}
