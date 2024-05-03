package com.management.twitter.dao;

import com.management.twitter.model.Comment1;
import com.management.twitter.model.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment1, Integer> {
    @EntityGraph(attributePaths = "commentCreator")
    public Comment1 findById(int id);
}
