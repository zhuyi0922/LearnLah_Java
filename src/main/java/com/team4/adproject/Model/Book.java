package com.team4.adproject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "book_id", nullable = false)
    private String bookId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "book", orphanRemoval = true)
    private List<Word> words = new ArrayList<>();

}