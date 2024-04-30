package com.example.socialmediaplatform.Repo;

import com.example.socialmediaplatform.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
