package com.management.twitter.dao;

import com.management.twitter.model.User1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User1, Integer> {
    public User1 findByEmail(String email);
//    public User getUserByName(String username);
}
