package com.management.twitter.dao;

import com.management.twitter.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
    public Post findById(int id);
//    List<Post> findAllByOrderByDateDesc();
      @Query("SELECT p FROM Post p LEFT JOIN FETCH p.user LEFT JOIN FETCH p.comments c LEFT JOIN FETCH c.commentCreator ORDER BY p.createdAt DESC")
      List<Post> findAllByOrderByCreatedAtDesc();
}
