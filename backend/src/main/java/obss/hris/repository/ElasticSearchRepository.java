package obss.hris.repository;

import obss.hris.model.entity.ElkCandidate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticSearchRepository extends ElasticsearchRepository<ElkCandidate, Long> {
    //texti bütün alanlarda arar

}
