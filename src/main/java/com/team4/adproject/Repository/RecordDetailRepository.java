package com.team4.adproject.Repository;

import com.team4.adproject.Model.RecordDetail;
import com.team4.adproject.Model.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

public interface RecordDetailRepository extends JpaRepository<RecordDetail, Integer> {
    @Query("SELECT rd FROM RecordDetail rd WHERE rd.lastAttemptTime BETWEEN :TimeStart AND :TimeEnd AND rd.status = :status And rd.record.user.userId = :userId")
    List<RecordDetail> findByLastAttemptTimeBetweenAndStatus(@Param("TimeStart") Date lastAttemptTimeStart, @Param("TimeEnd") Date lastAttemptTimeEnd, @Param("status") StatusEnum status,@Param("userId") Integer userId);

}