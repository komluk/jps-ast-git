package pl.edu.pjwstk.jps.ast.datastore;

import edu.pjwstk.jps.datastore.IIntegerObject;
import edu.pjwstk.jps.datastore.OID;

public class IntegerObject extends SimpleObject<Integer> implements IIntegerObject {

	public IntegerObject(String name, OID oid, Integer value) {
		super(name, oid, value);
	}

}
