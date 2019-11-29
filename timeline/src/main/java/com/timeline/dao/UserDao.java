package com.timeline.dao;

import com.timeline.dataObject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserInfo,Integer> {
    UserInfo findByUserId(Integer userId);
    UserInfo findByEmail(String email);
}
