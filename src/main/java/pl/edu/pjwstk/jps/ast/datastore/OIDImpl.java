package pl.edu.pjwstk.jps.ast.datastore;

import com.google.common.base.Objects;

import edu.pjwstk.jps.datastore.OID;

public class OIDImpl implements OID {
	private static final long serialVersionUID = 1L;
	private final long oid;
	
	public OIDImpl(long oid) {
		this.oid = oid;
	}
	
	public long getOid() {
		return oid;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(oid);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(obj == this) {
			return true;
		}
		
		if(obj instanceof OIDImpl) {
			OIDImpl other = (OIDImpl) obj;
			return this.oid == other.oid;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return String.valueOf(oid);
	}
}
