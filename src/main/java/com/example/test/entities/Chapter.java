package com.example.test.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chapter")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int number;
    private String name;
    private String text;

    public Chapter(int number, String name, String text) {
        this.number = number;
        this.name = name;
        this.text = text;
    }

}
