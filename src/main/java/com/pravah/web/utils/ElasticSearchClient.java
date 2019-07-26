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
import org.elasticsearch.action.update.UpdateRequest;
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
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.json.Json;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Objects;

@Service
public class ElasticSearchClient {
    private RestHighLevelClient restHighLevelClient;

    private ObjectMapper objectMapper=new  ObjectMapper();
    public ElasticSearchClient(){

        String connString = "https://b57p3oob2s:1woc8y9a95@privet-939751886.us-east-1.bonsaisearch.net:443";
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
        boolQueryBuilder = generateSearchQueryByNonReservedParams(searchParams, boolQueryBuilder);
        boolQueryBuilder = generateSearchQueryByCordinates(searchParams, boolQueryBuilder);
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

    private TermsAggregationBuilder getTermAggregationBuilder(String field) {
        return AggregationBuilders.terms("status_count").field(field);
    }

    private BoolQueryBuilder generateSearchQueryByCordinates(Map<String, String[]> searchParams, BoolQueryBuilder boolQueryBuilder) {
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
        return boolQueryBuilder;
    }

    private BoolQueryBuilder generateSearchQueryByNonReservedParams(Map<String, String[]> searchParams, BoolQueryBuilder boolQueryBuilder) throws Exception {
        for(Map.Entry<String,String[]> stringEntry: searchParams.entrySet()){

            String key= stringEntry.getKey();
            String vals[]=stringEntry.getValue();
            if(isReservedParamKey(key)){
                continue;
            }
            if(vals.length==1){
                boolQueryBuilder=boolQueryBuilder.should(QueryBuilders.matchQuery(key,vals[0]));
            }
            else {
                throw new Exception("only one key and value supported as of now");
            }
        }
        return boolQueryBuilder;
    }

    private boolean isReservedParamKey(String key) {
        return key.equalsIgnoreCase(Constants.LATITUDE)
                || key.equalsIgnoreCase(Constants.LONGITUDE)
                || key.equalsIgnoreCase(Constants.DISTANCE_IN_KM)
        || key.equalsIgnoreCase(Constants.COUNT_BY)
                || key.equalsIgnoreCase(Constants.FIELD);
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

    public JsonNode getCountByFeild(Map<String,String[]> searchParams) throws Exception {
        String field= searchParams.get(Constants.FIELD)[0];
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        boolQueryBuilder = generateSearchQueryByNonReservedParams(searchParams, boolQueryBuilder);

        searchSourceBuilder.query(boolQueryBuilder);
        TermsAggregationBuilder cardinalityAggregationBuilder = getTermAggregationBuilder(field);
        if(Objects.nonNull(cardinalityAggregationBuilder)) {
            searchSourceBuilder.aggregation(cardinalityAggregationBuilder);
        }
        searchRequest.source(searchSourceBuilder);


        SearchResponse searchResponse  = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Terms terms = searchResponse.getAggregations().get(field+"_count");
        ArrayNode arrayNode= objectMapper.createArrayNode();
        for(Terms.Bucket bucket: terms.getBuckets()){
            ObjectNode counts= objectMapper.createObjectNode();
            counts.put(bucket.getKeyAsString(), bucket.getDocCount());
            arrayNode.add(counts);
        }
        return arrayNode;

    }

    public JsonNode update(JsonNode data) throws Exception{
        if(!data.has("_id")){
            throw new Exception("key not found");
        }
        String id= data.get("_id").asText();
        ObjectNode dataObject= (ObjectNode) data;
        dataObject.remove("_id");
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("tickets");
        updateRequest.doc(data.toString(), XContentType.JSON);
        updateRequest.id(id);

        return objectMapper.convertValue(this.restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT), JsonNode.class);
    }
}
