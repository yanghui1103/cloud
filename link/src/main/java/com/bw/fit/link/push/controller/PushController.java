package com.bw.fit.link.push.controller;

import com.bw.fit.link.common.util.Socketio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author yangh
 * @Date 2019-3-14 11:31
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@RestController
@RequestMapping("push")
public class PushController {

    /**
     * 测试报警推送服务:主要应用一个方法pushArr
     */
    @GetMapping("/message")
    public void pushMessage(){
        Socketio socketio = new Socketio();
        //这里发送的消息内容可以结合具体场景自定义对象
        socketio.pushArr("connect_msg", "今天下午2点开会");
    }
}
