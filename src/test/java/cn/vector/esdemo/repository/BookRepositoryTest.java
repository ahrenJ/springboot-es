package cn.vector.esdemo.repository;

import cn.vector.esdemo.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    //创建一些mock数据
    @Test
    void createData() {
        Book book1 = new Book(1, "富婆寻找指南", new BigDecimal("23.33"), Arrays.asList("指南", "富婆"));
        Book book2 = new Book(2, "程序员的头发护理指南", new BigDecimal("28.00"), Arrays.asList("指南", "程序员", "头发"));
        Book book3 = new Book(3, "Java从入门到跑路", new BigDecimal("29.90"), Arrays.asList("Java", "编程", "入门教程"));
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
    }

    @Test
    void findByPriceBetween() {
        List<Book> books = bookRepository.findByPriceBetween(new BigDecimal("15.00"), new BigDecimal("30.00"));
        System.out.println(books);
    }

    @Test
    void findByTitle() {
        List<Book> books = bookRepository.findByTitle("Java");
        System.out.println(books);
    }

    @Test
    void findByTagIn() {
        List<Book> books = bookRepository.findByTagIn(Arrays.asList("Java", "数据库"));
        System.out.println(books);
    }
}