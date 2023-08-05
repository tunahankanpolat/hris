package obss.hris.controller;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.ElasticSearchService;
import obss.hris.business.abstracts.JobApplicationService;
import obss.hris.model.entity.ElkCandidate;
import obss.hris.model.response.GetCandidateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search/v1/")
@AllArgsConstructor
public class SearchController {
    private ElasticSearchService elasticSearchService;
    private JobApplicationService jobApplicationService;

    @GetMapping("candidates/{page}/{size}")
    public ResponseEntity<List<ElkCandidate>> searchCandidate(
            @RequestParam String keyword, @PathVariable int page, @PathVariable int size) {
        return ResponseEntity.ok(elasticSearchService.searchOnAllCandidate(keyword, page, size));
    }
//    @GetMapping("jobPosts/{jobPostId}/jobApplications/candidates/{page}/{size}")
//    public ResponseEntity<List<ElkCandidate>> searchCandidateOnJobPost(
//            @PathVariable Long jobPostId, @RequestParam String keyword,
//            @PathVariable int page, @PathVariable int size) {
//        return ResponseEntity.ok(elasticSearchService
//                .searchOnCandidateForJobPost(
//                        jobApplicationService.getCandidateIdAppliedToJobPost(jobPostId),
//                        keyword, page, size));
//    }
}
