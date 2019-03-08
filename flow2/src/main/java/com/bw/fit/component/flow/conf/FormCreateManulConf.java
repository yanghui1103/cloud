package com.bw.fit.component.flow.conf;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class FormCreateManulConf {
    @Resource
    ConnectionFactory connectionFactory;

    /****
     * 这个是接收表单数据队列
     * @return
     */
    @Bean
    public Queue insertFormNotifyQueue() {
        return new Queue(RabbitMqConfig.dyncFormQueue,true);
    }

    /*****
     * 指定一个交换机
     * @return
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(RabbitMqConfig.cloudCommonExchange);
    }

    /****
     * 交换机与队列绑定，服务于插入动态表单
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(insertFormNotifyQueue()).to(defaultExchange()).with(RabbitMqConfig.formRoutingKey);
    }



}
