package pl.edu.pjwstk.jps.ast.datastore;

import edu.pjwstk.jps.datastore.IBooleanObject;
import edu.pjwstk.jps.datastore.OID;

public class BooleanObject extends SimpleObject<Boolean> implements IBooleanObject {

	public BooleanObject(String name, OID oid, Boolean value) {
		super(name, oid, value);
	}

}
