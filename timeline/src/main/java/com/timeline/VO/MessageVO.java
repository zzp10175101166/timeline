package com.timeline.VO;

import java.time.LocalDateTime;

public class MessageVO {
    private Long messageId;
    private String title;
    private String content;
    private LocalDateTime time;
    private String senderName;
    private String receiverName;

    public MessageVO() {
    }

    public MessageVO(Long messageId, String title, String content, LocalDateTime time, String senderName, String receiverName) {
        this.messageId = messageId;
        this.title = title;
        this.content = content;
        this.time = time;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}
