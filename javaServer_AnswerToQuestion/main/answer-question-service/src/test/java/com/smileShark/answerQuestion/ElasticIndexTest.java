package com.smileShark.answerQuestion;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ElasticIndexTest {
    private RestHighLevelClient client;

    @Test
    void testConnection() {
        System.out.println("Testing connection to Elasticsearch =" + client);
    }

    @Test
    void testCreateHotelIndex() throws IOException {
        // 1. 创建Request对象
        CreateIndexRequest request = new CreateIndexRequest("items");
        // 2. 请求参数，MAPPING_TEMPLATE是静态常量字符串，内容是JSON格式请求体
        request.source("{\n" +
                "  \"mappings\": {\n" +
                "    \"properties\": {\n" +
                "      \"info\":{\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"ik_smart\"\n" +
                "      },\n" +
                "      \"age\":{\n" +
                "        \"type\": \"integer\"  // 修改为 integer 类型\n" +
                "      },\n" +
                "      \"email\":{\n" +
                "        \"type\": \"keyword\",\n" +
                "        \"index\": false\n" +
                "      },\n" +
                "      \"name\":{\n" +
                "        \"type\": \"object\",\n" +
                "        \"properties\": {\n" +
                "          \"firstName\":{\n" +
                "            \"type\":\"keyword\"\n" +
                "          },\n" +
                "          \"lastName\":{\n" +
                "            \"type\":\"keyword\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}", XContentType.JSON);
        // 3. 发起请求
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    void testExistsHotelIndex() throws IOException {
        // 1. 创建Request对象
        GetIndexRequest request = new GetIndexRequest("items");
        // 2. 发起请求
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println("exists=" + exists);
    }

    @BeforeEach
    void setUp() {
        client = new RestHighLevelClient(
                RestClient.builder(
                        HttpHost.create("http://localhost:9201")
                )
        );
    }

    @AfterEach
    void tearDown() throws IOException {
        if (client != null) {
            client.close();
        }
    }
}
