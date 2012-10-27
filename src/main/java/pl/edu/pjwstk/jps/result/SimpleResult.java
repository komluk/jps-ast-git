package pl.edu.pjwstk.jps.result;

import com.google.common.base.Objects.ToStringHelper;

import edu.pjwstk.jps.result.ISimpleResult;

public class SimpleResult<T> extends SingleResult implements ISimpleResult<T> {
	private final T value;
	
	public SimpleResult(T value) {
		this.value = value;
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	protected void toString(ToStringHelper helper) {
		helper.add("value", value);
	}

}
