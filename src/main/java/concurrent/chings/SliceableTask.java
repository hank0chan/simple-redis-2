package concurrent.chings;

/**
 * @author 王工
 */
public interface SliceableTask<IN, M, OUT> extends Iterable<IN> {

	public M compute(IN input);

	public void gather(TaskSlice<IN, M> slice);

	public OUT success();

	public void fail();

}
