package com.team4.adproject.Service;

import com.team4.adproject.Model.StatusEnum;
import com.team4.adproject.Repository.RecordDetailRepository;
import com.team4.adproject.Repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
public class RecordDetailServiceImpl implements RecordDetailService {
    @Autowired
    private RecordDetailRepository recordDetailRepository;

    public List<String> getReviewWordList(Integer userId) {
        // get today
        LocalDate today = LocalDate.now();
        Date threeDaysAgo = Date.valueOf(today.minusDays(3)) ;
        Date sevenDaysAgo = Date.valueOf(today.minusDays(7)) ;
        Date fifteenDaysAgo = Date.valueOf(today.minusDays(15)) ;
        Date todayDate = Date.valueOf(today) ;
        // 0. 前5天失败的单词
        List<String> wordList0 = recordDetailRepository.findByLastAttemptTimeBetweenAndStatus(sevenDaysAgo, todayDate, StatusEnum.Failed, userId)
                                                        .stream()
                                                        .map(x -> x.getWord().getWordId()).toList();
        // 1. 前3天背过1次的单词,
        List<String> wordList1 = recordDetailRepository.findByLastAttemptTimeBetweenAndStatus(threeDaysAgo, todayDate, StatusEnum.Success_first, userId)
                                                        .stream()
                                                        .map(x -> x.getWord().getWordId()).toList();
        // 2. 前7天到前3天背过2次的单词,
        List<String> wordList2 = recordDetailRepository.findByLastAttemptTimeBetweenAndStatus(sevenDaysAgo, threeDaysAgo, StatusEnum.Success_second, userId)
                                                        .stream()
                                                        .map(x -> x.getWord().getWordId()).toList();

        var test = recordDetailRepository.findByLastAttemptTimeBetweenAndStatus(fifteenDaysAgo, sevenDaysAgo, StatusEnum.Success_third, userId);
        // 3. 前15天到前7天背过3次的单词,
        List<String> wordList3 = recordDetailRepository.findByLastAttemptTimeBetweenAndStatus(fifteenDaysAgo, sevenDaysAgo, StatusEnum.Success_third, userId)
                                                        .stream()
                                                        .map(x -> x.getWord().getWordId()).toList();
        return Stream.concat(wordList1.stream(), Stream.concat(wordList2.stream(), wordList3.stream())).toList();

    }
}
