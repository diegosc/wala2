package com.dasac.wala.repositories;

import com.dasac.wala.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <UserEntity, Long> {

    //select * from  users WHERE  mail = :mail
    Optional<UserEntity> findByMail(String mail);
}
