package com.team4.adproject.Repository;

import com.team4.adproject.Model.UserLearningSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLearningScheduleRepository extends JpaRepository<UserLearningSchedule, Integer> {
    UserLearningSchedule findByUserId(int userId);

    UserLearningSchedule findByUser_UserIdAndBook_BookId(Integer userId, String bookId);
}