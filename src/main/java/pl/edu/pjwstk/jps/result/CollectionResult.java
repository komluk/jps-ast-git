package pl.edu.pjwstk.jps.result;

import java.util.Collection;

import com.google.common.base.Objects.ToStringHelper;

import edu.pjwstk.jps.result.ICollectionResult;
import edu.pjwstk.jps.result.ISingleResult;

public abstract class CollectionResult<T extends Collection<ISingleResult>> extends AbstractQueryResult implements ICollectionResult {
	private final T elements;
	
	public CollectionResult() {
		elements = initCollection();
	}
	
	protected abstract T initCollection();

	public T getElements() {
		return elements;
	}
	
	@Override
	protected void toString(ToStringHelper helper) {
		helper.add("elements", elements);
	}
}
