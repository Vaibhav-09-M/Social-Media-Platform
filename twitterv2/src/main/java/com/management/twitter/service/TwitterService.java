package com.management.twitter.service;

import com.management.twitter.dao.CommentRepository;
import com.management.twitter.dao.PostRepository;
import com.management.twitter.dao.UserRepository;
import com.management.twitter.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TwitterService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public PostRepository postRepository;
    @Autowired
    public CommentRepository commentRepository;

    public String saveUser(User1 user) {
        User1 existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null) {
            userRepository.save(user);
            return "Account creation successful";
        } else {
            return "Forbidden, Account already exists";
        }
    }

    public String loginUser(User1 user) {
        User1 existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null) {
            return "User does not exist";
        } else if (!Objects.equals(existingUser.getPassword(), user.getPassword())) {
            return "Username/Password incorrect";
        } else {
            return "Login successful";
        }
    }

//    public String createPost(Post post) {
////        postRepository.save(post);
////        return "Post created successfully";
//
//        // Check if the user exists
//        Optional<User1> userOptional = userRepository.findById(post.getUserID());
//        if (userOptional.isEmpty()) {
//            return "User does not exist"; // Return this message if the user isn't found
//        }
//        // If the user exists, save the post
//        postRepository.save(post);
//        return "Post created successfully";
//    }

    public ResponseEntity<?> createPost(PostRequest postRequest) {
        Optional<User1> userOptional = userRepository.findById(postRequest.getUserID());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist");
        }

        User1 user = userOptional.get();

        Post post = new Post();
        post.setPostBody(postRequest.getPostBody());
        post.setUser(user); // Associate the post with the user
        post.setCreatedAt(new Date()); // Set current date and time

        postRepository.save(post);
        return ResponseEntity.ok("Post created successfully");
    }

//    public Post getPost(int postID) {
////        Post post = postRepository.findById(postID);
////        if (post == null) {
////            throw new RuntimeException("Post does not exist");
////        }
////        return post;
//
//        Optional<Post> postOptional = Optional.ofNullable(postRepository.findById(postID));
//        return postOptional.orElse(null);
//    }


    public ResponseEntity<?> getPost(int postID) {
        Optional<Post> postOptional = Optional.ofNullable(postRepository.findById(postID));
        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            // Fetch the comments associated with the post
            List<Comment1> comments = post.getComments();

            // Create a DTO to hold post details along with comment details
            Map<String, Object> postDetails = new HashMap<>();
            postDetails.put("postID", post.getPostId());
            postDetails.put("postBody", post.getPostBody());
            postDetails.put("date", post.getCreatedAt());
            postDetails.put("comments", comments);

            // Add user details to the post details
            User1 user = post.getUser();
            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("userID", user.getId());
            userDetails.put("name", user.getName());
            postDetails.put("commentCreator", userDetails);

            return ResponseEntity.ok(postDetails);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
        }
    }

//    public ResponseEntity<?> getPost(int postID) {
//        Optional<Post> postOptional = Optional.ofNullable(postRepository.findById(postID));
//        if (postOptional.isPresent()) {
//            return ResponseEntity.ok(postOptional.get());
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
//        }
//    }

    public String updatePost(int postID, String postBody) {
//        Post post = postRepository.findById(postID);
//        if (post == null) {
//            throw new RuntimeException("Post does not exist");
//        }
//        post.setPostBody(postBody);
//        postRepository.save(post);
//        return "Post edited successfully";

        Optional<Post> postOptional = Optional.ofNullable(postRepository.findById(postID));
        if (postOptional.isEmpty()) {
            return "Post does not exist";
        }
        Post post = postOptional.get();
        post.setPostBody(postBody);
        postRepository.save(post);
        return "Post edited successfully";
    }

    public String deletePost(int postID) {
        // Check if the post exists
        if (!postRepository.existsById(postID)) {
            return "Post does not exist";  // Return this message if the post isn't found
        }
        // If it exists, delete it
        postRepository.deleteById(postID);
        return "Post deleted";
    }

    public ResponseEntity<?> createComment(CommentRequest commentRequest) {
        // Fetch the post using postID
        Optional<Post> postOptional = Optional.ofNullable(postRepository.findById(commentRequest.getPostID()));
        if (postOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post does not exist");
        }
        Post post = postOptional.get();

        // Fetch the user using userID
        Optional<User1> userOptional = userRepository.findById(commentRequest.getUserID());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist");
        }
        User1 user = userOptional.get();

        // Create a new comment
        Comment1 comment = new Comment1();
        comment.setCommentBody(commentRequest.getCommentBody());
        comment.setPost(post);
        comment.setCommentCreator(user);
        comment.setCreatedAt(new Date());

        // Save the comment
        commentRepository.save(comment);
        return ResponseEntity.ok("Comment created successfully");
    }

//    public String createComment(Comment1 comment) {
//        commentRepository.save(comment);
//        return "Comment created successfully";
//    }

//    public Comment1 getComment(int commentID) {
//        Optional<Comment1> comment = Optional.ofNullable(commentRepository.findById(commentID));
//        return comment.orElseThrow(() -> new RuntimeException("Comment does not exist"));
//    }

    public ResponseEntity<?> getComment(int commentID) {
        Optional<Comment1> commentOptional = Optional.ofNullable(commentRepository.findById(commentID));
        if (commentOptional.isPresent()) {
            Comment1 comment = commentOptional.get();
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
        }
    }

//    public ResponseEntity<?> getComment(int commentID) {
//        Optional<Comment1> commentOptional = Optional.ofNullable(commentRepository.findById(commentID));
//        if (commentOptional.isPresent()) {
//            return ResponseEntity.ok(commentOptional.get());
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
//        }
//    }

    public String updateComment(int commentID, String commentBody) {
//        Comment1 comment = commentRepository.findById(commentID);
//        if (comment == null) {
//            throw new RuntimeException("Comment does not exist");
//        }
//        comment.setCommentBody(commentBody);
//        commentRepository.save(comment);
//        return "Comment edited successfully";

        Optional<Comment1> commentOptional = Optional.ofNullable(commentRepository.findById(commentID));
        if (commentOptional.isEmpty()) {
            return "Comment does not exist";
        }
        Comment1 comment = commentOptional.get();
        comment.setCommentBody(commentBody);
        commentRepository.save(comment);
        return "Comment edited successfully";
    }

    public String deleteComment(int commentID) {
        // Check if the comment exists
        if (!commentRepository.existsById(commentID)) {
            return "Comment does not exist";  // Return this message if the comment isn't found
        }
        // If it exists, delete it
        commentRepository.deleteById(commentID);
        return "Comment deleted";
    }

    public User1 getUserDetails(int userID) {
        Optional<User1> user = userRepository.findById(userID);
        return user.orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    public List<Post> getUserFeed() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<User1> getAllUsers() {
        return (List<User1>) userRepository.findAll();
    }
}