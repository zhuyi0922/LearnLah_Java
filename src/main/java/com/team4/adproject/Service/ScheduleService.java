package com.team4.adproject.Service;

import java.util.Map;

public interface ScheduleService {
    public int getWordIndex(int userId);
    public boolean setLearningSchedule(Map<String, String> map);
}
