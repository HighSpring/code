package co.es.service.impl;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.es.application.Application;
import co.es.entities.Post;
import co.es.entities.RuleCondition;
import co.es.entities.Tag;
import co.es.service.PostService;
import co.es.service.RuleConditionService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PostServiceImplTest{
    @Autowired
    private PostService postService;
    @Autowired
    private RuleConditionService ruleConditionService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Before
    public void before() {
        //elasticsearchTemplate.deleteIndex(Post.class);
        //elasticsearchTemplate.createIndex(Post.class);
       // elasticsearchTemplate.putMapping(Post.class);
       // elasticsearchTemplate.refresh(Post.class, true);
    }

    //@Test
    public void testSave() throws Exception {
        Tag tag = new Tag();
        tag.setId("1");
        tag.setName("tech");
        Tag tag2 = new Tag();
        tag2.setId("2");
        tag2.setName("elasticsearch");

        Post post = new Post();
        post.setId("1");
        post.setTitle("Bigining with spring boot application and elasticsearch");
        post.setTags(Arrays.asList(tag, tag2));
        postService.save(post);

        assertThat(post.getId(), notNullValue());

        Post post2 = new Post();
        post2.setId("1");
        post2.setTitle("Bigining with spring boot application");
        post2.setTags(Arrays.asList(tag));
        postService.save(post);
        assertThat(post2.getId(), notNullValue());




    }

    public void testFindOne() throws Exception {

    }

    public void testFindAll() throws Exception {

    }
    //@Test
    public void testFindByTagsName() throws Exception {
        Tag tag = new Tag();
        tag.setId("1");
        tag.setName("tech");
        Tag tag2 = new Tag();
        tag2.setId("2");
        tag2.setName("elasticsearch");

        Post post = new Post();
        post.setId("1");
        post.setTitle("Bigining with spring boot application and elasticsearch");
        post.setTags(Arrays.asList(tag, tag2));
        postService.save(post);



        Post post2 = new Post();
        post2.setId("1");
        post2.setTitle("Bigining with spring boot application");
        post2.setTags(Arrays.asList(tag));
        postService.save(post);

        Page<Post> posts  = postService.findByTagsName("tech", new PageRequest(0,10));
        Page<Post> posts2  = postService.findByTagsName("tech", new PageRequest(0,10));
        Page<Post> posts3  = postService.findByTagsName("maz", new PageRequest(0,10));


       assertThat(posts.getTotalElements(), is(1L));
        assertThat(posts2.getTotalElements(), is(1L));
        assertThat(posts3.getTotalElements(), is(0L));
    }
    
    @Test
    public void testRuleConditionSave(){
    	RuleCondition ruleCondition = new RuleCondition();
    	ruleCondition.setBusinessType(1);
    	ruleCondition.setId(1);
    	ruleCondition.setCompanyId(5);
    	ruleCondition.setRuleType(5);
    	ruleCondition.setRuleName("custTransferHighSea555");
    	ruleCondition.setRuleCond("[{\"conditionValue\":\"2\",\"conditionName\":\"custBusinessLogTimeExp\"}]");
    	ruleCondition.setRuleCons("");
    	ruleCondition.setRuleId(1);
    	
    	ruleConditionService.save(ruleCondition);
    }
    @Test
    public void testRuleConditionFindOne(){
    	RuleCondition ruleCondition = new RuleCondition();
    	ruleCondition = ruleConditionService.findOne(1);
    	System.out.println(ruleCondition.getId());
    	System.out.println(ruleCondition.getCompanyId());
    	System.out.println(ruleCondition.getBusinessType());
    	System.out.println(ruleCondition.getRuleType());
    	System.out.println(ruleCondition.getRuleName());
    	System.out.println(ruleCondition.getRuleCond());
    	System.out.println(ruleCondition.getRuleCons());
    	System.out.println(ruleCondition.getRuleId());
    }
    //@Test
    public void testRuleConditionFindAll(){
    	Iterable<RuleCondition> iter = ruleConditionService.findAll();
    	if(iter!=null){
    		for(RuleCondition ruleCondition : iter){
		    	System.out.println(ruleCondition.getId());
		    	System.out.println(ruleCondition.getCompanyId());
		    	System.out.println(ruleCondition.getBusinessType());
		    	System.out.println(ruleCondition.getRuleType());
		    	System.out.println(ruleCondition.getRuleName());
		    	System.out.println(ruleCondition.getRuleCond());
		    	System.out.println(ruleCondition.getRuleCons());
		    	System.out.println(ruleCondition.getRuleId());
    		}
    	}
    }
    //@Test
    public void testRuleConditionFindByRuleName(){
    	Page<RuleCondition> pageRuleName = ruleConditionService.findByRuleName("custTransferHighSea" , new PageRequest(0,10));
    	if(pageRuleName!=null){
    		for(RuleCondition ruleCondition : pageRuleName){
		    	System.out.println(ruleCondition.getId());
		    	System.out.println(ruleCondition.getCompanyId());
		    	System.out.println(ruleCondition.getBusinessType());
		    	System.out.println(ruleCondition.getRuleType());
		    	System.out.println(ruleCondition.getRuleName());
		    	System.out.println(ruleCondition.getRuleCond());
		    	System.out.println(ruleCondition.getRuleCons());
		    	System.out.println(ruleCondition.getRuleId());
    		}
    	}
    }
}