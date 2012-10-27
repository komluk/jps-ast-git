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

}
