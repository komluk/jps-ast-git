package pl.edu.pjwstk.jps.ast.datastore.util;

import java.util.List;
import java.util.Map;

import pl.edu.pjwstk.jps.ast.datastore.SBAStore;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.pjwstk.jps.datastore.IComplexObject;
import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.ISimpleObject;
import edu.pjwstk.jps.datastore.OID;

public class SBAStorePrinter {
	private static final String TAB = "   ";
	private final Map<OID, TmpObject> processed = Maps.newHashMap();
	private final TmpObject root = new TmpObject();
	private final SBAStore db;

	public SBAStorePrinter(SBAStore db) {
		this(db, (IComplexObject)db.retrieve(db.getEntryOID()));
	}

	public SBAStorePrinter(SBAStore db, IComplexObject rootObject) {
		this.db = db;
		
		root.setOid(rootObject.getOID().toString());
		root.setName(db.retrieve(rootObject.getOID()).getName());
		processed.put(rootObject.getOID(), root);

		for(OID oid : rootObject.getChildOIDs()) {
			process(root, db.retrieve(oid));
		}
	}
	
	private void process(TmpObject parent, ISBAObject object) {
		if(!processed.containsKey(object.getOID())) {
			TmpObject o = new TmpObject();
			o.setOid(object.getOID().toString());
			o.setName(object.getName());
			
			processed.put(object.getOID(), o);
			parent.getChildren().add(o);
			
			if(object instanceof IComplexObject) {
				IComplexObject complex = (IComplexObject) object;
				for(OID oid : complex.getChildOIDs()) {
					process(o, db.retrieve(oid));
				}
			} else if(object instanceof ISimpleObject) {
				ISimpleObject<?> simpleObject = (ISimpleObject<?>) object;
				o.setValue(simpleObject.getValue().toString());
			}
		} else {
			parent.getChildren().add(processed.get(object.getOID()));
		}
	}
	
	public String toXml() {
		StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<").append(root.getName()).append(" oid=\"").append(root.getOid()).append("\">\n");
		for(TmpObject o : root.getChildren()) {
			sb.append(toXml(o, TAB));
		}
		sb.append("</").append(root.getName()).append(">");
		return sb.toString();
	}
	
	private StringBuilder toXml(TmpObject o, String indent) {
		if(o.getValue() != null) {
			return new StringBuilder()
				.append(indent)
				.append("<").append(o.getName()).append(" oid=\"").append(o.getOid()).append("\">")
				.append(o.getValue())
				.append("</").append(o.getName()).append(">\n");
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(indent).append("<").append(o.getName()).append(" oid=\"").append(o.getOid().toString()).append("\">\n");
			for(TmpObject child : o.getChildren()) {
				sb.append(toXml(child, indent + TAB));
			}
			sb.append(indent)
				.append("</").append(o.getName()).append(">\n");
			return sb;
		}
	}

	private static class TmpObject {
		private String name;
		private String oid;
		private String value;
		private final List<TmpObject> children = Lists.newArrayList();
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getOid() {
			return oid;
		}
		public void setOid(String oid) {
			this.oid = oid;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public List<TmpObject> getChildren() {
			return children;
		}
	}
}
