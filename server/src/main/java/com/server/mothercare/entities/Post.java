package com.server.mothercare.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity(name = "Post")
@Data
@RequiredArgsConstructor
public class Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "text")
    private String text;

    @Column(name = "type")
    private String type;

    @Column(name = "date")
    private Timestamp date;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id",referencedColumnName = "id")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = true)
    User user;

    public Post(String text, String type, Timestamp
            date, User user) {
        this.text = text;
        this.type = type;
        this.date = date;
        this.user = user;
    }

//    public Post(String text) {
//        this.text = text;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public Timestamp getDate() {
//        return date;
//    }
//
//    public void setDate(Timestamp date) {
//        this.date = date;
//    }
//
//    public Image getImage() {
//        return image;
//    }
//
//    public void setImage(Image image) {
//        this.image = image;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
