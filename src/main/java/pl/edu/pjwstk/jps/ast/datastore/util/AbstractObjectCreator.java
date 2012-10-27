package pl.edu.pjwstk.jps.ast.datastore.util;

import pl.edu.pjwstk.jps.ast.datastore.BooleanObject;
import pl.edu.pjwstk.jps.ast.datastore.DoubleObject;
import pl.edu.pjwstk.jps.ast.datastore.IntegerObject;
import pl.edu.pjwstk.jps.ast.datastore.StringObject;
import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.ISimpleObject;

public abstract class AbstractObjectCreator {
	public abstract ISBAObject getObject();
	
	protected ISimpleObject<?> parseSimpleObject(String fieldName, Object object) {
		String str = object.toString();
		
		try {
			int i = Integer.parseInt(str);
			return new IntegerObject(fieldName, OIDGenerator.generatorOID(), i);
		} catch (NumberFormatException e) {
			//NIC
		}
		
		try {
			double val = Double.parseDouble(str);
			return new DoubleObject(fieldName, OIDGenerator.generatorOID(), val);
		} catch (NumberFormatException e) {
			//NIC
		}
		
		try {
			if("true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str)) {
				boolean b = Boolean.parseBoolean(str);
				return new BooleanObject(fieldName, OIDGenerator.generatorOID(), b);
			}
		} catch (NumberFormatException e) {
			//NIC
		}
		
		return new StringObject(fieldName, OIDGenerator.generatorOID(), str);
	}
}
