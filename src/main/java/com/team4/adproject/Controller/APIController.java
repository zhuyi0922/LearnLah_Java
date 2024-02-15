package com.team4.adproject.Controller;

import com.team4.adproject.Model.*;
import com.team4.adproject.Model.Record;
import com.team4.adproject.Service.BookServiceImpl;
import com.team4.adproject.Service.RecordDetailService;
import com.team4.adproject.Service.ScheduleService;
import com.team4.adproject.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class APIController {
    @Autowired
    private RecordDetailService recordDetailService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/reviewWordList")
    public Map<String,List<Integer>> getReviewWordList(@RequestParam("username") String username) {
        var userId = userService.findByUsername(username).getUserId();
        return Map.of("list",recordDetailService.getReviewWordList(userId)) ;
    }

    @GetMapping("/getCurrentWordIndex")
    public int getWordIndex(@RequestParam("username") String username) {
        var userId = userService.findByUsername(username).getUserId();
        return scheduleService.getWordIndex(userId);
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

    @PostMapping("/setLearningSchedule")
    public Boolean setLearningSchedule(@RequestBody Map<String, String> map) {
        return scheduleService.setLearningSchedule(map);
    }

    @GetMapping("/getLearningSchedule")
    public Map<String,String> getLearningSchedule(@RequestParam("username") String username) {
        var userId = userService.findByUsername(username).getUserId();
         var schedule = scheduleService.getLearningSchedule(userId);
            return Map.of(
                    "bookId", schedule.getBook().getBookId(),
                    "dailyWords", String.valueOf(schedule.getDailyWords()),
                    "startDate", schedule.getStartDate().toString(),
                    "exceptedEndDate", schedule.getExceptedEndDate().toString()
            );
    }

    @PostMapping("/login")
    public Boolean login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        return userService.login(username, password);
    }

    @PostMapping("/register")
    public Boolean register(String username, String password) {
        return userService.register(username, password);
    }

    @PostMapping("/addRecord")
    public Boolean addRecord(@RequestBody Map<String, String> map) {
        // 记得新增一条record, 记得修改user的学习进度wordIndex
        var record = new Record();
        record.setUser(userService.findByUsername(map.get("username")));
        record.setStatus(StatusEnum.values()[Integer.parseInt(map.get("status"))]);
        record.setStartTime(Time.valueOf(map.get("startTime")));
        record.setEndTime(Time.valueOf(map.get("endTime")));
        record.setDate(Date.valueOf(map.get("date")));
        //recordDetailService.addRecord(record);
        return true;
    }
}
