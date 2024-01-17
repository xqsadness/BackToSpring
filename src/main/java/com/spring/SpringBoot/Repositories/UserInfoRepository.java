package com.spring.SpringBoot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.SpringBoot.Models.UserInfo;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByName(String username);

}