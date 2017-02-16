package concurrent.disruptor.sample;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class LongEventMain {

	public static void main(String[] args) {
		Executor executor = Executors.newCachedThreadPool();
		LongEventFactory factory = new LongEventFactory();
		int bufferSize = 1024;
		Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, executor, ProducerType.MULTI, new BlockingWaitStrategy());
	}
}
