package co.es.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import co.es.entities.RuleCondition;
import co.es.repository.RuleConditionRepository;
import co.es.service.RuleConditionService;

@Service
public class RuleConditionServiceImpl implements RuleConditionService{
	@Autowired
	RuleConditionRepository ruleConditionRepository;
	public RuleCondition save(RuleCondition ruleCondition){
		ruleConditionRepository.save(ruleCondition);
		return ruleCondition;
	}
	public RuleCondition findOne(Integer id){
		return ruleConditionRepository.findOne(id);
	}
    public Iterable<RuleCondition> findAll(){
    	return ruleConditionRepository.findAll();
    }
    public Page<RuleCondition> findByRuleName(String ruleName, PageRequest pageRequest){
    	return ruleConditionRepository.findByRuleName(ruleName, pageRequest);
    }
}
