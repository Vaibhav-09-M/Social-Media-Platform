package com.management.twitter.controller;

import com.management.twitter.model.Comment1;
import com.management.twitter.model.Post;
import com.management.twitter.model.PostRequest;
import com.management.twitter.model.User1;
import com.management.twitter.model.CommentRequest;
import com.management.twitter.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Home {
    @Autowired
    public TwitterService twitterService;

    @PostMapping("/signup")
    public String register(@RequestBody User1 user){
        return twitterService.saveUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User1 user){
        return twitterService.loginUser(user);
    }

    // Updated to use correct endpoint and method names
//    @PostMapping("/post")
//    public String createPost(@RequestBody Post post){
//        return twitterService.createPost(post);
//    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest){
        return twitterService.createPost(postRequest);
    }

//    @GetMapping("/post")
//    public Post getPost(@RequestParam int postID){
//        return twitterService.getPost(postID);
//    }

    @GetMapping("/post")
    public ResponseEntity<?> getPost(@RequestParam int postID){
        return twitterService.getPost(postID);
    }

    @PatchMapping("/post")
    public String updatePost(@RequestParam int postID, @RequestBody String postBody){
        return twitterService.updatePost(postID, postBody);
    }

    @DeleteMapping("/post")
    public String deletePost(@RequestParam int postID){
        return twitterService.deletePost(postID);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@RequestBody CommentRequest commentRequest){
        return twitterService.createComment(commentRequest);
    }

//    @GetMapping("/comment")
//    public Comment1 getComment(@RequestParam int commentID){
//        return twitterService.getComment(commentID);
//    }

    @GetMapping("/comment")
    public ResponseEntity<?> getComment(@RequestParam int commentID){
        return twitterService.getComment(commentID);
    }

//    @GetMapping("/comment")
//    public ResponseEntity<?> getComment(@RequestParam int commentID){
//        return twitterService.getComment(commentID);
//    }

    @PatchMapping("/comment")
    public String updateComment(@RequestParam int commentID, @RequestBody String commentBody){
        return twitterService.updateComment(commentID, commentBody);
    }

    @DeleteMapping("/comment")
    public String deleteComment(@RequestParam int commentID){
        return twitterService.deleteComment(commentID);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@RequestParam int userID) {
        try {
            User1 userDetails = twitterService.getUserDetails(userID);
            return ResponseEntity.ok(userDetails);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/")
    public List<Post> getUserFeed() {
        return twitterService.getUserFeed();
    }

    @GetMapping("/users")
    public List<User1> getAllUsers(){
        return twitterService.getAllUsers();
    }

}
