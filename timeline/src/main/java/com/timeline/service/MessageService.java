package com.timeline.service;

import com.timeline.dataModel.MessageModel;
import com.timeline.dataModel.MessageTrans;
import com.timeline.dataObject.Message;
import com.timeline.error.BussinessException;

import java.util.List;

public interface MessageService {
    Message saveMessage(MessageTrans messageTrans) throws BussinessException;
    List<Message> getMessages(Integer times) throws BussinessException;
}
