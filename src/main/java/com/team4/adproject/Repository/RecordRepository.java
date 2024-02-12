package com.team4.adproject.Repository;

import com.team4.adproject.Model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Integer> {
}