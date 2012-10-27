package pl.edu.pjwstk.jps.ast.datastore;

import edu.pjwstk.jps.datastore.IDoubleObject;
import edu.pjwstk.jps.datastore.OID;

public class DoubleObject extends SimpleObject<Double> implements IDoubleObject {

	public DoubleObject(String name, OID oid, Double value) {
		super(name, oid, value);
	}

}
