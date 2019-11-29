package com.timeline.controller;

import com.timeline.VO.MessageVO;
import com.timeline.VO.UserVo;
import com.timeline.dao.UserDao;
import com.timeline.dataModel.UserModel;
import com.timeline.dataObject.Message;
import com.timeline.dataObject.UserInfo;
import com.timeline.error.BussinessException;
import com.timeline.error.EmBussinessError;
import com.timeline.response.CommonReturnType;
import com.timeline.service.Impl.UserServiceImpl;
import com.timeline.service.MessageService;
import com.timeline.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController {
    @Autowired
    UserDao userDao;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @Autowired
    HttpServletRequest httpServletRequest;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public CommonReturnType login(@RequestBody Map<String, Object> params) throws BussinessException {
        String email = (String) params.get("email");
        String password = (String) params.get("password");
        UserModel userModel = userService.validateLogin(email, password);
        httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);
        logger.info(userModel.getName() + "登录了timeline系统");
        return CommonReturnType.create(userModel);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/logout")
    public CommonReturnType logout() {
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        logger.info(userModel.getName() + "退出了timeline系统");
        httpServletRequest.getSession().invalidate();
        return CommonReturnType.create(null);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public CommonReturnType register(@RequestBody Map<String, Object> params) throws BussinessException {
        String username = (String) params.get("username");
        String email = (String) params.get("email");
        String password = (String) params.get("password");
        UserModel userModel = new UserModel();
        userModel.setEmail(email);
        userModel.setName(username);
        userModel.setEncryptPassword(UserServiceImpl.getMD5(password));
        userModel.setGender((byte) 0);
        userService.register(userModel);
        logger.info(userModel.getEmail() + "注册了timeline");
        return CommonReturnType.create(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/messages/{times}")
    public CommonReturnType getAllMessage(@PathVariable("times") Integer times) throws BussinessException {
        List<Message> messages = messageService.getMessages(times);
        if (messages == null || messages.size() == 0) {
            return CommonReturnType.create(null);
        }
        Stream<Message> messageStream = messages.stream();
        Stream<MessageVO> messageVOStream = messageStream.map(this::getMessageVOFromMessage);
        List<MessageVO> messageVOList;
        try {
            messageVOList = messageVOStream.collect(Collectors.toList());
        } catch (Exception e) {
            throw new BussinessException(EmBussinessError.UNKNOWN_ERROR);
        }
        ;
        return CommonReturnType.create(messageVOList);
    }

    private MessageVO getMessageVOFromMessage(Message message) {
        MessageVO messageVO = new MessageVO();
        BeanUtils.copyProperties(message, messageVO);
        messageVO.setReceiverName("所有人");
        UserInfo userInfo = userDao.findByUserId(message.getSenderId());
        messageVO.setSenderName(userInfo.getName());
        return messageVO;
    }

}
