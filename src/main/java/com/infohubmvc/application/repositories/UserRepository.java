package com.infohubmvc.application.repositories;

import com.infohubmvc.application.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByFirstNameAndLastName(String firstName, String lastName);

    //Optional<User> getUserByUsername(String username);

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);


}
