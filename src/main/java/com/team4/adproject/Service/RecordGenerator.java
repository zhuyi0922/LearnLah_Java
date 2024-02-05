package com.team4.adproject.Service;

import com.team4.adproject.Model.*;
import com.team4.adproject.Model.Record;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecordGenerator {

    public static void main(String[] args) {
        List<Record> records = generateRandomRecords(15);
        Record record = new Record();
        for (Record r : records) {
            List<RecordDetail> recordDetails = generateRandomRecordDetails(30, record);
            r.setRecordDetails(recordDetails);
        }


    }

    private static List<Record> generateRandomRecords(int numberOfRecords) {
        List<Record> records = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfRecords; i++) {
            Record record = new Record();
            record.setStartTime(generateRandomTime());
            record.setEndTime(generateRandomTime());
            record.setDate(generateRandomDate());
            record.setUser(generateRandomUser());
            record.setStatus(generateRandomStatus());
            records.add(record);
        }

        return records;
    }

    private static List<RecordDetail> generateRandomRecordDetails(int numberOfDetails, Record record) {
        List<RecordDetail> recordDetails = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfDetails; i++) {
            RecordDetail recordDetail = new RecordDetail();
            recordDetail.setAttempts(Integer.valueOf(random.nextInt(10) + 1));
            recordDetail.setLastAttemptTime(generateRandomDate());
            recordDetail.setWord(generateRandomWord());
            recordDetail.setStatus(generateRandomStatus());
            recordDetail.setRecord(record);
            recordDetails.add(recordDetail);
        }

        return recordDetails;
    }


    private static Time generateRandomTime() {

        Random random = new Random();


        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);


        String formattedTime = String.format("%02d:%02d:%02d", hour, minute, second);
        return Time.valueOf(formattedTime);

    }

    private static Date generateRandomDate() {

        Random random = new Random();


        int year = 1970 + random.nextInt(50);
        int month = 1 + random.nextInt(12);
        int day = 1 + random.nextInt(28);


        LocalDate localDate = LocalDate.of(year, month, day);

        return Date.valueOf(localDate);
    }

    private static User generateRandomUser() {

        User user = new User();
        user.setUsername(generateRandomString());
        user.setPassword(generateRandomString());
        return user;
    }
    private static String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < 10; i++) {

            int index = secureRandom.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    private static Word generateRandomWord() {
        return null;
    }

    private static StatusEnum generateRandomStatus() {
        StatusEnum[] statuses = StatusEnum.values();
        Random random = new Random();
        int index = random.nextInt(statuses.length);
        return statuses[index];
    }
}
