package com.team4.adproject.Service;

import com.team4.adproject.Model.UserLearningSchedule;

import java.util.Map;

public interface ScheduleService {
    public int getWordIndex(int userId);
    public boolean setLearningSchedule(Map<String, String> map);
    public UserLearningSchedule getLearningSchedule(int userId);
    public void updateWordIndex(UserLearningSchedule schedule);
}
