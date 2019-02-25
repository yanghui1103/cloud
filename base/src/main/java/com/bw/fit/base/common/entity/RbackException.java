package com.bw.fit.base.common.entity;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-25 17:24
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public class RbackException  extends Exception {
    /****
     * 事务回滚使用到的异常类
     */
    private String res ;
    private String msg ;
    public RbackException(String res,String msg){
        this.res = res;
        this.msg = msg;
    }
    public String getRes() {
        return res;
    }
    public void setRes(String res) {
        this.res = res;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }


}
