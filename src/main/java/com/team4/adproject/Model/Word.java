package com.team4.adproject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "word")
public class Word {
    @Id
    @Column(name = "word_id", nullable = false)
    private String wordId;

    @Column(name = "wordRank")
    private int wordRank;

    @Column(name = "word_head")
    private String wordHead;

/*    @Column(name = "word_sentence")
    private String wordSentence;

    @Column(name = "word_cn_sentence")
    private String wordCnSentence;

    @Column(name = "us_phone")
    private String usPhone;

    @Column(name = "us_pronounciation")
    private String usPronounciation;

    @Column(name = "trans_trans_cn")
    private String trans_Cn;

    @Column(name = "trans_pos")
    private String trans_pos;

    @Column(name = "trans_trans_other")
    private String trans_Other;*/


    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

}