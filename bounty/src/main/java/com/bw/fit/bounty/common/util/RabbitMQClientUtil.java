package com.bw.fit.bounty.common.util;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang.SerializationUtils;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Queue;
import com.rabbitmq.client.QueueingConsumer;
import org.omg.CORBA.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class RabbitMQClientUtil {
    private  static Logger log = LoggerFactory.getLogger(RabbitMQClientUtil.class);
    private static RabbitMQClientUtil rabbitMQClientUtil;
    private Vector properties=null;
    private static ConnectionFactory rabbitConnFactory=null;
    private static Connection rabbitConn=null;
    private static Channel rabbitChannel=null;


    public RabbitMQClientUtil(){
        rabbitConnFactory=new ConnectionFactory();
        initMQConfig();//初始化设置rabbitmq配置
    }
    private Vector getProperties(){//提供出口[影子实例]
        return properties;
    }
    private static synchronized void initRabbitMQUtil(){
        if(rabbitMQClientUtil==null){
            rabbitMQClientUtil=new RabbitMQClientUtil();
        }
    }

    /**
     * 【单利入口】
     * @return
     */
    public static RabbitMQClientUtil getInstance(){
        if(rabbitMQClientUtil==null){
            initRabbitMQUtil();
        }
        return rabbitMQClientUtil;
    }

    /**
     * 【Rabbitmq连接配置】
     */
    protected static void initMQConfig(){
        String rabbitIp=PropertiesUtil.getValueByKey("RABBIT_MQ_IP");
        int rabbitPort=Integer.parseInt(PropertiesUtil.getValueByKey(("RABBIT_MQ_PORT")));
        String rabbitUserName= PropertiesUtil.getValueByKey(("RABBIT_USERNAME"));
        String rabbitPassword=PropertiesUtil.getValueByKey(("RABBIT_PASSWORD"));
        log.info("初始化RabbitMQ配置文件信息..........");
        log.info("RABBIT_MQ_IP："+rabbitIp);
        log.info("RABBIT_MQ_PORT："+rabbitUserName);
        log.info("RABBIT_USERNAME："+rabbitUserName);
        log.info("RABBIT_PASSWORD："+rabbitPassword);
        rabbitConnFactory.setHost(rabbitIp);
        rabbitConnFactory.setPort(rabbitPort);
        rabbitConnFactory.setUsername(rabbitUserName);
        rabbitConnFactory.setPassword(rabbitPassword);
        rabbitConnFactory.setAutomaticRecoveryEnabled(true);
        rabbitConnFactory.setRequestedHeartbeat(5);
        rabbitConnFactory.setNetworkRecoveryInterval(6000);
        log.info("初始化完成："+rabbitConnFactory);
    }


    public void updateProperties(){
        RabbitMQClientUtil rabbitmqUtil=new RabbitMQClientUtil();
        properties=rabbitmqUtil.getProperties();
    }

/************************单利结束****************************/

/************************【发送者】**********************************/
    /**
     *
     * send1【均衡发送给消费者】【特点：每个消费者收到的消息是相同的】
     * @param queueName 队列名称
     * @param object 发送对象
     * @param priority 优先级【优先级,最大10,最小为0,数值越大优先级越高】
     */
    public static boolean sendMsg(String queueName,Serializable object,int priority){
        try{
            rabbitConn=rabbitConnFactory.newConnection();
            rabbitChannel=rabbitConn.createChannel();
            BasicProperties.Builder properties = new BasicProperties.Builder();//优先级
            properties.priority(priority);//设置优先级
            rabbitChannel.exchangeDeclare(queueName, "direct", true, false, null);
            rabbitChannel.queueDeclare(queueName, true, false, false, null);
            rabbitChannel.basicPublish("",queueName,properties.build(), SerializationUtils.serialize(object));
            return true;
        }catch(Exception e){
            e.printStackTrace();
            log.info("均衡发送消息错误："+e.getMessage(),e);
        }
        return false;
    }




/**********************【消费者】********************************/

    //public static BlockingQueue<Entry> LISTENER_ENTRY_QUEUE_2 =new LinkedBlockingQueue<Entry>();
    /**
     * 【批量消费】
     * @param queueName
     * @param blockingQueue
     * @param limit
     * @return
     */
    public BlockingQueue<Object> consumeQueue(String queueName,BlockingQueue<Object> blockingQueue,int limit){
        try{
            if(blockingQueue==null){
                blockingQueue=new LinkedBlockingQueue<Object>();
            }
            rabbitConn=rabbitConnFactory.newConnection();
            rabbitChannel=rabbitConn.createChannel();
            Map<String, Object> priority = new HashMap<String, Object>();
            priority.put("x-max-priority", 10);
            rabbitChannel.queueDeclare(queueName, true, false, false, priority);
            rabbitChannel.basicQos(1);//同一时间取1条
            QueueingConsumer consumer=new QueueingConsumer(rabbitChannel);
            rabbitChannel.basicConsume(queueName, false, consumer);
            for (int i = 0; i < limit; i++){
                QueueingConsumer.Delivery delivery=consumer.nextDelivery();
                Object object=SerializationUtils.deserialize(delivery.getBody());
                if(object!=null){
                    rabbitChannel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
                blockingQueue.put(object);
            }
            return blockingQueue;
        }catch(Exception e){
            log.info("批量消费错误："+e.getMessage(),e);
        }
        return null;
    }



    /**
     * 【批量消费】
     * @param queueName
     * @param limit
     * @return
     */
    public List<Object> consumeQueue(String queueName,List<Object> listObject,int limit){
        try{
            if(listObject==null){
                listObject=new ArrayList<Object>();
            }
            rabbitConn=rabbitConnFactory.newConnection();
            rabbitChannel=rabbitConn.createChannel();
            Map<String, Object> priority = new HashMap<String, Object>();
            priority.put("x-max-priority", 10);
            rabbitChannel.queueDeclare(queueName, true, false, false, priority);
            rabbitChannel.basicQos(1);//同一时间取1条
            QueueingConsumer consumer=new QueueingConsumer(rabbitChannel);
            rabbitChannel.basicConsume(queueName, false, consumer);
            for (int i = 0; i < limit; i++){
                QueueingConsumer.Delivery delivery=consumer.nextDelivery();
                Object object=SerializationUtils.deserialize(delivery.getBody());
                if(object!=null){
                    rabbitChannel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
                listObject.add(object);
            }
            return listObject;
        }catch(Exception e){
            log.info("批量消费错误："+e.getMessage(),e);
        }
        return null;
    }


    /**
     * 【批量消费】
     * @param queueName
     * @return
     */
    public Object consumeQueue(String queueName){
        try{
            rabbitConn=rabbitConnFactory.newConnection();
            rabbitChannel=rabbitConn.createChannel();
            Map<String, Object> priority = new HashMap<String, Object>();
            priority.put("x-max-priority", 10);
//			rabbitChannel.queueDeclare(queueName, true, false, false, priority);
            rabbitChannel.basicQos(1);//同一时间取1条
            QueueingConsumer consumer=new QueueingConsumer(rabbitChannel);
            rabbitChannel.basicConsume(queueName, false, consumer);
            while(true){
                QueueingConsumer.Delivery delivery=consumer.nextDelivery();
                Object object=SerializationUtils.deserialize(delivery.getBody());
                if(object!=null){
                    rabbitChannel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
                return object;
            }

        }catch(Exception e){
            e.printStackTrace();
            log.info("批量消费错误："+e.getMessage(),e);
        }
        return null;
    }


    /**
     * 【获取队列内容数量】
     * @return true=正在使用，false=未被使用
     */
    public int deleteQueue(String queueName){
        try{
            rabbitConn=rabbitConnFactory.newConnection();
            rabbitChannel=rabbitConn.createChannel();
            Queue.DeleteOk deleteOk=rabbitChannel.queueDelete(queueName);
            if(deleteOk!=null){
                int msgCount=deleteOk.getMessageCount();
                return msgCount;
            }
            return 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 【清空队列数量】
     */
    public boolean clearQueueData(String queueName){
        try{
            rabbitConn=rabbitConnFactory.newConnection();
            rabbitChannel=rabbitConn.createChannel();
            Queue.PurgeOk purgeOK=rabbitChannel.queuePurge(queueName);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public static void close(){
        try{
            rabbitChannel.close();
            rabbitConn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        RabbitMQClientUtil rabbitUtil=RabbitMQClientUtil.getInstance();
        String queueName="CHANNEL_1";
//		Product product=new Product();
//				product.setId(1);
//				product.setPname("产品1");
//				product.setPrice("产品价格");
//		rabbitUtil.sendMsg(queueName, product, 10);
//
        //System.out.print(((Product)rabbitUtil.consumeQueue(queueName)).getId());
//		rabbitUtil.deleteQueue(queueName);
        rabbitUtil.close();

    }

}
