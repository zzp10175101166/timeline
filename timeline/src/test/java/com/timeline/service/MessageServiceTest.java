package com.timeline.service;

import com.timeline.TimelineApplication;
import com.timeline.dataModel.MessageTrans;
import com.timeline.dataObject.Message;
import com.timeline.error.BussinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimelineApplication.class)
class MessageServiceTest {
    @Autowired
    MessageService messageService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //没有使用参数化测试存储消息的功能
    @Test
    void saveMessage() {
        MessageTrans messageTrans = new MessageTrans(null, null, null);
        assertThrows(BussinessException.class, () -> messageService.saveMessage(messageTrans));
        messageTrans.setSenderId(33);
        messageTrans.setReceiverId(-1);
        messageTrans.setContent("");
        assertThrows(BussinessException.class, () -> messageService.saveMessage(messageTrans));
        messageTrans.setTitle("");
        assertThrows(BussinessException.class, () -> messageService.saveMessage(messageTrans));
        messageTrans.setContent("content");
        messageTrans.setTitle("title");
        try {
            Message message=messageService.saveMessage(messageTrans);
            Assertions.assertEquals(33,message.getSenderId().intValue());
        } catch (BussinessException e) {
            logger.error(e.getErrorMessage());
        }
    }

    //使用参数化测试存储消息的功能
    @ParameterizedTest(name = "The [{index}] saveMessageTest")
    @MethodSource(value = "messageTransProvider")
    void testSaveMessage(MessageTrans messageTrans){
        try {
            Message message=messageService.saveMessage(messageTrans);
            Assertions.assertEquals(33,message.getSenderId().intValue());
        } catch (BussinessException e) {
            logger.error(e.getErrorMessage());
        }
    }
    //数据提供源
    private static List<MessageTrans> messageTransProvider(){
        MessageTrans messageTrans = new MessageTrans(null, null, null,null);
        MessageTrans messageTrans1 = new MessageTrans(33, -1, "",null);
        MessageTrans messageTrans2 = new MessageTrans(33,-1,"","");
        MessageTrans messageTrans3 = new MessageTrans(33,-1,"title","content");
        return Arrays.asList(messageTrans,messageTrans1,messageTrans2,messageTrans3);
    }


    //没有使用参数化测试获取消息的功能
    @Test
    void getAllMessage() {
        List<Message> messages = null;
        try {
            messages = messageService.getMessages(1);
            for (Message m :
                    messages) {
                assertNotNull(m.getContent());
                System.out.println(m.getContent());
            }
        } catch (BussinessException e) {
            e.printStackTrace();
        }

    }
    //使用了参数化测试获取消息的功能
    @ParameterizedTest(name = "The [{index}] testGetMessages")
    @ValueSource(ints = {0,1,2,3})
    void testGetMessages(int times){
        List<Message> messages = null;
        try {
            messages = messageService.getMessages(times);
            for (Message m :
                    messages) {
                assertNotNull(m.getContent());
                System.out.println(m.getTime());
            }
        } catch (BussinessException e) {
            e.printStackTrace();
        }

    }
}