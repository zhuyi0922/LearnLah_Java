package com.team4.adproject.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.adproject.Model.Book;
import com.team4.adproject.Model.Word;
import com.team4.adproject.Repository.BookRepository;
import com.team4.adproject.Repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DictionaryServiceImpl {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private WordRepository wordRepository;
/*    public void getDicFromJson(String bookName){
        String filePath = "F:\\JavaWeb_Maven_project\\AdProject\\src\\main\\resources\\BookJsonData\\" + bookName + ".json";
        Book book = new Book();
        book.setBookId(bookName);
        book.setBookId(bookName);
        List<Word> wordList = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            // 逐行读取文件
            while ((line = reader.readLine()) != null) {
                // 解析当前行的 JSON 字符串
                JsonNode rootNode = objectMapper.readTree(line);
                int wordRank = rootNode.get("wordRank").asInt();
                String headWord = rootNode.get("headWord").asText();
                // get the word node
                JsonNode word = rootNode.get("content").get("word");
                String wordId = word.get("wordId").asText();
                // get the content node
                JsonNode content = word.get("content");
                String Sentence = "";
                String CnSentence = "";
                if (content.get("sentence") != null && content.get("sentence").get("sentences")!= null) {
                    JsonNode sentences = content.get("sentence").get("sentences");
                    for (JsonNode sentenceNode : sentences) {
                        String sContent = sentenceNode.path("sContent").asText();
                        String sCn = sentenceNode.path("sCn").asText();
                        Sentence = sContent + "+" + Sentence;
                        CnSentence = sCn + "+" + CnSentence;
                    }
                }
                //
                String usphone = content.get("usphone").asText();
                String trans_cn = "";
                String trans_pos = "";
                String trans_other = "";
                JsonNode trans = content.get("trans");
                for (JsonNode tran : trans) {
                    String strans_pos = tran.get("pos")==null ? "" : tran.get("pos").asText();
                    String strans_cn = tran.get("tranCn")==null ? "" : tran.get("tranCn").asText();
                    String strans_other =  tran.get("tranOther")==null ? "" : tran.get("tranOther").asText();
                    trans_pos = strans_pos + "+" + trans_pos;
                    trans_cn = strans_cn + "+" + trans_cn;
                    trans_other = strans_other + "+" + trans_other;
                }
                Word wordObj = new Word();
                wordObj.setWordId(wordId);
                wordObj.setWordHead(headWord);
                wordObj.setWordSentence(Sentence);
                wordObj.setWordCnSentence(CnSentence);
                wordObj.setUsPhone(usphone);
                wordObj.setTrans_Cn(trans_cn);
                wordObj.setTrans_pos(trans_pos);
                wordObj.setTrans_Other(trans_other);
                wordList.add(wordObj);
                wordRepository.save(wordObj);
            }
            book.setWords(wordList);
            bookRepository.save(book);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    public void getDicNew(String bookName){
        String filePath = "F:\\JavaWeb_Maven_project\\AdProject\\src\\main\\resources\\BookJsonData\\" + bookName + ".json";
        Book book = new Book();
        book.setBookId(bookName);
        book.setName(bookName);
        bookRepository.save(book);
        List<Word> wordList = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            // 逐行读取文件
            while ((line = reader.readLine()) != null) {
                // 解析当前行的 JSON 字符串
                JsonNode rootNode = objectMapper.readTree(line);
                int wordRank = rootNode.get("wordRank").asInt();
                String headWord = rootNode.get("headWord").asText();
                // get the word node
                JsonNode word = rootNode.get("content").get("word");
                String wordId = word.get("wordId").asText();

                Word wordObj = new Word();
                wordObj.setWordId(wordId);
                wordObj.setWordHead(headWord);
                wordObj.setWordRank(wordRank);
                wordObj.setBook(book);
                wordList.add(wordObj);
                wordRepository.save(wordObj);
            }
            book.setWords(wordList);
            bookRepository.save(book);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
