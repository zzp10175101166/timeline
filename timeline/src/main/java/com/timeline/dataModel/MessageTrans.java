package com.timeline.dataModel;

public class MessageTrans {
    private Integer senderId;
    private Integer receiverId;
    private String title;
    private String content;

    public MessageTrans() {
    }
    //    public MessageTrans(String content,Integer senderId, Integer receiverId,String title ) {
//        this.senderId = senderId;
//        this.receiverId = receiverId;
//        this.title = title;
//        this.content = content;
//    }

    public MessageTrans(Integer senderId, Integer receiverId, String title, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.title = title;
        this.content = content;
    }

    public MessageTrans(Integer senderId, Integer receiverId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageTrans(String content) {
        this.content = content;
    }
}
