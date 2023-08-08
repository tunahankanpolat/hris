package obss.hris.repository;

import obss.hris.model.entity.JobPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost,Long> {
    List<JobPost> findAllByHumanResource_UserName(String userName, Pageable pageable);
    @Query("SELECT j FROM JobPost j WHERE j.activationTime <= CURRENT_DATE AND j.closureTime > CURRENT_DATE")
    List<JobPost> findAllActiveJobPosts(Pageable pageable);
}
