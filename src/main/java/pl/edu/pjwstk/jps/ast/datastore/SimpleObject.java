package pl.edu.pjwstk.jps.ast.datastore;

import com.google.common.base.Objects.ToStringHelper;

import edu.pjwstk.jps.datastore.ISimpleObject;
import edu.pjwstk.jps.datastore.OID;

public abstract class SimpleObject<T> extends SBAObject implements ISimpleObject<T>{
	private final T value;
	
	public SimpleObject(String name, OID oid, T value) {
		super(name, oid);
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
