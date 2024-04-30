package com.example.socialmediaplatform.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer postID;

    private String postBody;

    private Integer userID;

    private LocalDate date=LocalDate.now();

    @ElementCollection
    private List<Integer> commentList = new ArrayList<>();

}