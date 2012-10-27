package pl.edu.pjwstk.jps.ast.datastore;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.OID;

public abstract class SBAObject implements ISBAObject {
	private final String name;
	private final OID oid;

	public SBAObject(String name, OID oid) {
		this.name = name;
		this.oid = oid;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public OID getOID() {
		return oid;
	}
	
	@Override
	public String toString() {
		ToStringHelper helper = Objects.toStringHelper(this);
		helper.add("oid", oid)
			.add("name", name);
		toString(helper);
		return helper.toString();
	}

	protected abstract void toString(ToStringHelper helper);
}
