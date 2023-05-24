package com.sam.whatsapp.Models;

public class MessagesModel {

    String uId, message, messageId;
    Long timestamp;

    public MessagesModel(String uId,String message, Long timestamp) {
        this.message = message;
        this.uId = uId;
        this.timestamp = timestamp;
    }

    public MessagesModel(String uId, String message){
        this.uId = uId;
        this.message = message;
    }
    public MessagesModel(){}

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}

