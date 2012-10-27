package pl.edu.pjwstk.jps.ast.datastore.util;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import pl.edu.pjwstk.jps.ast.datastore.BooleanObject;
import pl.edu.pjwstk.jps.ast.datastore.DoubleObject;
import pl.edu.pjwstk.jps.ast.datastore.IntegerObject;

import edu.pjwstk.jps.datastore.ISBAObject;

public class ObjectCreatorTest {
	
	@Test
	public void testPrimitive() {
		ISBAObject o1 = new JavaObjectCreator("int", 10).getObject();
		Assert.assertEquals(o1.getClass(), IntegerObject.class);
		Assert.assertEquals(((IntegerObject)o1).getValue().intValue(), 10);
		
		ISBAObject o2 = new JavaObjectCreator("double", 928278272726261122212000.0).getObject();
		Assert.assertEquals(o2.getClass(), DoubleObject.class);
		Assert.assertEquals(((DoubleObject)o2).getValue().doubleValue(), 928278272726261122212000.0);
		
		ISBAObject o3 = new JavaObjectCreator("boolean", true).getObject();
		Assert.assertEquals(o3.getClass(), BooleanObject.class);
		Assert.assertEquals(((BooleanObject)o3).getValue().booleanValue(), true);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testPrimitiveArray() {
		int [] array = new int[]{1,2,3};
		new JavaObjectCreator("asd", array).getObject();
	}
	
	@Test
	public void testSimpleComplex() {
		new JavaObjectCreator("simpleObject", new SimpleObject()).getObject();
	}
	
	public static final class SimpleObject {
		private int privateIntField = 20;
		public int intField = 10;
		public double doubleField = 0.32;
		public String stringField = "str";
		public String emptyStringFIled = null;
		public int [] intArray = new int [] { 1,2,3 };
		public boolean [] booleanArray = new boolean[] { true, true, false };
		public String [] stringArray = new String[] { "z", "x", "c" };
		public List<String> stringList = Lists.newArrayList("a", "b", "c");
		public SimpleObject2 simpleObject = new SimpleObject2();
	}
	
	public static final class SimpleObject2 {
		public String strFiled = "object2";
	}
}
