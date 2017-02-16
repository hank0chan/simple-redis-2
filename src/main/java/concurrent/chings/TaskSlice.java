package concurrent.chings;

/**
 * @author 王工
 */
public class TaskSlice<T, R> implements Comparable<TaskSlice<T, R>> {

	int index;
	int from, to;

	T input;
	R output;

	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public T getInput() {
		return input;
	}
	public void setInput(T input) {
		this.input = input;
	}
	public R getOutput() {
		return output;
	}
	public void setOutput(R output) {
		this.output = output;
	}
	@Override
	public int compareTo(TaskSlice<T, R> o) {
		return new Integer(index).compareTo(o.index);
	}
	@Override
	public String toString() {
		return "TaskSlice [index=" + index + ", from=" + from + ", to=" + to + ", input=" + input + ", output=" + output + "]";
	}

}
