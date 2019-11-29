package com.timeline.service.Impl;

import com.timeline.dao.UserDao;
import com.timeline.dao.UserLoginDao;
import com.timeline.dataModel.UserModel;
import com.timeline.dataObject.UserInfo;
import com.timeline.dataObject.UserLogin;
import com.timeline.error.BussinessException;
import com.timeline.error.EmBussinessError;
import com.timeline.service.UserService;
import com.timeline.validator.ValidationResult;
import com.timeline.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class UserServiceImpl implements UserService {
    private static String salt = "dhfka468538*^*^(;jl";
    @Autowired
    private UserDao userDao;
    @Autowired
    ValidatorImpl validator;
    @Autowired
    UserLoginDao userLoginDao;


    public static String getMD5(String s) {
        String base = s + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    public UserModel getUserModelByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }
        UserInfo userInfo = userDao.findByUserId(userId);
        UserLogin userLogin = userLoginDao.findByUserId(userId);
        UserModel userModel = getUserModel(userInfo, userLogin);
        return userModel;
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BussinessException {
        if (userModel == null) {
            throw new BussinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR);
        }
        ValidationResult result = validator.validate(userModel);
        if (result.isHasError()) {
            throw new BussinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR, result.getErrorMessage());
        }
        UserInfo userInfo = getUserInfoFromUserModel(userModel);
        if(userDao.findByEmail(userInfo.getEmail())!=null){
            throw new BussinessException(EmBussinessError.USER_REGISTER_FAIL);
        }
        userDao.save(userInfo);
        userModel.setUserId(userInfo.getUserId());
        UserLogin userLogin = getUserLoginFromUserModel(userModel);
        userLoginDao.save(userLogin);


    }

    @Override
    public UserModel validateLogin(String email, String password) throws BussinessException {
        if (email == null || password == null) {
            throw new BussinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR);
        }
        UserInfo userInfo = userDao.findByEmail(email);
        if (userInfo == null) {
            throw new BussinessException(EmBussinessError.USER_NOT_EXIST);
        }
        UserModel userModel = new UserModel();
        UserLogin userLogin = userLoginDao.findByUserId(userInfo.getUserId());
        String encryptPassword = UserServiceImpl.getMD5(password);
        if (encryptPassword.equals(userLogin.getEncryptPassword())) {
            userModel = getUserModel(userInfo, userLogin);
        } else {
            throw new BussinessException(EmBussinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    private UserInfo getUserInfoFromUserModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userModel, userInfo);
        return userInfo;
    }

    private UserLogin getUserLoginFromUserModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserLogin userLogin = new UserLogin();
        BeanUtils.copyProperties(userModel, userLogin);
        return userLogin;
    }

    private UserModel getUserModel(UserInfo userInfo, UserLogin userLogin) {
        if (userInfo == null) {
            return null;
        }
        if (userLogin == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userInfo, userModel);
        userModel.setEncryptPassword(userLogin.getEncryptPassword());
        return userModel;
    }
}
