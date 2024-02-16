package com.team4.adproject.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordDetailsListDTO {
    private StatusEnum status;
    private String username;
    private Date today;
    private Time startTime;
    private Time endTime;
    private List<RecordDetailPOJO> recordDetailPOJOList;
}
