package pl.edu.pjwstk.jps.ast.datastore.util;

import java.util.concurrent.atomic.AtomicLong;

import pl.edu.pjwstk.jps.ast.datastore.OIDImpl;

import edu.pjwstk.jps.datastore.OID;

public final class OIDGenerator {
	private final static AtomicLong oidIterator = new AtomicLong(0);
	
	private OIDGenerator() {}
	
	public static OID generatorOID() {
		return new OIDImpl(oidIterator.incrementAndGet());
	}
}
