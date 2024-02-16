package com.team4.adproject.Service;

import com.team4.adproject.Model.Word;
import com.team4.adproject.Repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordServiceImpl {
    @Autowired
    private WordRepository wordRepository;
    public Word findByWordId(String wordId) {
        return wordRepository.findByWordId(wordId);
    }
}
