package pl.edu.pjwstk.jps.result;

import com.google.common.base.Objects.ToStringHelper;

import edu.pjwstk.jps.datastore.OID;
import edu.pjwstk.jps.result.IReferenceResult;

public class ReferenceResult extends SingleResult implements IReferenceResult {
	public final OID oid;

	public ReferenceResult(OID oid) {
		this.oid = oid;
	}

	@Override
	public OID getOIDValue() {
		return oid;
	}

	@Override
	protected void toString(ToStringHelper helper) {
		helper.add("oid", oid);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ReferenceResult that = (ReferenceResult) o;

		if (oid != null ? !oid.equals(that.oid) : that.oid != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return oid != null ? oid.hashCode() : 0;
	}
}
