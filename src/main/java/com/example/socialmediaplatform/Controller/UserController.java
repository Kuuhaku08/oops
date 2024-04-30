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
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    //Endpoints related to Users

    @PostMapping("/login")
    public Object login(@RequestBody Users user) {
        Optional<Users> userObj= Optional.ofNullable(userRepo.findByEmail(user.getEmail()));

        Map<String, Object> obj=new HashMap<>();

        if(userObj.isPresent()) {
            if(user.getPassword().equals(userObj.get().getPassword())) {
                return ("Login Successful");
            }

            obj.put("Error","Username/Password Incorrect");
        }
        else{
            obj.put("Error","User does not exist");
        }
        return obj;
    }

    @PostMapping("/signup")
    public Object signup(@RequestBody Users user) {
        try{
            userRepo.save(user);
            return ("Account Creation Successful");
        } catch(Exception e) {
            Map<String, Object> obj=new HashMap<>();
            obj.put("Error","Forbidden, Account already exists");
            return obj;
        }
    }

    @GetMapping("/user")
    public Object getUser(Integer userID) {
        Optional<Users> userObj= userRepo.findById(userID);
        Map<String, Object> obj=new HashMap<>();

        if(userObj.isPresent()) {
            obj.put("name",userObj.get().getName());
            obj.put("userID",userObj.get().getUserID());
            obj.put("email",userObj.get().getEmail());
        }
        else{
            obj.put("Error","User does not exist");
        }
        return obj;
    }

    @GetMapping("/users")
    public List<Map<String,Object>> getUsers() {
        List<Users> usersList=new ArrayList<>();
        userRepo.findAll().forEach(usersList::add);

        List<Map<String,Object>> list=new ArrayList<>();
        for (Users users : usersList) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("name", users.getName());
            map.put("userID", users.getUserID());
            map.put("email", users.getEmail());
            list.add(map);
        }

        return list;
    }

    @GetMapping("/")
    public List<Map<String,Object>> feed() {
        List<Post> postList=new ArrayList<>();
        postRepo.findAll().forEach(postList::add);
        postList.sort(Comparator.comparing(Post::getDate));
        Collections.reverse(postList);

        List<Map<String,Object>> list=new ArrayList<>();
        for (Post post : postList) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("postID", post.getPostID());
            map.put("postBody", post.getPostBody());
            map.put("date", post.getDate());

            Set<Object> comments=new HashSet<>();

            for(Integer comment:post.getCommentList()){

                Map<String, Object> commentD=new LinkedHashMap<>();
                commentD.put("commentID",comment);
                commentD.put("commentBody",commentRepo.findById(comment).get().getCommentBody());

                Map<String, Object> userD=new LinkedHashMap<>();
                userD.put("userID",commentRepo.findById(comment).get().getUserID());
                userD.put("name",userRepo.findById(commentRepo.findById(comment).get().getUserID()).get().getName());

                commentD.put("commentCreator",userD);

                comments.add(commentD);

            }
            map.put("comments",comments);
            list.add(map);
        }

        return list;
    }
}