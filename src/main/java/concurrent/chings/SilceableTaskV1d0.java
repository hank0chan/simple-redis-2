package concurrent.chings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 王工
 */
public abstract class SilceableTaskV1d0<IN, M, OUT> implements SliceableTask<IN, M, OUT> {

	protected List<TaskSlice<IN, M>> slices;

	@Override
	public void gather(TaskSlice<IN, M> slice) {
		if(slices == null) {
			slices = new ArrayList<TaskSlice<IN, M>>();
		}
		slices.add(slice);
		Collections.sort(slices);
	}

	@Override
	public void fail() {}

}
