package obss.hris.business.abstracts;

import obss.hris.model.entity.ElkCandidate;

import java.util.List;

public interface ElasticSearchService {
    void saveCandidate(ElkCandidate candidate);
    List<ElkCandidate> searchOnAllCandidate(String keyword, int page, int size);
    List<ElkCandidate> searchOnCandidateForJobPost(List<Long> candidateIds, String keyword, int page, int size);
}
