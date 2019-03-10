package com.bw.fit.common.cache.util;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


/*****
 * 这个方法暂时为了测试lettuce式支持多线程，线程安全，且支持同步与异步，性能很爆棚
 * 具体使用需要注入式的，需要改造为读写俩方法
 */
public class LettuceSingleRedisClient {

    public static void main(String[] args) throws Exception {
        RedisURI redisUri = RedisURI.Builder.redis("10.1.20.87")
                .withPort(6379)
                .withPassword("123456")
                .withDatabase(0)
                .build();

        RedisClient client = RedisClient.create(redisUri);

        GenericObjectPool<StatefulRedisConnection<String, String>> pool = ConnectionPoolSupport
                .createGenericObjectPool(() -> client.connect(), new GenericObjectPoolConfig());

// executing work
        try (StatefulRedisConnection<String, String> connection = pool.borrowObject()) {

            RedisCommands<String, String> commands = connection.sync(); //connection.async()异步
            commands.multi();
            commands.set("key5", "value5");


            commands.exec();


            String key = commands.get("key5");
            System.out.println(key);
        }

// terminating
        pool.close();
        client.shutdown();

    }
}
