package pl.edu.pjwstk.jps.ast.datastore.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pjwstk.jps.ast.datastore.ComplexObject;
import pl.edu.pjwstk.jps.ast.datastore.SBAStore;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import edu.pjwstk.jps.datastore.IComplexObject;
import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.ISimpleObject;

public class JavaObjectCreator extends AbstractObjectCreator {
	private static final Logger log = LoggerFactory.getLogger(JavaObjectCreator.class);
	
	private final Object object;
	private String name;

	public JavaObjectCreator(String name, Object object) {
		Preconditions.checkNotNull(name, "Name can not be null");
		Preconditions.checkNotNull(object, "Object can not be null");
		Preconditions.checkArgument(!object.getClass().isArray(), "Can not operate on collections");
		Preconditions.checkArgument(!Iterable.class.isAssignableFrom(object.getClass()));
		
		this.name = name;
		this.object = object;
	}

	@Override
	public ISBAObject getObject() {
		Class<?> clazz = object.getClass();
		if(isPrimitiveObject(object)) {
			log.debug("Object " + object + " is primitive");
			return parseSimpleObject(clazz.getSimpleName(), object);
		}
		
		IComplexObject complexObject = new ComplexObject(name, OIDGenerator.generatorOID());
		for(Field field : clazz.getFields()) {
			Object value = null;
			try {
				value = field.get(object);
			} catch (Exception e) {
				log.warn("Unable to get field value", e);
				continue;
			}
			
			if(value == null) {
				log.debug("Field {} is null. Skipping.", field.getName());
				continue;
			}
			
			if(isPrimitiveObject(value)) {
				log.debug("field [{}] is primitive. Class [{}], value [{}]", new Object[] { field.getName(), value.getClass(), value });
				ISimpleObject<?> simpleObject = parseSimpleObject(name, value);
				SBAStore.getInstance().put(complexObject);
				complexObject.getChildOIDs().add(simpleObject.getOID());
			} else if (isCollectionObject(value)) {
				log.debug("field [{}] is iterable of class {}", field.getName(), value.getClass());
				Iterable<?> iterable = (Iterable<?>) value;
				for(Object o : iterable) {
					ISBAObject IterableElement = new JavaObjectCreator(name, o).getObject();
					SBAStore.getInstance().put(IterableElement);
					complexObject.getChildOIDs().add(IterableElement.getOID());
				}
			} else if(isArrayObject(field)) {
				Collection<Object> collection = castToCollection(field.getName(), value);
				log.debug("field [{}] is array of class {}", field.getName(), value.getClass());
				for(Object o : collection) {
					ISBAObject arrayElement = new JavaObjectCreator(name, o).getObject();
					SBAStore.getInstance().put(arrayElement);
					complexObject.getChildOIDs().add(arrayElement.getOID());
				}
			} else {
				log.debug("field [{}] is complex inner object of class {}", field.getName(), value.getClass());
				ISBAObject innerComplexObject = new JavaObjectCreator(field.getName(), value).getObject();
				SBAStore.getInstance().put(innerComplexObject);
				complexObject.getChildOIDs().add(innerComplexObject.getOID());
			}
		}
		return complexObject;
	}
	
	
	private Collection<Object> castToCollection(String fieldName, Object value) {
		List<Object> result = Lists.newArrayList();
		
		try {
			Object [] array = (Object[]) value;
			for(Object v : array) {
				result.add(v);
			}
			return result;
		} catch (Exception e) {
			log.debug("field {} is no array of type object", fieldName);
		}
		
		try {
			int [] array = (int[]) value;
			for(int v : array) {
				result.add(v);
			}
			return result;
		} catch (ClassCastException e) {
			log.debug("field {} is no array of int", fieldName);
		}
		
		try {
			double [] array = (double[]) value;
			for(double v : array) {
				result.add(v);
			}
			return result;
		} catch (ClassCastException e) {
			log.debug("field {} is no array of double", fieldName);
		}
		
		try {
			long [] array = (long[]) value;
			for(long v : array) {
				result.add(v);
			}
			return result;
		} catch (ClassCastException e) {
			log.debug("field {} is no array of long", fieldName);
		}
		
		try {
			boolean [] array = (boolean[]) value;
			for(boolean v : array) {
				result.add(v);
			}
			return result;
		} catch (ClassCastException e) {
			log.debug("field {} is no array of boolean", fieldName);
		}
		
		try {
			short [] array = (short[]) value;
			for(short v : array) {
				result.add(v);
			}
			return result;
		} catch (ClassCastException e) {
			log.debug("field {} is no array of short", fieldName);
		}
		
		try {
			char [] array = (char[]) value;
			for(int v : array) {
				result.add(v);
			}
			return result;
		} catch (ClassCastException e) {
			log.debug("field {} is no array of char", fieldName);
		}
		
		try {
			byte [] array = (byte[]) value;
			for(byte v : array) {
				result.add(v);
			}
			return result;
		} catch (ClassCastException e) {
			log.debug("field {} is no array of byte", fieldName);
		}
		
		log.info("Has no idea what to do with {} and value {}", fieldName, value);
		
		return result;
	}

	private boolean isCollectionObject(Object value) {
		return Iterable.class.isAssignableFrom(value.getClass());
	}
	
	private boolean isArrayObject(Field field) {
		return field.getType().isArray();
	}

	private boolean isPrimitiveObject(Object obj) {
		Class<?> clazz = obj.getClass();
		return Double.TYPE.equals(clazz)
				|| Long.TYPE.equals(clazz)
				|| Integer.TYPE.equals(clazz)
				|| Boolean.TYPE.equals(clazz)
				|| Double.class.equals(clazz)
				|| Long.class.equals(clazz)
				|| Integer.class.equals(clazz)
				|| Boolean.class.equals(clazz)
				|| String.class.equals(clazz)
				|| Character.class.equals(clazz);
	}
}
