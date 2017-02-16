package concurrent.disruptor.sample;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;

@Deprecated
public class LongEventProducer {

	private final RingBuffer<LongEvent> ringBuffer;
	
	public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}
	
	public void onData(ByteBuffer bb) {
		long sequence = ringBuffer.next();
		try {
			LongEvent event = ringBuffer.get(sequence);
			event.setValue(bb.getLong(0));
		} finally {
			ringBuffer.publish(sequence);
		}
	}
}
