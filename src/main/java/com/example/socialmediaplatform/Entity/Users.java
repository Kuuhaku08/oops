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

public class Users {

    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userID;

    @Column(name="email",unique = true)
    private String email;

    private String password;

}
