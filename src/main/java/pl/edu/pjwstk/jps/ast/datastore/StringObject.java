package pl.edu.pjwstk.jps.ast.datastore;

import edu.pjwstk.jps.datastore.IStringObject;
import edu.pjwstk.jps.datastore.OID;

public class StringObject extends SimpleObject<String> implements IStringObject {

	public StringObject(String name, OID oid, String value) {
		super(name, oid, value);
	}

}
