package co.es.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import co.es.entities.RuleCondition;

public interface RuleConditionService {
	RuleCondition save(RuleCondition ruleCondition);
	RuleCondition findOne(Integer id);
    Iterable<RuleCondition> findAll();
    Page<RuleCondition> findByRuleName(String ruleName, PageRequest pageRequest);
}
