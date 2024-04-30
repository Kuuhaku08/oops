package com.example.socialmediaplatform.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postID;

    private String postBody;

    private Integer userID;


    @ElementCollection
    private List<Integer> commentList = new ArrayList<>();

}
