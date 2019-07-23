package com.pravah.web.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;


@Service
public class TicketsDao  {

    private RestHighLevelClient restHighLevelClient;

    private ObjectMapper objectMapper= new ObjectMapper();
    public TicketsDao(){

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
    public JsonNode findByAssignedto(String name){

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchHits hits = null;
        try {
            SearchResponse searchResponse  = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            hits = searchResponse.getHits();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }


        if(Objects.nonNull(hits)) {
            return objectMapper.convertValue(hits, JsonNode.class);
        }
        return null;

    }
    public JsonNode findByLoggedby(String name){

        return null;
    }
    public JsonNode getAllTickets( String name){

        return null;
    }
    public JsonNode addTicket( JsonNode ticket) throws IOException {

        IndexRequest request = new IndexRequest("tickets");

        request.source(ticket, XContentType.JSON);
        IndexResponse indexResponse=this.restHighLevelClient.index(request,RequestOptions.DEFAULT);
        return new ObjectMapper().convertValue(indexResponse,JsonNode.class);
    }

}
