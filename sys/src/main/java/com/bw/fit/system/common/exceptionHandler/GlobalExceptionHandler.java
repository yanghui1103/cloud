package com.bw.fit.system.common.exceptionHandler;

import javax.servlet.http.HttpServletRequest;

import com.bw.fit.system.common.interceptor.SessionCorrectInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

import com.alibaba.fastjson.*;
import com.bw.fit.system.common.model.RbackException;

/*****
 * 系统统一异常拦截机制
 * @author yangh
 *
 */
@Component
@ControllerAdvice 
public class GlobalExceptionHandler {

		private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
		@ExceptionHandler(value = Exception.class)  
	    @ResponseBody  
	    public JSONObject dealExp(HttpServletRequest req, Exception e) throws Exception {  
			JSONObject obj = new JSONObject();  
			e.printStackTrace();
	        obj.put("res", "1");  
	        obj.put("msg", "拦截层返回异常,请与系统管理员联系");
			logger.info("ExceptionHandler:"+e.getMessage());
	        return obj;  
	    }
		
		@ExceptionHandler(value = RbackException.class)  
	    @ResponseBody  
	    public JSONObject dealRbackExp(HttpServletRequest req, RbackException e) throws Exception {  
	        return makeErrorObj("拦截层返回异常:", req, e);  
	    }  
		
		
		/** 
	     * 构造错误信息 
	     * @param msg 错误描述 
	     * @param e   异常信息 
	     * @return 
	     */  
	    private JSONObject makeErrorObj(String msg, HttpServletRequest req, RbackException e) {  
	        JSONObject obj = new JSONObject();  
	        obj.put("res", "1");  
	        obj.put("msg", msg + " (" + e.getMsg() + ")");   
	        return obj;  
	    }


}
