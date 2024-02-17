package com.team4.adproject.Controller;

import com.team4.adproject.Model.*;
import com.team4.adproject.Model.Record;
import com.team4.adproject.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.net.URL;
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
    @Autowired
    private RecordServiceImpl recordService;
    @Autowired
    private WordServiceImpl wordService;
    @Autowired
    private MachineLearningServiceImpl machineLearningService;

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
    public Boolean addRecord(@RequestBody RecordDetailsListDTO dto) {
        // 记得新增一条record, 记得修改user的学习进度wordIndex
        // 先判断是否已经有了这个record
        Record record = new Record();
        var originRecord = recordService.findByDateAndUser(dto.getToday(), userService.findByUsername(dto.getUsername()));
        if (originRecord == null){
            // 为null, 则新增record, 然后再添加recordDetail
            record.setDate(dto.getToday());
            record.setUser(userService.findByUsername(dto.getUsername()));
            record.setStatus(dto.getStatus());
            record.setStartTime(dto.getStartTime());
            recordService.addRecord(record);
        }
        else if (dto.getStatus()==StatusEnum.Finished){
            record = originRecord;
            record.setStatus(dto.getStatus());
            record.setEndTime (dto.getEndTime());
            // userschedule的finished day
            recordService.addRecord(record);
        }
        else{
            record = originRecord;
        }
        int newWordSize = 0;
        // 如果有了, 则直接添加recordDetail 或者 更改 已存在的recordDetail
        for (RecordDetailPOJO recordDetailPOJO : dto.getRecordDetailPOJOList()) {
            Word word = wordService.findByWordId(recordDetailPOJO.getWordId());
            RecordDetail recordDetail =  recordDetailService.findByRecordAndWord(record, word);
            if ( recordDetail != null){
                // 已经背过这个词, 更新记录
                switch (recordDetailPOJO.getStatus()){
                    case Success_first:
                        recordDetail.setStatus(StatusEnum.Success_second);
                        break;
                    case Success_second:
                        recordDetail.setStatus(StatusEnum.Success_third);
                        break;
                    case Failed:
                        recordDetail.setStatus(StatusEnum.Failed);
                        break;
                }
                recordDetail.setLastAttemptTime(dto.getToday());
                recordDetailService.addRecordDetail(recordDetail);
                continue;
            }

            // 第一次背这个词, 新增记录
            var recordDetailNew = new RecordDetail();
            recordDetailNew.setRecord(record);
            recordDetailNew.setWord(word);
            recordDetailNew.setAttempts(recordDetailPOJO.getAttempts());
            recordDetailNew.setStatus(recordDetailPOJO.getStatus());
            recordDetailNew.setLastAttemptTime(dto.getToday());
            recordDetailService.addRecordDetail(recordDetailNew);
            newWordSize++;
        }
        // 最后要更新wordIndex, 但是还没今日任务是否完成
        UserLearningSchedule userLearningSchedule = scheduleService.getLearningSchedule(userService.findByUsername(dto.getUsername()).getUserId());
        userLearningSchedule.setWordIndex(userLearningSchedule.getWordIndex() + newWordSize);
        scheduleService.updateWordIndex(userLearningSchedule);
        return true;
    }

    @GetMapping("/getResult")
    public String getResult(@RequestParam("username") String username, @RequestParam("accuracy") float accuracy) {
        try {
            return machineLearningService.getResult(username, accuracy);
        }
        catch (Exception e){
            return "error";
        }
    }
}
