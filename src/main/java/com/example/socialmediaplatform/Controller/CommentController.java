package com.example.socialmediaplatform.Controller;

import com.example.socialmediaplatform.Entity.Comment;
import com.example.socialmediaplatform.Entity.Post;
import com.example.socialmediaplatform.Entity.Users;
import com.example.socialmediaplatform.Repo.CommentRepo;
import com.example.socialmediaplatform.Repo.PostRepo;
import com.example.socialmediaplatform.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class CommentController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    //Endpoints related to Comments

    @PostMapping("/comment")
    public Object comment(@RequestBody Comment comment) {
        Map<String, Object> obj=new HashMap<>();
        Optional<Users> userObj=userRepo.findById(comment.getUserID());
        if(userObj.isPresent()) {
            Optional<Post> postObj=postRepo.findById(comment.getPostID());
            if(postObj.isPresent()) {
                commentRepo.save(comment);
                postObj.get().getCommentList().add(comment.getCommentID());
                return ("Comment created successfully");
            }
            else {
                obj.put("Error", "Post does not exist");
            }
        }
        else {
            obj.put("Error", "User does not exist");
        }
        return obj;
    }

    @GetMapping("/comment")
    public Object getComment(Integer commentID) {
        Optional<Comment> commentObj= commentRepo.findById(commentID);
        Map<String, Object> obj=new HashMap<>();
        if(commentObj.isPresent()) {
            obj.put("commentID",commentObj.get().getCommentID());
            obj.put("commentBody",commentObj.get().getCommentBody());
            Map<String,Object> map=new HashMap<>();
            map.put("userID",commentObj.get().getUserID());
            map.put("name",userRepo.findById(commentObj.get().getUserID()).get().getName());
            obj.put("commentCreator",map);
        }
        else{
            obj.put("Error", "Comment does not exist");
        }
        return obj;
    }

    @PatchMapping("/comment")
    public Object updateComment(@RequestBody Comment comment) {
        Optional<Comment> oldComment= commentRepo.findById(comment.getCommentID());
        if(oldComment.isPresent()) {
            Comment updatedComment=oldComment.get();
            updatedComment.setCommentBody(comment.getCommentBody());
            commentRepo.save(updatedComment);
            return ("Comment edited successfully");
        }
        Map<String, Object> obj=new HashMap<>();
        obj.put("Error","Comment does not exist");
        return obj;
    }

    @DeleteMapping("/comment")
    public Object deleteComment(Integer commentID) {
        Optional<Comment> commentObj= commentRepo.findById(commentID);
        if(commentObj.isPresent()) {
            commentRepo.delete(commentObj.get());
            return ("Comment deleted");
        }
        else{
            Map<String, Object> obj=new HashMap<>();
            obj.put("Error", "Comment does not exist");
            return obj;
        }
    }

}
