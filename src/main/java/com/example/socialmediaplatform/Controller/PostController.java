package com.example.socialmediaplatform.Controller;

import com.example.socialmediaplatform.Entity.Comment;
import com.example.socialmediaplatform.Entity.Post;
import com.example.socialmediaplatform.Entity.Users;
import com.example.socialmediaplatform.Repo.CommentRepo;
import com.example.socialmediaplatform.Repo.PostRepo;
import com.example.socialmediaplatform.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping
public class PostController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    //Endpoints related to Posts

    @PostMapping("/post")
    public Object post(@RequestBody Post post) {
        Optional<Users> userObj= userRepo.findById(post.getUserID());
        if(userObj.isPresent()) {
            postRepo.save(post);
            return ("Post created successfully");
        }
        else{
            Map<String, Object> obj=new LinkedHashMap<>();
            obj.put("Error","User does not exist");
            return obj;
        }
    }

    @GetMapping("/post")
    public Object getPost(@RequestParam int postID) {
        Optional<Post> postObj= postRepo.findById(postID);

        Map<String, Object> obj=new LinkedHashMap<>();

        if(postObj.isPresent()) {
            obj.put("postID",postObj.get().getPostID());
            obj.put("postBody",postObj.get().getPostBody());
            obj.put("date",postObj.get().getDate());

            Set<Object> comments=new LinkedHashSet<>();

            for(Integer comment:postObj.get().getCommentList()){

                Map<String, Object> commentD=new LinkedHashMap<>();
                commentD.put("commentID",comment);
                commentD.put("commentBody",commentRepo.findById(comment).get().getCommentBody());

                Map<String, Object> userD=new LinkedHashMap<>();
                userD.put("userID",commentRepo.findById(comment).get().getUserID());
                userD.put("name",userRepo.findById(commentRepo.findById(comment).get().getUserID()).get().getName());

                commentD.put("commentCreator",userD);

                comments.add(commentD);

            }

            obj.put("comments",comments);
        }
        else{
            obj.put("Error","Post does not exist");
        }
        return obj;
    }

    @PatchMapping("/post")
    public Object updatePost(@RequestBody Post post) {
        Optional<Post> oldPost= postRepo.findById(post.getPostID());
        if(oldPost.isPresent()) {
            Post updatedPost=oldPost.get();
            updatedPost.setPostBody(post.getPostBody());

            postRepo.save(updatedPost);
            return ("Post edited successfully");
        }
        Map<String, Object> obj=new HashMap<>();
        obj.put("Error","Post does not exist");
        return obj;
    }

    @DeleteMapping("/post")
    public Object deletePost(Integer postID) {
        Optional<Post> postObj= postRepo.findById(postID);
        if(postObj.isPresent()) {
            postRepo.deleteById(postID);
            return ("Post deleted");
        }
        Map<String, Object> obj=new HashMap<>();
        obj.put("Error","Post does not exist");
        return obj;
    }
}