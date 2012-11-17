package pl.edu.pjwstk.jps.result;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

import edu.pjwstk.jps.result.IAbstractQueryResult;

public abstract class AbstractQueryResult implements IAbstractQueryResult {
	@Override
	public String toString() {
		ToStringHelper helper = Objects.toStringHelper(this);
		toString(helper);
		return helper.toString();
	}

	protected abstract void toString(ToStringHelper helper);
}
