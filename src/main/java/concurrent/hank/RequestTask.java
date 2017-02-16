package concurrent.hank;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

import cache.util.TimeUtils;

/**
 * 请求任务
 * @author hankChan
 * @Email hankchan101@gmail.com
 * @time 09:55:01 - 16 Feb 2017
 * @detail
 */
public class RequestTask implements Callable<Object> {
	
	/**
	 * 访问Redis的键
	 */
	private Object key;

	public RequestTask(Object key) {
		this.key = key;
	}
	
	@Override
	public Object call() throws Exception {
		// TODO 根据key值执行请求
		System.out.println("任务执行完成: " + key);
		return key + ": " + TimeUtils.YYYYMMDDHHMMSS.format(new Date());
	}

}
