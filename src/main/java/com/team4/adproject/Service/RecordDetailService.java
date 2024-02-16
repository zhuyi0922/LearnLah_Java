package com.team4.adproject.Service;

import com.team4.adproject.Model.Record;
import com.team4.adproject.Model.RecordDetail;
import com.team4.adproject.Model.Word;

import java.util.List;

public interface RecordDetailService {
    public List<Integer> getReviewWordList(Integer userId);
    public RecordDetail findByRecordAndWord(Record record, Word word);

    void addRecordDetail(RecordDetail recordDetail);
}
