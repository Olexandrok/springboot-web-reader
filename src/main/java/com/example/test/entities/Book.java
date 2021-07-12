package com.example.test.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private  String name;
    private String preview;
    private Long userId;
    private String author;

    @ElementCollection
    private Set<String> fandom = new HashSet<String>();

    @ElementCollection
    private Set<String> tags = new HashSet<String>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private List<Chapter> chapters;

    @Lob
    @Setter(AccessLevel.NONE)
    private String image;

    public void setImage(byte[] image) throws UnsupportedEncodingException {
        this.image = new String(java.util.Base64.getEncoder().encode(image), "UTF-8");
    }

}
