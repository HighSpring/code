package co.es.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "post", type = "RuleCondition", shards = 1, replicas = 0)
public class RuleCondition{
	
	@Id
	private Integer id;
	private Integer companyId;
	private Integer businessType;
	private Integer ruleType;
	private Integer ruleId;
	private String ruleName;
	private String ruleCond;
	private String ruleCons;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	public Integer getRuleType() {
		return ruleType;
	}
	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleCond() {
		return ruleCond;
	}
	public void setRuleCond(String ruleCond) {
		this.ruleCond = ruleCond;
	}
	public String getRuleCons() {
		return ruleCons;
	}
	public void setRuleCons(String ruleCons) {
		this.ruleCons = ruleCons;
	}
	
}
