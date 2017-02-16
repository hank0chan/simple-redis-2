package concurrent.chings;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * @author 王工
 */
public class SlicingExecutor {

	public static Logger logger = LoggerFactory.getLogger(SlicingExecutor.class);
	public static Logger swiftLogger = LoggerFactory.getLogger("SWIFT");

	public static final int availableProcessors = Runtime.getRuntime().availableProcessors();

	ExecutorService excutor;

	public SlicingExecutor() {
		excutor = Executors.newFixedThreadPool(availableProcessors);
	}

	public void destroy() {
		excutor.shutdown();
	}

	public <IN, M, OUT> OUT execute(final SliceableTask<IN, M, OUT> task) {
		try {
			CompletionService<TaskSlice<IN, M>> completionService = new ExecutorCompletionService<TaskSlice<IN, M>>(excutor);
			int slices = 0, sliceFrom = 0;
			for(IN sliceInput : task) {
				final int index = slices;
				final int from = sliceFrom;
				final int to = from + (sliceInput instanceof Iterable ? Iterables.size((Iterable<?>)sliceInput) : 1);
				final IN input = sliceInput;
				completionService.submit(new Callable<TaskSlice<IN, M>>() {
					@Override
					public TaskSlice<IN, M> call() throws Exception {
						TaskSlice<IN, M> slice = new TaskSlice<IN, M>();
						slice.index = index;
						slice.from = from;
						slice.to = to;
						slice.input = input;
						slice.output = task.compute(input);
						return slice;
					}
				});
				slices++;
				sliceFrom = to;
			}
			for(int i = 0; i < slices; i++) {
				TaskSlice<IN, M> slice = completionService.take().get();
				synchronized(task) {
					task.gather(slice);
				}
			}
			return task.success();
		} catch(Exception x) {
			task.fail();
			throw new RuntimeException(x);
		}
	}

	public static <T> List<List<T>> slice(List<T> list) {
		return slice(list, availableProcessors > 1 ? availableProcessors - 1 : 1);
	}

	public static <T> List<List<T>> slice(List<T> list, int threads) {
		int silceLength = list.size() / threads;
		if(silceLength == 0) silceLength = 1;
		return Lists.partition(list, silceLength);
	}

}
