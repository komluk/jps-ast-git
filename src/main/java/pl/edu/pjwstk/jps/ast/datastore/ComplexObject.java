package pl.edu.pjwstk.jps.ast.datastore;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.Lists;

import edu.pjwstk.jps.datastore.IComplexObject;
import edu.pjwstk.jps.datastore.OID;

public class ComplexObject extends SBAObject implements IComplexObject {
	private final List<OID> oids = Lists.newArrayList();
	
	public ComplexObject(String name, OID oid) {
		this(name, oid, new OID[] {});
	}
	
	public ComplexObject(String name, OID oid, OID ... oids) {
		super(name, oid);
		for(OID o : oids) {
			this.oids.add(o);
		}
	}
	
	public ComplexObject(String name, OID oid, Collection<OID> oids) {
		super(name, oid);
		for(OID o : oids) {
			this.oids.add(o);
		}
	}
	
	@Override
	public List<OID> getChildOIDs() {
		return oids;
	}

	@Override
	protected void toString(ToStringHelper helper) {
		helper.add("oids", oids);
	}
}
