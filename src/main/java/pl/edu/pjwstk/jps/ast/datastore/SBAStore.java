package pl.edu.pjwstk.jps.ast.datastore;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pjwstk.jps.ast.datastore.util.JavaObjectCreator;
import pl.edu.pjwstk.jps.ast.datastore.util.OIDGenerator;
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
			ISBAObject sbaObject = new XmlObjectGenerator(new File(filePath)).getObject();
			root.getChildOIDs().add(sbaObject.getOID());
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
		root.getChildOIDs().add(sbaObject.getOID());
	}

	@Override
	public void addJavaCollection(@SuppressWarnings("rawtypes") Collection o, String collectionName) {
		for(Object collectionElement : o) {
			addJavaObject(collectionElement, collectionName);
		}
	}
	
	public void put(ISBAObject object) {
		db.put(object.getOID(), object);
		root.getChildOIDs().add(object.getOID());
	}

	public static SBAStore getInstance() {
		return instance;
	}
}
