package com.bw.fit.base.log.entity;

import com.bw.fit.base.common.entity.BaseEntity;

/**
 * @Description 日志收集实体
 * @Author yangh
 * @Date 2019-2-25 11:35
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public class TLog extends BaseEntity {
    private String params; //请求参数
    private String result;  //返回结果
    private String logType ; // 日志类型
    private String operateFunction ;
    private String resourceId ;  // 被操作的目标资源fdid集合
    private String ip;
    private String url;     // 从request里取数据

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
