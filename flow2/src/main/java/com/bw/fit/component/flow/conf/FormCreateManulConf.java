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



    @Bean
    public SimpleMessageListenerContainer messageContainer() {
        //加载处理消息A的队列
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        //设置接收多个队列里面的消息，这里设置接收队列A
        //假如想一个消费者处理多个队列里面的信息可以如下设置：
        //container.setQueues(queueA(),queueB(),queueC());
        container.setQueues(insertFormNotifyQueue());
        container.setExposeListenerChannel(true);
        //设置最大的并发的消费者数量
        container.setMaxConcurrentConsumers(10);
        //最小的并发消费者的数量
        container.setConcurrentConsumers(1);
        //设置确认模式手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                /**通过basic.qos方法设置prefetch_count=1，这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message，
                 换句话说,在接收到该Consumer的ack前,它不会将新的Message分发给它 */
                channel.basicQos(1);
                byte[] body = message.getBody();
                System.out.println("接收处理队列A当中的消息:" + new String(body));
                /**为了保证永远不会丢失消息，RabbitMQ支持消息应答机制。
                 当消费者接收到消息并完成任务后会往RabbitMQ服务器发送一条确认的命令，然后RabbitMQ才会将消息删除。*/
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        });
        return container;
    }

}
