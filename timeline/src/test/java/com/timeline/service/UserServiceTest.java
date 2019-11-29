package com.timeline.service;

import com.timeline.TimelineApplication;
import com.timeline.dataModel.UserModel;
import com.timeline.error.BussinessException;
import com.timeline.service.Impl.UserServiceImpl;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimelineApplication.class)
class UserServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;


    @Test
    void getUserModelByUserId() {
        UserModel userModel = userService.getUserModelByUserId(33);
        assertEquals(userModel.getEmail(), "1002376198@qq.com");
        userModel = userService.getUserModelByUserId(null);
        assertNull(userModel);
    }

    @Test
    void register() {
        assertThrows(BussinessException.class, () -> userService.register(null));

        UserModel userModel = new UserModel();
        userModel.setName("account");
        userModel.setGender((byte) 1);
        userModel.setEmail("1002376198@qq.com");
        assertThrows(BussinessException.class, () -> userService.register(userModel));
        String password = "123456";
        String encryptPassword = UserServiceImpl.getMD5(password);
        userModel.setEncryptPassword(encryptPassword);
        assertThrows(BussinessException.class,()->userService.register(userModel));
        try {
            userModel.setEmail("18717826762@qq.com");
            userService.register(userModel);
        } catch (BussinessException e) {
            logger.error(((BussinessException) e).getErrorMessage());
        }

    }

    @Test
    void validateLogin() {
        try {
            assertNull(userService.validateLogin(null, null));
        } catch (BussinessException e) {
            e.printStackTrace();
        }
        assertThrows(BussinessException.class, () -> userService.validateLogin("123", "123"));
        assertThrows(BussinessException.class, () -> userService.validateLogin("1002376198@qq.com", "12356"));
        try {
            UserModel userModel = userService.validateLogin("1002376198@qq.com", "123456");
            assertEquals("123", userModel.getName());
        } catch (BussinessException e) {
            System.out.println(e.getErrorMessage());
            e.printStackTrace();
        }
    }
}