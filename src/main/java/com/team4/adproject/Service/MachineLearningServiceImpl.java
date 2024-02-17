package com.team4.adproject.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.team4.adproject.Model.Record;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.TreeMap;
import com.team4.adproject.Model.*;

@Service
public class MachineLearningServiceImpl {
    @Autowired
    private RecordServiceImpl recordService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ScheduleService scheduleService;

    public String getResult(String username, float accuracy) throws IOException {
        Date today = new Date(System.currentTimeMillis());
        User user = userService.findByUsername(username);
        Record record = recordService.findByDateAndUser(today, user);
        UserLearningSchedule schedule = scheduleService.getLearningSchedule(user.getUserId());
        Date startDate = schedule.getStartDate();
        recordService.findByUserAndSuccess(user);
        int finishedDays = recordService.findByUserAndSuccess(user).size();
        int durations = (int)  ChronoUnit.DAYS.between(startDate.toLocalDate(), today.toLocalDate());
        float checkInRate = (float) finishedDays/durations;
        int words = record.getRecordDetails().size();
        float seconds = (float) Math.abs(ChronoUnit.SECONDS.between(record.getStartTime().toLocalTime(), record.getEndTime().toLocalTime()))/words;
        URL apiUrl = new URL("http://3.27.46.145:8080/predict-user-cluster");
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        /*for test*/
        JSONObject jsonRequest = new JSONObject();
        JSONArray accuracyArray = new JSONArray();
        accuracyArray.put(accuracy);
        jsonRequest.put("accuracy", accuracyArray);

        JSONArray loginStreakArray = new JSONArray();
        loginStreakArray.put(checkInRate);
        jsonRequest.put("login_streak", loginStreakArray);

        JSONArray quizTimeTakenArray = new JSONArray();
        quizTimeTakenArray.put(seconds);
        jsonRequest.put("quiz_time_taken", quizTimeTakenArray);

        JSONArray quizWordLearntArray = new JSONArray();
        quizWordLearntArray.put(words);
        jsonRequest.put("quiz_word_learnt", quizWordLearntArray);
        String a = jsonRequest.toString();
        /*for test*/
        byte[] input = jsonRequest.toString().getBytes("utf-8");
        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(input, 0, input.length);
        }
        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }

        reader.close();
        inputStream.close();
        connection.disconnect();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(responseBuilder.toString());
        return node.get("user_cluster").asText();
    }
}
