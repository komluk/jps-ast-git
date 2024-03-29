package pl.edu.pjwstk.jps.result;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.Lists;

import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStructResult;

public class StructResult extends SingleResult implements IStructResult {
	private final List<ISingleResult> elements = Lists.newArrayList();
	
	public StructResult(Collection<? extends ISingleResult> results) {
		elements.addAll(results);
	}
	
	public StructResult(ISingleResult ... results) {
		elements.addAll(Arrays.asList(results));
	}
	
	public StructResult() {}
	
	@Override
	public List<ISingleResult> elements() {
		return elements;
	}

	@Override
	protected void toString(ToStringHelper helper) {
		helper.add("elements", elements);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StructResult that = (StructResult) o;

		if (elements != null ? !elements.equals(that.elements) : that.elements != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return elements != null ? elements.hashCode() : 0;
	}
}
