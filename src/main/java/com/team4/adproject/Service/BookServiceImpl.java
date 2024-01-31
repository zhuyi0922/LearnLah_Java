package com.team4.adproject.Service;

import com.team4.adproject.Model.Book;
import com.team4.adproject.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl {
    @Autowired
    private BookRepository repo;
    public List<Book> findAllBook() {
        return repo.findAllBook();
    }
    public Book findByBookId(String bookId) {
        return repo.findByBookId(bookId);
    }
}
