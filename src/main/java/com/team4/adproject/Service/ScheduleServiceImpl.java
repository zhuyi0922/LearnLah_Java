package com.team4.adproject.Service;

import com.team4.adproject.Repository.UserLearningScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    @Autowired
    private UserLearningScheduleRepository repo;
    @Override
    public int getWordIndex(int userId, String bookId) {
        return repo.findByUser_UserIdAndBook_BookId(userId,bookId).getWordIndex();
    }

}
