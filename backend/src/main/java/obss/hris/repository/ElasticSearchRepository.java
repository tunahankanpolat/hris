package obss.hris.repository;

import obss.hris.model.entity.ElkCandidate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticSearchRepository extends ElasticsearchRepository<ElkCandidate, Long> {

}
