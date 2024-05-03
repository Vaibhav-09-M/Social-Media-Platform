package com.management.twitter.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Comment1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int commentId;
    public String commentBody;

//    public Post getPost() {
//        return post;
//    }
//
//    public void setPost(Post post) {
//        this.post = post;
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "postId", nullable = false)
//    public Post post;

//    public void setUsername(String username) {
//        this.username = username;
//    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    @JsonBackReference
    private Post post;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userID")
    @ManyToOne(fetch = FetchType.EAGER) // Eagerly fetch the associated user
    @JoinColumn(name = "user_id")
    private User1 commentCreator;  // Ensure this is linked to User1

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Comment1() {
        this.createdAt = new Date();
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

//    public String username;

    public int getCommentId() {
        return commentId;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public User1 getCommentCreator() {
        return commentCreator;
    }

    public void setCommentCreator(User1 commentCreator) {
        this.commentCreator = commentCreator;
    }

    public Post getPost() {
        if (this.post == null) {
            throw new RuntimeException("Post does not exist for this comment");
        }
        return this.post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
