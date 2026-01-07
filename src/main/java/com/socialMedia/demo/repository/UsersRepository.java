package com.socialMedia.demo.repository;

import com.socialMedia.demo.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    Boolean existsByEmail(String email);

//    @Query("SELECT u FROM Users u WHERE u.username like %:username%")
//    Page<Users> findByUsernameCustom(@Param("username") String username, Pageable pageable);
}
