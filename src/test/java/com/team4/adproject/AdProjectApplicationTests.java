package com.team4.adproject;

import com.team4.adproject.Model.*;
import com.team4.adproject.Model.Record;
import com.team4.adproject.Repository.RecordDetailRepository;
import com.team4.adproject.Repository.RecordRepository;
import com.team4.adproject.Repository.WordRepository;
import com.team4.adproject.Service.DictionaryServiceImpl;
import com.team4.adproject.Service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@SpringBootTest
class AdProjectApplicationTests {
	@Autowired
	private DictionaryServiceImpl dictionaryService;
	@Autowired
	private RecordRepository recordRepository;
	@Autowired
	private RecordDetailRepository recordDetailRepository;
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private WordRepository wordRepository;
	@Test
	void TestLoadJson() {
		dictionaryService.getDicNew("IELTSluan_2");
	}

	@Test
	void GenerateRecord() {
		int numberOfRecords = 15;
		LocalDate currentDate = LocalDate.now().minusDays(numberOfRecords);
		Random random = new Random();
		Set<Integer> wordRanks = new HashSet<>();
		while (wordRanks.size() < 300) {
			wordRanks.add(random.nextInt(3100));
		}
		List<Integer>  wordRankList = wordRanks.stream().toList();

		// 1. create user
		User user = userService.findByUsername("zhu");
		for (int i = 0; i < numberOfRecords; i++) {
			Record record = new Record();
			record.setUser(user);
			// 2. create status { values is an array}
			record.setStatus(StatusEnum.values()[new Random().nextInt(2)]);

			LocalTime startTime = generateRandomTime();
			LocalTime endTime = generateRandomTimeAfter(startTime);

			record.setStartTime(Time.valueOf(startTime));
			record.setEndTime(Time.valueOf(endTime));

			record.setDate(Date.valueOf(currentDate.plusDays(i)));
			recordRepository.save(record);
			var list = generateRandomRecordDetails(record,wordRankList.subList(i*20,20*(i+1)) );
			record.setRecordDetails(list);
			recordRepository.save(record);
		}

	}

	private static LocalTime generateRandomTime() {
		Random random = new Random();
		int hour = random.nextInt(24);
		int minute = random.nextInt(60);
		int second = random.nextInt(60);
		return LocalTime.of(hour, minute, second);
	}

	private static LocalTime generateRandomTimeAfter(LocalTime startTime) {
		Random random = new Random();
		int hour = random.nextInt(24 - startTime.getHour()) + startTime.getHour();
		int minute = random.nextInt(60 - startTime.getMinute()) + startTime.getMinute();
		int second = random.nextInt(60 - startTime.getSecond()) + startTime.getSecond();
		return LocalTime.of(hour, minute, second);
	}
	public List<RecordDetail> generateRandomRecordDetails(Record record, List<Integer> wordRanks) {
		List<RecordDetail> recordDetails = new ArrayList<>();
		int num = 20;

		for (int i = 0; i < num; i++){
			RecordDetail recordDetail = new RecordDetail();
			recordDetail.setRecord(record);

			recordDetail.setWord(wordRepository.findByWordRank(wordRanks.get(i)));
			int statusIndex = new Random().nextInt(2,6);
			recordDetail.setStatus(StatusEnum.values()[statusIndex]);
			recordDetail.setAttempts(new Random().nextInt(statusIndex-1, 20));
			Random random = new Random();
			LocalDate date = record.getDate().toLocalDate();
			recordDetail.setLastAttemptTime(Date.valueOf(date.plusDays(random.nextInt(32-date.getDayOfMonth()))));
			recordDetails.add(recordDetail);
			recordDetailRepository.save(recordDetail);
		}
		return recordDetails;
	}
}

