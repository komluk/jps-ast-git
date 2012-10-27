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

}
