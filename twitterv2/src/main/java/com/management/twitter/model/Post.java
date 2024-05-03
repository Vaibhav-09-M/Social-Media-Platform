package com.management.twitter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int postId;
    public String postBody;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment1> comments;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userID")  // This is the foreign key column in the Post table.
    @ManyToOne(fetch = FetchType.EAGER) // Eagerly fetch the associated user
    @JoinColumn(name = "userID")
    private User1 user;

    public Post() {
        this.createdAt = new Date();  // Set current date and time at creation
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getPostId() {
        return postId;
    }

    public String getPostBody() {
        return postBody;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public List<Comment1> getComments() {
        return comments;
    }

    public void setComments(List<Comment1> comments) {
        this.comments = comments;
    }

    public User1 getUser() {
        return user;
    }

    public void setUser(User1 user) {
        this.user = user;
    }

}
