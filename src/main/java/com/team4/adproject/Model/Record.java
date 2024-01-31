package com.team4.adproject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recordId", nullable = false)
    private Integer recordId;

    // here the Time is from java.sql.Time, not java.util.Time
    // only contain the hr, min, sec
    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "end_time")
    private Time end_time;

    @Column(name = "date")
    private String date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition="ENUM('Finished', 'Unfinished')")
    private StatusEnum status;

    @OneToMany(mappedBy = "record", orphanRemoval = true)
    private List<RecordDetail> recordDetails = new ArrayList<>();

}