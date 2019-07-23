package com.pravah.web.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

@Service
public class ElasticSearchClient {
    private RestHighLevelClient restHighLevelClient;

    private ObjectMapper objectMapper=new  ObjectMapper();
    public ElasticSearchClient(){

        String connString = "https://Aoh8DQs6Fk:LMfDsJVgQcBFSC7Am2E3ho5@aralia-828997626.us-east-1.bonsaisearch.net:443";
        URI connUri = URI.create(connString);
        String[] auth = connUri.getUserInfo().split(":");

        CredentialsProvider cp = new BasicCredentialsProvider();
        cp.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(auth[0], auth[1]));

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost(connUri.getHost(), connUri.getPort(), connUri.getScheme()))
                        .setHttpClientConfigCallback(
                                httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(cp)
                                        .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())));


        this.restHighLevelClient=restHighLevelClient;

    }

    public JsonNode search(Map<String,String[]> searchParams) throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        for(Map.Entry<String,String[]> stringEntry: searchParams.entrySet()){

            String key= stringEntry.getKey();
            String vals[]=stringEntry.getValue();
            if(vals.length==1){
                boolQueryBuilder=boolQueryBuilder.should(QueryBuilders.matchQuery(key,vals[0]));
            }
            else {
                throw new Exception("only one key and value supported as of now");
            }
        }

        if(searchParams.containsKey(Constants.LATITUDE) && searchParams.containsKey(Constants.LONGITUDE) && searchParams.containsKey(Constants.DISTANCE_IN_KM)) {
            double latitude= Double.parseDouble(searchParams.get(Constants.LATITUDE)[0]);
            double longitude= Double.parseDouble(searchParams.get(Constants.LONGITUDE)[0]);
            String distanceInKm=searchParams.get(Constants.DISTANCE_IN_KM)[0];
            QueryBuilder geoDistanceQueryBuilder = QueryBuilders
                    .geoDistanceQuery(Constants.GEO_CORDINATES)
                    .point(latitude, longitude)
                    .distance(distanceInKm, DistanceUnit.KILOMETERS);
            boolQueryBuilder = boolQueryBuilder.should(geoDistanceQueryBuilder);

        }
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        ArrayNode arrayNode=  objectMapper.createArrayNode();
        SearchResponse searchResponse  = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits=hits.getHits();

        for(SearchHit searchHit: searchHits){
            ObjectNode objectNode=objectMapper.convertValue( searchHit.getSourceAsMap(), ObjectNode.class);
            objectNode.put("_id", searchHit.getId());
            arrayNode.add( objectNode);
        }

        return arrayNode;
    }

    public JsonNode index(JsonNode data, String index) throws IOException {

        IndexRequest request = new IndexRequest(index);

        request.source(data.toString(), XContentType.JSON);
        IndexResponse indexResponse=this.restHighLevelClient.index(request,RequestOptions.DEFAULT);
        ObjectNode objectNode= objectMapper.createObjectNode();
        objectNode.put("id", indexResponse.getId());
        objectNode.put("status", indexResponse.status().getStatus());

        return objectNode;
    }


    public JsonNode delete(Map<String, String[]> searchParams, String index) throws Exception {
        DeleteByQueryRequest request =
                new DeleteByQueryRequest(index);


        for(Map.Entry<String,String[]> stringEntry: searchParams.entrySet()){

            String key= stringEntry.getKey();
            String vals[]=stringEntry.getValue();
            if(vals.length==1){
                request=request.setQuery(QueryBuilders.matchQuery(key,vals[0]));
            }
            else {
                throw new Exception("only one key and value supported as of now");
            }

        }
        BulkByScrollResponse bulkResponse =
                this.restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);

        ObjectNode objectNode= objectMapper.createObjectNode();
        objectNode.put("deletedCount", bulkResponse.getStatus().getDeleted());
        return objectNode;
    }
}
