package cn.vector.esdemo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.List;

/**
 * Create By ahrenJ
 * Date: 2020-08-01
 * <p>
 * 如果不使用@Field注解指定数据类型的话
 * Spring Data Elasticsearch框架会自动映射数据类型，如果es服务器中不存在索引的话会自动创建
 */
@Document(indexName = "book")
public class Book {

    //@Id注解表明该字段是文档id
    @Id
    private Integer id;

    private String title;

    private BigDecimal price;

    @Field(type = FieldType.Keyword)
    private List<String> tag;

    public Book(Integer id, String title, BigDecimal price, List<String> tag) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", tag=" + tag +
                '}';
    }
}
