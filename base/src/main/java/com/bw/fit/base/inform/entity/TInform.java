package com.bw.fit.base.inform.entity;

import com.bw.fit.base.common.entity.BaseEntity;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-25 17:23
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public class TInform extends BaseEntity {

    private String content;
    private  String contentType;
    private  String title;
    private String sender;
    private String receiver;
    private String isRead;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
