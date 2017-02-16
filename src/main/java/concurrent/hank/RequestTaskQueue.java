package concurrent.hank;

import java.util.Stack;

/**
 * 请求队列
 * @author hankChan
 * @Email hankchan101@gmail.com
 * @time 10:15:28 - 16 Feb 2017
 * @detail
 */
public class RequestTaskQueue {

	private Stack<RequestTask> queue;
	/** 队列大小 */
	private final static int QUEUE_SIZE = 32;
	
	public RequestTaskQueue() {
		queue = new Stack<>();
	}
	
	/**
	 * 进入队列
	 * @param request
	 */
	public synchronized void enterQueue(RequestTask request) {
		while(queue.size() > QUEUE_SIZE) {
			try {
				wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		queue.push(request);
		notifyAll();
	}
	
	/**
	 * 离开队列
	 * @return
	 */
	public synchronized RequestTask outerQueue() {
		RequestTask requestTask;
		while(queue.empty()) {
			try {
				wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		requestTask = (RequestTask) queue.pop();
		notifyAll();
		return requestTask;
	}
}
