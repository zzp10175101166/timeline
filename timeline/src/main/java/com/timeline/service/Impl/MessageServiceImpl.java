package com.timeline.service.Impl;

import com.timeline.dao.MessageDao;
import com.timeline.dataModel.MessageModel;
import com.timeline.dataModel.MessageTrans;
import com.timeline.dataObject.Message;
import com.timeline.error.BussinessException;
import com.timeline.error.EmBussinessError;
import com.timeline.service.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDao messageDao;

    @Override
    public Message saveMessage(MessageTrans messageTrans) throws BussinessException {
        if (messageTrans.getSenderId() == null || messageTrans.getReceiverId() == null || messageTrans.getTitle() == null || messageTrans.getContent() == null) {
            throw new BussinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR);
        }
        if (messageTrans.getContent().equals("") || messageTrans.getTitle().equals("")) {
            throw new BussinessException(EmBussinessError.MESSAGE_SEND_FAIL);
        }
        Message message = new Message();
        BeanUtils.copyProperties(messageTrans, message);
        LocalDateTime localDateTime = LocalDateTime.now();
        message.setTime(localDateTime);
        messageDao.save(message);

        return message;
    }

    @Override
    public List<Message> getMessages(Integer times) throws BussinessException {
        if (times == null) {
            throw new BussinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR);
        }
        Sort sort = new Sort(Sort.Direction.DESC, "messageId");
        Pageable pageable = new PageRequest(times, 5, sort);
        return messageDao.findAll(pageable).getContent();
    }
}
