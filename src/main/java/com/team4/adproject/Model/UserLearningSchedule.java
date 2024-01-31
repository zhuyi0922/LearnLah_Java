package com.team4.adproject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "user_learning_schedule")
public class UserLearningSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "learning_schedule_id", nullable = false)
    private Integer learningScheduleId;

    @Column(name = "daily_words")
    private Integer dailyWords;

    @Column(name = "excepted_end_date")
    private Date exceptedEndDate;

    @Column(name = "start_date")
    private Date startDate;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "word_index")
    private Integer wordIndex;

}