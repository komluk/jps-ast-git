package pl.edu.pjwstk.jps.result;

import com.google.common.base.Objects.ToStringHelper;

import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBinderResult;

public class BinderResult extends SingleResult implements IBinderResult {
	private final String name;
	private final IAbstractQueryResult value;
	
	public BinderResult(String name, IAbstractQueryResult value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public IAbstractQueryResult getValue() {
		return value;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	protected void toString(ToStringHelper helper) {
		helper.add("name", name)
			.add("value", value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BinderResult that = (BinderResult) o;

		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (value != null ? !value.equals(that.value) : that.value != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}
}
