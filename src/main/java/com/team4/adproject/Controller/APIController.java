package com.team4.adproject.Controller;

import com.team4.adproject.Model.Book;
import com.team4.adproject.Model.User;
import com.team4.adproject.Model.Word;
import com.team4.adproject.Service.BookServiceImpl;
import com.team4.adproject.Service.RecordDetailService;
import com.team4.adproject.Service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class APIController {
    @Autowired
    private RecordDetailService recordDetailService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private BookServiceImpl bookService;

    @GetMapping("/reviewWordList")
    public List<String> getReviewWordList() {
        //return reviewWordIdList;
        return recordDetailService.getReviewWordList();
    }

    @GetMapping("/curentWordIndex")
    public int getWordIndex(int userId, String bookId) {
        // 注意前端回来的时候这个int类型的转换
        return scheduleService.getWordIndex(userId, bookId);
    }

    @GetMapping("/getBook")
    // no need to implement serializable, jackson is already integrated with spring
    public List<Word> getBook(String bookId) {
        return bookService.findByBookId(bookId).getWords();
    }

    @GetMapping("/getBookList")
    public List<Book> getBookList() {
        return bookService.findAllBook();
    }

}
