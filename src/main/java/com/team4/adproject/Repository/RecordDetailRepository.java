package com.team4.adproject.Repository;

import com.team4.adproject.Model.RecordDetail;
import com.team4.adproject.Model.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

public interface RecordDetailRepository extends JpaRepository<RecordDetail, Integer> {


    List<RecordDetail> findByLastAttemptTimeBetweenAndStatus(Date lastAttemptTimeStart, Date lastAttemptTimeEnd, StatusEnum status);
}