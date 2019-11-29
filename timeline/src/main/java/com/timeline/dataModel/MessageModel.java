package com.timeline.dataModel;

import java.time.Duration;
import java.time.LocalDateTime;

public class MessageModel {
    private Long messageId;
    private String content;
    private LocalDateTime time;
    private Integer senderId;
    private Integer receiverId;
    private Duration duration;

    public MessageModel() {
    }

    public MessageModel(String content) {
        this.content = content;
    }

    public MessageModel(String content, Integer senderId, Integer receiverId) {
        this.content = content;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public Duration getDuration() {
        LocalDateTime now = LocalDateTime.now();
        this.duration = Duration.between(now, this.time);
        return this.duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
