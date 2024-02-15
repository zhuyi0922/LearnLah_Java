package com.team4.adproject.Model;

import lombok.Value;

import java.io.Serializable;
import java.sql.Date;

/**
 * DTO for {@link UserLearningSchedule}
 */
@Value
public class UserLearningScheduleDto implements Serializable {
    Integer dailyWords;
    Date exceptedEndDate;
    Date startDate;
    Integer wordIndex;
    String bookName;

}