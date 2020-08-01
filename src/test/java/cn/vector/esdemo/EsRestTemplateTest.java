package cn.vector.esdemo;

import cn.vector.esdemo.entity.Book;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.math.BigDecimal;

/**
 * ElasticsearchRestTemplate的简单使用
 * Create By ahrenJ
 * Date: 2020-08-01
 */
@SpringBootTest
public class EsRestTemplateTest {

    //自动注入即可使用
    @Autowired
    private ElasticsearchRestTemplate esRestTemplate;

    //按id查询
    @Test
    void testQueryBookById() {
        Book book = esRestTemplate.get("1", Book.class);
        Assertions.assertNotNull(book);
        System.out.println(book.toString());
    }

    //按书名查询
    @Test
    void testQueryBookByTitle() {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("title", "Java"))
                .build();
        SearchHits<Book> searchHits = esRestTemplate.search(searchQuery, Book.class);
        //SearchHits就是查询的结果集
        searchHits.get().forEach(hit -> {
            System.out.println(hit.getContent());
        });
    }

    //按价格区间查询
    @Test
    void testQueryBookByPriceInternal() {
        BigDecimal min = new BigDecimal("15.00");
        BigDecimal max = new BigDecimal("30.00");
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.rangeQuery("price")
                        .gte(min)
                        .lte(max))
                .build();
        SearchHits<Book> searchHits = esRestTemplate.search(searchQuery, Book.class);
        searchHits.get().forEach(hit -> {
            System.out.println(hit.getContent());
        });
    }

    //按标签匹配查询
    @Test
    void testQueryBookByTag() {
        //查询标签中含有Java和数据库的书籍
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termsQuery("tag", "Java", "数据库"))
                .build();
        SearchHits<Book> searchHits = esRestTemplate.search(searchQuery, Book.class);
        searchHits.get().forEach(hit -> {
            System.out.println(hit.getContent());
        });
    }

    //聚合操作-计算所有书籍的平均价格
    @Test
    void testAggregationBookAvgPrice() {
        //聚合名为avg_price，对price字段进行聚合，计算平均值
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .addAggregation(AggregationBuilders.avg("avg_price").field("price"))
                .build();
        SearchHits<Book> searchHits = esRestTemplate.search(searchQuery, Book.class);
        searchHits.get().forEach(hit -> {
            System.out.println(hit.getContent());
        });
        //获取聚合结果
        if (searchHits.hasAggregations()) {
            ParsedAvg parsedAvg = searchHits.getAggregations().get("avg_price");
            Assertions.assertNotNull(parsedAvg, "无聚合结果");
            System.out.println(parsedAvg.getValue());
        }
    }
}
