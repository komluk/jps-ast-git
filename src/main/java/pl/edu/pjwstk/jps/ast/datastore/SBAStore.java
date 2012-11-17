package pl.edu.pjwstk.jps.ast.datastore;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pjwstk.jps.ast.datastore.util.JavaObjectCreator;
import pl.edu.pjwstk.jps.ast.datastore.util.OIDGenerator;
import pl.edu.pjwstk.jps.ast.datastore.util.SBAStorePrinter;
import pl.edu.pjwstk.jps.ast.datastore.util.XmlObjectGenerator;

import com.google.common.collect.Maps;

import edu.pjwstk.jps.datastore.IComplexObject;
import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.datastore.OID;

public class SBAStore implements ISBAStore {
	private static final Logger log = LoggerFactory.getLogger(SBAStore.class);
	
	private static final SBAStore instance = new SBAStore();
	
	private final Map<OID, ISBAObject> db = Maps.newHashMap();
	private final IComplexObject root = new ComplexObject("root", OIDGenerator.generatorOID());
	
	private SBAStore() {
		db.put(root.getOID(), root);
	}
	
	@Override
	public ISBAObject retrieve(OID oid) {
		return db.get(oid);
	}

	@Override
	public OID getEntryOID() {
		return root.getOID();
	}

	@Override
	public void loadXML(String filePath) {
		try {
			for(ISBAObject object : new XmlObjectGenerator(new File(filePath)).getObjects()) {
				if(!db.containsKey(object.getOID())) {
					put(object);
				}
				
				if(!root.getChildOIDs().contains(object.getOID())) {
					root.getChildOIDs().add(object.getOID());
				}
			}
		} catch (Exception e) {
			log.warn("Unable to read data from file", e);
		}
	}

	@Override
	public OID generateUniqueOID() {
		return OIDGenerator.generatorOID();
	}

	@Override
	public void addJavaObject(Object o, String objectName) {
		ISBAObject sbaObject = new JavaObjectCreator(objectName, o).getObject();
		put(sbaObject);
	}

	@Override
	public void addJavaCollection(@SuppressWarnings("rawtypes") Collection o, String collectionName) {
		for(Object collectionElement : o) {
			addJavaObject(collectionElement, collectionName);
		}
	}
	
	public void put(ISBAObject object) {
		putInternal(object);
		root.getChildOIDs().add(object.getOID());
	}
	
	public void putInternal(ISBAObject object) {
		db.put(object.getOID(), object);
	}
	
	@Override
	public String toString() {
		return new SBAStorePrinter(this).toXml();
	}
	
	public Map<OID, ISBAObject> getDB() {
		return Collections.unmodifiableMap(db);
	}

	public static SBAStore getInstance() {
		return instance;
	}
}
