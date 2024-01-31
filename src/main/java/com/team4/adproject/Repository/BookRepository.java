package com.team4.adproject.Repository;

import com.team4.adproject.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    Book findByBookId(String bookId);
    @Query(value = "select b from Book b")
    List<Book> findAllBook();
}