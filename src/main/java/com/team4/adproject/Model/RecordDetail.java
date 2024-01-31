package com.team4.adproject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "record_detail")
public class RecordDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", nullable = false)
    private Integer recordId;

    @Column(name = "attempts")
    private Integer attempts;


    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record record;

    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition="ENUM()")
    private StatusEnum status;

// maybe need a time to record the date,, for the strategy of generating reviewing wordList
}