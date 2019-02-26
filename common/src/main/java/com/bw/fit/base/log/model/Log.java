package com.bw.fit.base.log.model;

import com.bw.fit.base.common.model.BaseModel;
import com.bw.fit.base.inform.model.Inform;

/**
 * @Description 日志模型
 * @Author yangh
 * @Date 2019-2-25 15:09
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public class Log extends BaseModel {

    private String wantInform;//是否需要发送信息
    private Inform inform;    //如果需要发信息，都要加上该信息类的事务Id

    private String params; //请求参数
    private String result;  //返回结果
    private String logType; // 日志类型:来源数据字典
    private String operateFunction;
    private String resourceId;  // 被操作的目标资源fdid集合
    private String ip;
    private String url;     // 从request里取数据

    public String getWantInform() {
        return wantInform;
    }

    public void setWantInform(String wantInform) {
        this.wantInform = wantInform;
    }

    public Inform getInform() {
        return inform;
    }

    public void setInform(Inform inform) {
        this.inform = inform;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getOperateFunction() {
        return operateFunction;
    }

    public void setOperateFunction(String operateFunction) {
        this.operateFunction = operateFunction;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
