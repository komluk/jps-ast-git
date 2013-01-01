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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SimpleResult that = (SimpleResult) o;

		if (value != null ? !value.equals(that.value) : that.value != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}
