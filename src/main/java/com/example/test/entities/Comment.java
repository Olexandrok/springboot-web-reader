package com.example.test.entities;

import lombok.*;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "comment")
public class Comment{

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;
    private String text;

    @Lob
    @Setter(AccessLevel.NONE)
    private String image;

    private Long userId;
    private Date dateOfCreation;

    public Comment(String userName, String text, Long bookId, Long userId, String image) throws UnsupportedEncodingException {
        this.dateOfCreation = new Date();
        this.userName = userName;
        this.text = text;
        this.userId = userId;
        this.image = image;
    }

    public void setImage(byte[] image) throws UnsupportedEncodingException {
        this.image = new String(java.util.Base64.getEncoder().encode(image), "UTF-8");
    }

}