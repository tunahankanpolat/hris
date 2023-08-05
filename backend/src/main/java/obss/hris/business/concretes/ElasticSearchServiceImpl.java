package obss.hris.business.concretes;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQueryField;
import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.ElasticSearchService;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.model.entity.ElkCandidate;
import obss.hris.model.response.GetCandidateResponse;
import obss.hris.model.response.GetJobPostResponse;
import obss.hris.repository.ElasticSearchRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQuery;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ElasticSearchServiceImpl implements ElasticSearchService {
    private ElasticsearchOperations elasticsearchOperations;
    private ModelMapperService modelMapperService;

    @Override
    public void saveCandidate(ElkCandidate candidate) {
        elasticsearchOperations.save(candidate);
    }

    @Override
    public List<ElkCandidate> searchOnAllCandidate(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Query query = NativeQuery.builder()
                .withQuery(q -> q
                        .multiMatch(m -> m
                                .query(keyword)
                                .fields("firstName","lastName", "email", "about", "skills")
                                .fuzziness("1")
                                .prefixLength(3)
                                .slop(1)
                        ))
                .withPageable(pageable)
                .build();

        SearchHits<ElkCandidate> searchHits = elasticsearchOperations.search(query, ElkCandidate.class);
        return  searchHits.map(SearchHit::getContent).stream().toList();

        //return elkCandidates.stream().map(candidate ->
        //        modelMapperService.forResponse().map(candidate, GetCandidateResponse.class)).toList();
    }

    @Override
    public List<ElkCandidate> searchOnCandidateForJobPost(List<Long> candidateIds, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);


        Query query = NativeQuery.builder()
                .withQuery(q -> q
                        .multiMatch(m -> m
                                .query(keyword)
                                .fields("firstName","lastName", "email", "about", "skills")
                                .fuzziness("1")
                                .prefixLength(3)
                                .slop(1)
                        ))
                .withFilter(q -> q
                        .bool(b -> b
                                .must(m -> m
                                        .terms(t -> t
                                                .field("candidateId")
                                                .terms(t2 -> t2
                                                        .value(candidateIds.stream()
                                                                .map(FieldValue::of).toList())))
                                )
                        )
                )
                .withPageable(pageable)
                .build();

        SearchHits<ElkCandidate> searchHits = elasticsearchOperations.search(query, ElkCandidate.class);
        return searchHits.map(SearchHit::getContent).stream().toList();
        //return elkCandidates.stream().map(candidate ->
        //        modelMapperService.forResponse().map(candidate, GetCandidateResponse.class)).toList();
    }

}
