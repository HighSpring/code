package co.es.service.impl;
  
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthStatus;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import co.es.entities.TaskInfo;
import co.es.util.APP;
  
/**  
 * @author seaphy  
 * @date 2014-5-4  
 */  
@Service
public class ElasticsearchCommander {  
  
    private static final Logger logger = Logger.getLogger(ElasticsearchCommander.class);  
  
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;  
  
    @Autowired  
    private Client client;  
  
    public void init() {  
        if (!elasticsearchTemplate.indexExists(APP.ESProp.INDEX_NAME)) {  
            elasticsearchTemplate.createIndex(APP.ESProp.INDEX_NAME);  
        }  
        elasticsearchTemplate.putMapping(TaskInfo.class);
    }  
  
    public boolean update(List<TaskInfo> taskInfoList) {  
        List<IndexQuery> queries = new ArrayList<IndexQuery>();  
        for (TaskInfo taskInfo : taskInfoList) {  
            IndexQuery indexQuery = new IndexQueryBuilder().withId(taskInfo.getTaskId()).withObject(taskInfo).build();  
            queries.add(indexQuery);  
        }  
        elasticsearchTemplate.bulkIndex(queries);  
        return true;  
    }  
  
    public boolean insertOrUpdateTaskInfo(List<TaskInfo> taskInfoList) {  
        List<IndexQuery> queries = new ArrayList<IndexQuery>();  
        for (TaskInfo taskInfo : taskInfoList) {  
            IndexQuery indexQuery = new IndexQueryBuilder().withId(taskInfo.getTaskId()).withObject(taskInfo).build();  
            queries.add(indexQuery);  
        }  
        elasticsearchTemplate.bulkIndex(queries);  
        return true;  
    }
  
    public boolean insertOrUpdateTaskInfo(TaskInfo taskInfo) {  
        try {  
            IndexQuery indexQuery = new IndexQueryBuilder().withId(taskInfo.getTaskId()).withObject(taskInfo).build();  
            elasticsearchTemplate.index(indexQuery);  
            return true;  
        } catch (Exception e) {  
            logger.error("insert or update task info error.", e);  
            return false;  
        }  
    }  
  
    public <T> boolean deleteById(String id, Class<T> clzz) {  
        try {  
            elasticsearchTemplate.delete(clzz, id);  
            return true;  
        } catch (Exception e) {  
            logger.error("delete " + clzz + " by id " + id + " error.", e);  
            return false;  
        }  
    }  
  
    /**  
     * 检查健康状态  
    * @author 高国藩  
    * @date 2015年6月15日 下午6:59:47  
    * @return  
     */  
    public boolean ping() {  
        try {  
            ActionFuture<ClusterHealthResponse> health = client.admin().cluster().health(new ClusterHealthRequest());  
            ClusterHealthStatus status = health.actionGet().getStatus();  
            if (status.value() == ClusterHealthStatus.RED.value()) {  
                throw new RuntimeException("elasticsearch cluster health status is red.");  
            }  
            return true;  
        } catch (Exception e) {  
            logger.error("ping elasticsearch error.", e);  
            return false;  
        }  
    }  
}