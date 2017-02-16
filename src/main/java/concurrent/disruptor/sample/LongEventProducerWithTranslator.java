package concurrent.disruptor.sample;

import java.nio.ByteBuffer;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * 版本3.x开始推荐使用这种方式
 * @author hankChan
 * @Email hankchan101@gmail.com
 * @time 09:11:40 - 16 Feb 2017
 * @detail
 */
public class LongEventProducerWithTranslator {

	private final RingBuffer<LongEvent> ringBuffer;
	
	public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}
	
	private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR = 
			new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
				@Override
				public void translateTo(LongEvent event, long sequence, ByteBuffer bb) {
					event.setValue(bb.getLong(0));
				}
			};
			
	public void onData(ByteBuffer bb) {
		ringBuffer.publishEvent(TRANSLATOR, bb);
	}
}
