package com.example.lxcblog.service.blog;

import com.alibaba.fastjson.JSON;
import com.example.lxcblog.entity.Blog.Article;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.profile.ProfileShardResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ESArticleService {

    @Resource
    private RestHighLevelClient client;


    /**
     * 查询是否存在索引
     * 如果不存在索引
     *      创建一个新的索引
     * @throws IOException
     */
    public void existIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("alxcy_blog");
        if(!client.indices().exists(request,RequestOptions.DEFAULT)){
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("alxcy_blog");
            CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest , RequestOptions.DEFAULT);
        }
    }

    /**
     * 向索引中添加文档
     * @param article
     * @throws IOException
     */
    public void addArticle(Article article) throws IOException{
        IndexRequest request = new IndexRequest("article");
        request.id(String.valueOf(article.getTextUid()));
        Map<String , Object> jsonMap = new HashMap<>();
        jsonMap.put("textUid" , article.getTextUid());
        jsonMap.put("title" , article.getTitle());
        jsonMap.put("userUid" , article.getUserUid());
        request.source(jsonMap , XContentType.JSON);
        IndexResponse response = client.index(request , RequestOptions.DEFAULT);
    }


    public List<Object> searchDocument(String text) throws IOException{
        List<Object> list = new ArrayList<>();
        // 请求article的索引
        SearchRequest request = new SearchRequest("article");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 设置查询返回的列表大小为 5
        // 设置查询时间最大值为 10s
        searchSourceBuilder.size(5).timeout(new TimeValue(10 , TimeUnit.SECONDS));
        // 在返回值中选取key为title的值
        searchSourceBuilder.query(QueryBuilders.termQuery("title", text));
        request.source(searchSourceBuilder);

        SearchResponse response = client.search(request , RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();

        // 将数据赋值给list列表
        for(int i = 0 ; i < hits.getHits().length ; i++){
            SearchHit hit = hits.getHits()[i];
            list.add(hit.getSourceAsMap());
        }

        return list;
    }
}
