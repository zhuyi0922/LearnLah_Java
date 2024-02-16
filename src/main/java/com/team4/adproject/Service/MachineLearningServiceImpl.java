package com.team4.adproject.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.TreeMap;

@Service
public class MachineLearningServiceImpl {
    public String getResult() throws IOException {
        URL apiUrl = new URL("http://3.27.46.145:8080/predict-user-cluster");
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        /*for test*/
        JSONObject jsonRequest = new JSONObject();
        JSONArray accuracyArray = new JSONArray();
        accuracyArray.put(0.4);
        jsonRequest.put("accuracy", accuracyArray);

        JSONArray loginStreakArray = new JSONArray();
        loginStreakArray.put(0.45);
        jsonRequest.put("login_streak", loginStreakArray);

        JSONArray quizTimeTakenArray = new JSONArray();
        quizTimeTakenArray.put(42.8);
        jsonRequest.put("quiz_time_taken", quizTimeTakenArray);

        JSONArray quizWordLearntArray = new JSONArray();
        quizWordLearntArray.put(23);
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
