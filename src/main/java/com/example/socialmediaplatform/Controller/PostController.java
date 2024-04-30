package com.example.socialmediaplatform.Controller;

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
            return ("Post created successful");
        }
        else{
            Map<String, Object> obj=new HashMap<>();
            obj.put("Error","User does not exist");
            return obj;
        }
    }

    @GetMapping("/post")
    public Object getPost(Integer postID) {
        Optional<Post> postObj= postRepo.findById(postID);
        Map<String, Object> obj=new HashMap<>();
        if(postObj.isPresent()) {
            obj.put("postID",postID);
            obj.put("postBody",postObj.get().getPostBody());
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
