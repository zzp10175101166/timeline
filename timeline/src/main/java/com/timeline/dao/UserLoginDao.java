package com.timeline.dao;

import com.timeline.dataObject.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginDao extends JpaRepository<UserLogin,Integer> {
    UserLogin findByUserId(Integer userId);
}
