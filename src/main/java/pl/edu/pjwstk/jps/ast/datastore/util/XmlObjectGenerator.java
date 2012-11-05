package pl.edu.pjwstk.jps.ast.datastore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pjwstk.jps.ast.datastore.ComplexObject;
import pl.edu.pjwstk.jps.ast.datastore.SBAStore;
import edu.pjwstk.jps.datastore.IComplexObject;
import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.ISimpleObject;

public class XmlObjectGenerator extends AbstractObjectCreator {
	private static final Logger log = LoggerFactory.getLogger(XmlObjectGenerator.class);
	
	private static final SAXBuilder saxBuilder = new SAXBuilder();
	
	private final InputStream inputStream;

	public XmlObjectGenerator(InputStream in) {
		inputStream = in;
	}
	public XmlObjectGenerator(File inputFile) throws FileNotFoundException {
		this(new FileInputStream(inputFile));
	}
	
	@Override
	public ISBAObject getObject() {
		IComplexObject rootObject = null;
		
		try {
			Document doc = saxBuilder.build(inputStream);
			Element rootElement = doc.getRootElement();
			rootObject = new ComplexObject(rootElement.getName(), OIDGenerator.generatorOID());
			if(!rootElement.getChildren().isEmpty()) {
				for(Element child : rootElement.getChildren()) {
					parseElement(rootObject, child);
				}
			}
		} catch (Exception e) {
			log.error("Unable to read data from stream", e);
		}
		
		return rootObject;
	}
	
	private void parseElement(IComplexObject rootObject, Element element) {
		if(element.hasAttributes()) {
			for(Attribute attr : element.getAttributes()) {
				ISimpleObject<?> simpleObject = parseSimpleObject(attr.getName(), attr.getValue());
				rootObject.getChildOIDs().add(simpleObject.getOID());
				log.debug("Detected {} from attribute {} with value {}",
						new Object[]{simpleObject, attr.getName(), attr.getValue() });
				SBAStore.getInstance().putInternal(simpleObject);
			}
		}
		
		if(element.getChildren().isEmpty()) {
			ISimpleObject<?> simpleObject = parseSimpleObject(element.getName(), element.getText());
			log.debug("Detected {} from <{}>{}</{}>",
					new Object[] { simpleObject, element.getName(),
						element.getText(), element.getName()});
			SBAStore.getInstance().putInternal(simpleObject);
			rootObject.getChildOIDs().add(simpleObject.getOID());
		} else {
			IComplexObject complexObject = new ComplexObject(element.getName(), OIDGenerator.generatorOID());
			log.debug("Complex object created from {}", element.getName());
			rootObject.getChildOIDs().add(complexObject.getOID());
			for(Element child : element.getChildren()) {
				parseElement(complexObject, child);
			}
			SBAStore.getInstance().putInternal(complexObject);
		}
	}
}
