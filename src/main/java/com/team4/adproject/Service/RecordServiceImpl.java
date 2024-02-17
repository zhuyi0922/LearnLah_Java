package com.team4.adproject.Service;

import com.team4.adproject.Model.*;
import com.team4.adproject.Model.Record;
import com.team4.adproject.Repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class RecordServiceImpl {
    @Autowired
    private RecordRepository recordRepository;
    public void saveRecord(Record record) {
        recordRepository.save(record);
    }
    public Record findByDateAndUser(Date date, User user) {
        return recordRepository.findByDateAndUser(date, user);
    }


    public void addRecord(Record record) {
        recordRepository.save(record);
    }

    public List<Record> findByUserAndSuccess(User user) {
        return recordRepository.findByUserAndStatus(user, StatusEnum.Finished);
    }
}
