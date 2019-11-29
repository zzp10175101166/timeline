package com.timeline.service;

import com.timeline.dataModel.UserModel;
import com.timeline.error.BussinessException;
import org.springframework.stereotype.Service;


public interface UserService {
    UserModel getUserModelByUserId(Integer userId);
    void register(UserModel userModel) throws BussinessException;
    UserModel validateLogin(String email,String password) throws BussinessException;

}
