package com.team4.adproject.Service;

import com.team4.adproject.Model.User;
import com.team4.adproject.Model.UserLearningSchedule;
import com.team4.adproject.Repository.UserLearningScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    @Autowired
    private UserLearningScheduleRepository repo;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private BookServiceImpl bookService;
    @Override
    public int getWordIndex(int userId) {
        return repo.findByUser_UserId(userId).getWordIndex();
    }

    @Override
    public boolean setLearningSchedule(Map<String, String> map) {
        var schedule = new UserLearningSchedule();
        schedule.setUser(userService.findByUsername(map.get("username")));
        schedule.setBook(bookService.findByBookId(map.get("bookId")));
        schedule.setWordIndex(0);
        schedule.setDailyWords(Integer.parseInt(map.get("dailyWords")));
        schedule.setStartDate(Date.valueOf(map.get("startDate")));
        schedule.setExceptedEndDate(Date.valueOf(map.get("exceptedEndDate")));
        repo.save(schedule);
        return true;
    }
    @Override
    public UserLearningSchedule getLearningSchedule(int userId) {
        return repo.findByUser_UserId(userId);
    }

    @Override
    public void updateWordIndex(UserLearningSchedule schedule) {
        repo.save(schedule);
    }
}
