package com.timeline.controller;

import com.timeline.VO.MessageVO;
import com.timeline.VO.UserVo;
import com.timeline.dao.UserDao;
import com.timeline.dataModel.MessageTrans;
import com.timeline.dataObject.Message;
import com.timeline.dataObject.UserInfo;
import com.timeline.error.BussinessException;
import com.timeline.response.CommonReturnType;
import com.timeline.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class WebSocketServer {
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    MessageService messageService;
    @Autowired
    UserDao userDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @MessageMapping("/sendTopic")
    @SendTo("/topic/getResponse")
    public CommonReturnType sendTopic(MessageTrans messageTrans, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
        logger.info("接收到了信息" + messageTrans.getContent() + "来自于" + messageTrans.getSenderId());
        Message message = new Message();
        try {
            message = messageService.saveMessage(messageTrans);
        } catch (BussinessException e) {
            logger.error(e.getErrorMessage());
        }
        MessageVO messageVO = getMessageVOFromMessage(message);
        return CommonReturnType.create(messageVO);
    }

    /**
     * 服务端接收一对一响应
     */
    @MessageMapping("/sendUser")
    //@SendToUser(value = "/queue/getResponse")
    public MessageTrans sendUser(MessageTrans messageTrans, StompHeaderAccessor stompHeaderAccesso, Principal principal) {
        stompHeaderAccesso.getSessionAttributes();
        logger.info("接收到了信息" + messageTrans.getContent());
        this.template.convertAndSendToUser(messageTrans.getReceiverId().toString(), "/queue/getResponse", messageTrans);
        return new MessageTrans("一对一服务 响应");
    }

    /**
     * 一对一订阅通知
     */
    @SubscribeMapping("/user/{userId}/queue/getResponse")
    public MessageTrans subOnUser(@DestinationVariable String userId, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
        stompHeaderAccessor.setUser(new Principal() {
            @Override
            public String getName() {
                return userId;
            }
        });
        logger.info(userId + "/queue/getResponse 已订阅");
        return new MessageTrans("感谢你订阅了 一对一服务");
    }

    /**
     * 一对多订阅通知
     */
    @SubscribeMapping("/topic/getResponse")
    public CommonReturnType subOnTopic(Principal principal) {
        logger.info("/topic/getResponse 已订阅");
        return CommonReturnType.create(null);
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


