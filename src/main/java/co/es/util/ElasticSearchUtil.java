package co.es.util;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class ElasticSearchUtil {

    @Autowired  
    private Client client;  
    
	public ElasticSearchUtil(){}
	
	public void test(String[] args) throws JsonProcessingException, InterruptedException, ExecutionException{
		// instance a json mapper
		ObjectMapper mapper = new ObjectMapper(); // create once, reuse
		// generate json
		byte[] json = mapper.writeValueAsBytes("");
		
		String json1 = "{" +
		        "\"user\":\"kimchy\"," +
		        "\"postDate\":\"2013-01-30\"," +
		        "\"message\":\"trying out Elasticsearch\"" +
		    "}";

		IndexResponse response = client.prepareIndex("twitter", "tweet")
		        .setSource(json)
		        .get();
		
		// Index name
		String _index = response.getIndex();
		// Type name
		String _type = response.getType();
		// Document ID (generated or not)
		String _id = response.getId();
		// Version (if it's the first time you index this document, you will get: 1)
		long _version = response.getVersion();
		// isCreated() is true if the document is a new one, false if it has been updated
		boolean created = response.isCreated();
		
		GetResponse getResponse = client.prepareGet("twitter", "tweet", "1").get();
		
		GetResponse response1 = client.prepareGet("twitter", "tweet", "1")
		        .setOperationThreaded(false)
		        .get();
		DeleteResponse response2 = client.prepareDelete("twitter", "tweet", "1").get();
		DeleteResponse response3 = client.prepareDelete("twitter", "tweet", "1")
		        .setOperationThreaded(false)
		        .get();
		
		//update
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index("index");
		updateRequest.type("type");
		updateRequest.id("1");
		updateRequest.doc("");
		client.update(updateRequest).get();
		
		client.prepareUpdate("ttl", "doc", "1")
        .setDoc(""/*jsonBuilder()               
            .startObject()
                .field("gender", "male")
            .endObject()*/)
        .get();
		
		UpdateRequest updateRequest1 = new UpdateRequest("index", "type", "1")
        .doc(""/*jsonBuilder()
            .startObject()
                .field("gender", "male")
            .endObject()*/);
		client.update(updateRequest).get();
		
		IndexRequest indexRequest6 = new IndexRequest("index", "type", "1")
        .source(""/*jsonBuilder()
            .startObject()
                .field("name", "Joe Smith")
                .field("gender", "male")
            .endObject()*/);
		UpdateRequest updateRequest6 = new UpdateRequest("index", "type", "1")
		        .doc(""/*jsonBuilder()
		            .startObject()
		                .field("gender", "male")
		            .endObject()*/)
		        .upsert(indexRequest6);              
		client.update(updateRequest).get();
		
		
		MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
			    .add("twitter", "tweet", "1")           
			    .add("twitter", "tweet", "2", "3", "4") 
			    .add("another", "type", "foo")          
			    .get();

			for (MultiGetItemResponse itemResponse : multiGetItemResponses) { 
			    GetResponse response7 = itemResponse.getResponse();
			    if (response7.isExists()) {                      
			        String json7 = response7.getSourceAsString(); 
			    }
			}
			
			BulkRequestBuilder bulkRequest = client.prepareBulk();

			// either use client#prepare, or use Requests# to directly build index/delete requests
			bulkRequest.add(client.prepareIndex("twitter", "tweet", "1")
			        .setSource(/*jsonBuilder()
			                    .startObject()
			                        .field("user", "kimchy")
			                        .field("postDate", new Date())
			                        .field("message", "trying out Elasticsearch")
			                    .endObject()*/
			                  )
			        );

			bulkRequest.add(client.prepareIndex("twitter", "tweet", "2")
			        .setSource(/*jsonBuilder()
			                    .startObject()
			                        .field("user", "kimchy")
			                        .field("postDate", new Date())
			                        .field("message", "another post")
			                    .endObject()*/
			                  )
			        );

			BulkResponse bulkResponse = bulkRequest.get();
			if (bulkResponse.hasFailures()) {
			    // process failures by iterating through each bulk response item
			}
	}
}
