package com.pravah.web.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pravah.web.utils.Constants;
import com.pravah.web.utils.ElasticSearchClient;
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
import org.elasticsearch.index.mapper.IdFieldMapper;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Objects;


@Service
public class TicketsDao  {

    @Autowired
    private ElasticSearchClient elasticSearchClient;

    public JsonNode searchTickets(Map<String,String[]> searchParams) throws Exception {
        return elasticSearchClient.search(searchParams);
    }

    public JsonNode addTicket( JsonNode ticket) throws IOException {
        return elasticSearchClient.index(ticket, Constants.TICKETS_INDEX);
    }
    public JsonNode deleteTickets(Map<String,String[]> searchParams) throws Exception {
        return elasticSearchClient.delete(searchParams,Constants.TICKETS_INDEX);
    }

    public JsonNode getCountBy(String field) throws Exception {
        return elasticSearchClient.getCountByFeild(field);
    }

}
