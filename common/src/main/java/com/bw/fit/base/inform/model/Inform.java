package com.bw.fit.base.inform.model;

import com.bw.fit.base.common.model.BaseModel;

import javax.validation.constraints.NotEmpty;

/**
 * @Description 信息通知模型，通知给其他主体，可能是短信，微信，公众号，
 * 邮件，站内信(待阅)
 * @Author yangh
 * @Date 2019-2-25 15:19
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public class Inform extends BaseModel {
    @NotEmpty(message = "主题不得为空")
    private String title;
    @NotEmpty(message = "信息内容不得为空")
    private String content;
    @NotEmpty(message = "内容类型不得为空")
    private String contentType; //数据字典：文本，url
    @NotEmpty(message = "接收者不得为空")
    private String receiver;
    private String sender;
    private String affair; // 事务 例如INSTORE:ID号
    @NotEmpty(message = "发送方式不得为空")
    private String way; //方式：短信，邮件，站内信
    private String isRead;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getAffair() {
        return affair;
    }

    public void setAffair(String affair) {
        this.affair = affair;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
