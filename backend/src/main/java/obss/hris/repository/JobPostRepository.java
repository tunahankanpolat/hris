package obss.hris.repository;

import obss.hris.model.entity.JobPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost,Long> {
    List<JobPost> findAllByHumanResource_UserName(String userName, Pageable pageable);
    List<JobPost> findAllByIsActiveTrue(Pageable pageable);


}
