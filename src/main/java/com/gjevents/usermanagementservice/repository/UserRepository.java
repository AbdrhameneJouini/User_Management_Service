package com.gjevents.usermanagementservice.repository;

import com.gjevents.usermanagementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {




    User findByLogin(String login);
    User findByEmail(String email);

    User findUserById(Long id);
    User findUserByLogin(String login);


    boolean existsUserByEmail(String email);





}
