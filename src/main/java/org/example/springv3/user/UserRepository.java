package org.example.springv3.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.username=:username")
    Optional<User> findByUsername(@Param("username") String username);
    //User 말고 Optional<User> 로 받아주고 서비스에서도 Optional<User>로 해서 바꿔준다

    @Query("select u from User u where u.username=:username and u.password=:password")
    Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}

