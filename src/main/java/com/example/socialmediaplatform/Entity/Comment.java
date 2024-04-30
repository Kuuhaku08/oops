package com.example.socialmediaplatform.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int commentID;

    private String commentBody;

    private Integer postID;

    private Integer userID;
}
