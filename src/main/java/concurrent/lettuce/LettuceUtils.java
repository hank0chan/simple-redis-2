package concurrent.lettuce;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;

import cache.util.PojoRefSpringBeanUtil;
import cache.util.SerializableUtils;

@Component
public class LettuceUtils {

	/**
	 * 使用普通Java类引用SpringBeans的方式，屏蔽注解代码
	 */
//	@SuppressWarnings("unchecked")
//	private static ObjectFactory<StatefulRedisConnection<byte[], byte[]>> 
//		statefulRedisConnectionFactory = (ObjectFactory<StatefulRedisConnection<byte[], byte[]>>) 
//		PojoRefSpringBeanUtil.getBean(StatefulRedisConnection.class);
	
	private static ObjectFactory<StatefulRedisConnection<byte[], byte[]>> statefulRedisConnectionFactory;
	@Autowired
	public void setStatefulRedisConnectionFactory(
			ObjectFactory<StatefulRedisConnection<byte[], byte[]>> statefulRedisConnectionFactory) {
		LettuceUtils.statefulRedisConnectionFactory = statefulRedisConnectionFactory;
	}
	
	public static StatefulRedisConnection<byte[], byte[]> getConnection() {
		return statefulRedisConnectionFactory.getObject();
	}

	/**
	 * 加入缓存
	 * @param key 缓存键
	 * @param value 缓存值
	 */
	@Deprecated
	public static <T extends Serializable> String put(Object key, T value) {
		StatefulRedisConnection<byte[], byte[]> connection = null;
		RedisAsyncCommands<byte[], byte[]> syncCommands = null;
		RedisFuture<String> response = null;
		try {
			connection = getConnection();
			syncCommands = connection.async();
			response = syncCommands.set(SerializableUtils.serialize(key), 
					SerializableUtils.serialize(value));
			return response.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return "Failure To Put Cache!";
	}
	
	/**
	 * 加入缓存，并定义缓存时效
	 * @param key 缓存键
	 * @param value 缓存值
	 * @param expireTime 缓存时效，单位：秒
	 */
	public static <T extends Serializable> String put(Object key, T value, long expireTime) {
		StatefulRedisConnection<byte[], byte[]> connection = null;
		RedisAsyncCommands<byte[], byte[]> syncCommands = null;
		RedisFuture<String> response = null;
		try {
			connection = getConnection();
			syncCommands = connection.async();
			response = syncCommands.setex(SerializableUtils.serialize(key), 
					expireTime, SerializableUtils.serialize(value));
			return response.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return "Failure To Put Cache!";
	}
	
	/**
	 * 获取缓存
	 * @param key 缓存键
	 * @return
	 */
	public static <T> T get(Object key) {
		StatefulRedisConnection<byte[], byte[]> connection = null;
		RedisAsyncCommands<byte[], byte[]> syncCommands = null;
		RedisFuture<byte[]> data = null;
		try {
			connection = getConnection();
			syncCommands = connection.async();
			data = syncCommands.get(SerializableUtils.serialize(key));
			return SerializableUtils.unserialize(data.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 移除所有缓存
	 */
	public static void removeAll() {
		StatefulRedisConnection<byte[], byte[]> connection = null;
		RedisAsyncCommands<byte[], byte[]> syncCommands = null;
		try {
			connection = getConnection();
			syncCommands = connection.async();
			syncCommands.flushall();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
