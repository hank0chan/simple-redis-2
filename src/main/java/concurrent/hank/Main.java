package concurrent.hank;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

	public static void main(String[] args) {
		RequestTaskQueue queue = new RequestTaskQueue();
		for(int i = 0; i < 16; i++) {
			RequestTask task = new RequestTask(i);
			queue.enterQueue(task);
		}
		ExecutorService executor = Executors.newCachedThreadPool();
		CompletionService<Object> completionService = new ExecutorCompletionService<>(executor);
		final int TOTAL_TASK_NUM = 10; 
		try {
			for(int i = 0; i < TOTAL_TASK_NUM; i++) {
				// 提交任务
				completionService.submit(queue.outerQueue());
			}
			// 检查任务执行结果
			for(int i = 0; i < TOTAL_TASK_NUM; i++) {
				Future<Object> future = completionService.take();
				System.out.println(future.get());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
		}
	}
}
