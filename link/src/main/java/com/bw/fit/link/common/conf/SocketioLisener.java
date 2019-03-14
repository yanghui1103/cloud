package com.bw.fit.link.common.conf;

import com.bw.fit.link.common.util.Socketio;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @Description
 * @Author yangh
 * @Date 2019-3-14 11:28
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Configuration
@WebListener
public class SocketioLisener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //启动Socketio服务
        Socketio socketio = new Socketio();
        socketio.startServer();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //关闭Socketio服务
        Socketio socketio = new Socketio();
        socketio.stopSocketio();
    }

}
