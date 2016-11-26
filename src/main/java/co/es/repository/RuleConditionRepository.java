package co.es.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import co.es.entities.RuleCondition;

public interface RuleConditionRepository extends ElasticsearchRepository<RuleCondition, Integer>{
	RuleCondition save(RuleCondition ruleCondition);
	RuleCondition findOne(Integer id);
    Iterable<RuleCondition> findAll();
    Page<RuleCondition> findByRuleName(String ruleName, Pageable pageable);
}
