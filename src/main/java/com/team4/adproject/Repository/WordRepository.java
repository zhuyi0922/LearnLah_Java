package com.team4.adproject.Repository;

import com.team4.adproject.Model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, String> {

}