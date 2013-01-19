package pl.edu.pjwstk.jps.parser;

import com.beust.jcommander.internal.Lists;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import edu.pjwstk.jps.datastore.IComplexObject;
import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.result.*;
import org.junit.Before;
import org.omg.CORBA.BAD_INV_ORDER;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pjwstk.jps.ast.AbstractExpression;
import pl.edu.pjwstk.jps.ast.datastore.ComplexObject;
import pl.edu.pjwstk.jps.ast.datastore.SBAStore;
import pl.edu.pjwstk.jps.interpreter.InterpreterUtils;
import pl.edu.pjwstk.jps.interpreter.InterpreterUtils.*;
import pl.edu.pjwstk.jps.interpreter.InterpreterUtils;
import pl.edu.pjwstk.jps.result.*;

import javax.annotation.Nullable;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static pl.edu.pjwstk.jps.interpreter.InterpreterUtils.deRefrence;
import static pl.edu.pjwstk.jps.interpreter.InterpreterUtils.equals;
import static pl.edu.pjwstk.jps.interpreter.InterpreterUtils.toSingleResult;

/**
 * User: pawel
 * Date: 19.01.13
 * Time: 14:09
 */
public class WcisloParserTest extends ParserTest {
	@BeforeClass
	public void beforeClass() throws URISyntaxException {
		SBAStore.getInstance().reset();
		SBAStore.getInstance().loadXML(new File(WcisloParserTest.class.getResource("/test.xml").toURI()).getAbsolutePath());
	}

	@BeforeMethod
	public void beforeMethod() {
		init();
	}

	public void query1Test() throws Exception {
		AbstractExpression expression = getExpression("all 1 true");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query2Test() throws Exception {
		AbstractExpression expression = getExpression("all (bag(1, bag(2,3) groupas wew) as num) (num == 2)");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());
	}

	public void query3Test() throws Exception {
		AbstractExpression expression = getExpression("all emp married");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query4Test() throws Exception {
		AbstractExpression expression = getExpression("true and false");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());
	}

	public void query5Test() throws Exception {
		AbstractExpression expression = getExpression("booleanValue and true");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query6Test() throws Exception {
		AbstractExpression expression = getExpression("false and true and 1");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());
	}

	public void query7Test() throws Exception {
		AbstractExpression expression = getExpression("any 1 true");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query8Test() throws Exception {
		AbstractExpression expression = getExpression("any emp married");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query9Test() throws Exception {
		AbstractExpression expression = getExpression("1 as liczba");
		BinderResult binder = getResult(expression, BinderResult.class);
		assertEquals(binder.getName(), "liczba");
		assertTrue(binder.getValue() instanceof IntegerResult);
		assertEquals(((IntegerResult) binder.getValue()).getValue(), Integer.valueOf(1));
	}

	public void query10Test() throws Exception {
		AbstractExpression expression = getExpression("bag(1,2) as num");
		BagResult res = getResult(expression, BagResult.class);
		assertEquals(res.getElements().size(), 2);
		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult single : res.getElements()) {
			assertTrue(single instanceof BinderResult);
			assertEquals(((BinderResult) single).getName(), "num");
			assertTrue(((BinderResult) single).getValue() instanceof IntegerResult);
			IntegerResult intNum = (IntegerResult) ((BinderResult) single).getValue();
			ints.add(intNum.getValue());
		}
		assertEquals(ints, Sets.newHashSet(1, 2));
	}

	public void query11Test() throws Exception {
		AbstractExpression expression = getExpression("bag(1)");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(1));
	}

	public void query12Test() throws Exception {
		AbstractExpression expression = getExpression("bag(1,2,3)");
		BagResult res = getResult(expression, BagResult.class);
		assertEquals(res.getElements().size(), 3);
		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult single : res.getElements()) {
			assertTrue(single instanceof IntegerResult);
			ints.add(((IntegerResult) single).getValue());
		}
		assertEquals(ints, Sets.newHashSet(1, 2, 3));
	}

	public void query13Test() throws Exception {
		AbstractExpression expression = getExpression("bag(1+2,3)");
		BagResult res = getResult(expression, BagResult.class);
		assertEquals(res.getElements().size(), 2);
		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult single : res.getElements()) {
			assertTrue(single instanceof IntegerResult);
			ints.add(((IntegerResult) single).getValue());
		}
		assertEquals(ints, Sets.newHashSet(3, 3));
	}

	public void query14Test() throws Exception {
		AbstractExpression expression = getExpression("bag(bag(1,2,3))");
		BagResult res = getResult(expression, BagResult.class);
		assertEquals(res.getElements().size(), 3);
		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult single : res.getElements()) {
			assertTrue(single instanceof IntegerResult);
			ints.add(((IntegerResult) single).getValue());
		}
		assertEquals(ints, Sets.newHashSet(1, 2, 3));
	}

	public void query15Test() throws Exception {
		AbstractExpression expression = getExpression("integerNumber");
		ReferenceResult ref = getResult(expression, ReferenceResult.class);
		IAbstractQueryResult single = deRefrence(ref, SBAStore.getInstance());
		assertTrue(single instanceof IntegerResult);
		assertEquals(((IntegerResult) single).getValue(), Integer.valueOf(10));
	}

	public void query16Test() throws Exception {
		AbstractExpression expression = getExpression("realNumber");
		ReferenceResult ref = getResult(expression, ReferenceResult.class);
		IAbstractQueryResult single = deRefrence(ref, SBAStore.getInstance());
		assertTrue(single instanceof DoubleResult);
		assertEquals(((DoubleResult) single).getValue(), Double.valueOf(234.35));
	}

	public void query17Test() throws Exception {
		AbstractExpression expression = getExpression("booleanValue");
		ReferenceResult ref = getResult(expression, ReferenceResult.class);
		IAbstractQueryResult single = deRefrence(ref, SBAStore.getInstance());
		assertTrue(single instanceof BooleanResult);
		assertEquals(((BooleanResult) single).getValue(), Boolean.TRUE);
	}

	public void query18Test() throws Exception {
		AbstractExpression expression = getExpression("stringValue");
		ReferenceResult ref = getResult(expression, ReferenceResult.class);
		IAbstractQueryResult single = deRefrence(ref, SBAStore.getInstance());
		assertTrue(single instanceof StringResult);
		assertEquals(((StringResult) single).getValue(), "Ala");
	}

	public void query19Test() throws Exception {
		AbstractExpression expression = getExpression("pomidor");
		BagResult bag = getResult(expression, BagResult.class);
		Set<String> strings = Sets.newHashSet();
		for(ISingleResult single : bag.getElements()) {
			IAbstractQueryResult deRef = deRefrence(single, SBAStore.getInstance());
			assertTrue(deRef instanceof StringResult);
			strings.add(((StringResult) deRef).getValue());
		}
		assertEquals(
				strings,
				Sets.newHashSet(
						"Pan pomidor wlazł na tyczkę",
						"I przedrzeźnia ogrodniczkę.",
						"\"Jak pan może,", "Panie pomidorze?!\""));
	}

	public void query20Test() throws Exception {
		AbstractExpression expression = getExpression("sampleComplexObj");
		ReferenceResult ref = getResult(expression, ReferenceResult.class);

		ISBAObject sbaObject = SBAStore.getInstance().retrieve(((ReferenceResult) ref).getOIDValue());
		assertTrue(sbaObject instanceof ComplexObject);
		assertEquals(sbaObject.getName(), "sampleComplexObj");
		assertEquals(((ComplexObject) sbaObject).getChildOIDs().size(), 5);
	}

	public void query21Test() throws Exception {
		AbstractExpression expression = getExpression("(1,2)");
		StructResult struct = getResult(expression, StructResult.class);
		assertEquals(struct.elements().size(), 2);

		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult single : struct.elements()) {
			assertTrue(single instanceof IntegerResult);
			ints.add(((IntegerResult) single).getValue());
		}
		assertEquals(ints, Sets.newHashSet(1, 2));
	}

	public void query22Test() throws Exception {
		AbstractExpression expression = getExpression("(bag(1,2),3)");
		BagResult bag = getResult(expression, BagResult.class);
		structEqual(Collections2.transform(bag.getElements(), new BagElementsToStruct()), new int[][] {{1,3}, {2,3}});
	}

	public void query23Test() throws Exception {
		AbstractExpression expression = getExpression("bag(1,2),bag(3,4)");
		BagResult bag = getResult(expression, BagResult.class);
		structEqual(Collections2.transform(bag.getElements(), new Function<ISingleResult, StructResult>() {
			@Nullable
			@Override
			public StructResult apply(@Nullable ISingleResult input) {
				assertTrue(input instanceof StructResult);
				return (StructResult) input;
			}
		}), new int[][] {
				{1,3}, {1,4},
				{2,3}, {2,4}
		});
	}

	public void query24Test() throws Exception {
		AbstractExpression expression = getExpression("10/5");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(2));
	}

	public void query25Test() throws Exception {
		AbstractExpression expression = getExpression("5/3.50");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(1.428), 0.002);
	}

	public void query26Test() throws Exception {
		AbstractExpression expression = getExpression("3.50/5");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(0.7), 0.002);
	}

	public void query27Test() throws Exception {
		AbstractExpression expression = getExpression("3.50/5.50");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(0.636), 0.002);
	}

	public void query28Test() throws Exception {
		AbstractExpression expression = getExpression("(1 as x).(x)");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(1));
	}

	public void query29Test() throws Exception {
		AbstractExpression expression = getExpression("(1,2).(\"Ala\")");
		StringResult res = getResult(expression, StringResult.class);
		assertEquals(res.getValue(), "Ala");
	}

	public void query30Test() throws Exception {
		AbstractExpression expression = getExpression("emp.book.author");
		BagResult bag = getResult(expression, BagResult.class);
		Set<String> names = Sets.newHashSet();
		for(ISingleResult single : bag.getElements()) {
			assertTrue(single instanceof ReferenceResult);
			ISingleResult res = (ISingleResult) deRefrence(single, SBAStore.getInstance());
			assertTrue(res instanceof IStringResult);
			names.add(((IStringResult) res).getValue());
		}
		assertEquals(names, Sets.newHashSet("Juliusz Słowacki", "Adam Mickiewicz", "Aleksander Dumas (syn)"));
	}

	public void query31Test() throws Exception {
		AbstractExpression expression = getExpression("bag(1,2).(\"Ala\")");
		BagResult res = getResult(expression, BagResult.class);
		assertEquals(res.getElements().size(), 2);
		for(ISingleResult singleResult : res.getElements()) {
			assertTrue(singleResult instanceof StringResult);
			assertEquals(((StringResult) singleResult).getValue(), "Ala");
		}
	}

	public void query32Test() throws Exception {
		AbstractExpression expression = getExpression("1 == 2");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());
	}

	public void query33Test() throws Exception {
		AbstractExpression expression = getExpression("integerNumber == 10");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void query34Test() throws Exception {
		AbstractExpression expression = getExpression("bag(1,2) == 2");
		BooleanResult res = getResult(expression, BooleanResult.class);
	}

	public void query35Test() throws Exception {
		AbstractExpression expression = getExpression("booleanValue == true");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query36Test() throws Exception {
		AbstractExpression expression = getExpression("stringValue == \"Ala\"");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query37Test() throws Exception {
		AbstractExpression expression = getExpression("1 == true");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());
	}

	public void query38Test() throws Exception {
		AbstractExpression expression = getExpression("5 == \"5\"");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());
	}

	public void query39Test() throws Exception {
		AbstractExpression expression = getExpression("5.50 == 5");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());
	}

	public void query40Test() throws Exception {
		AbstractExpression expression = getExpression("1 > 1");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());
	}

	public void query41Test() throws Exception {
		AbstractExpression expression = getExpression("3 > 2.99");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query42Test() throws Exception {
		AbstractExpression expression = getExpression("24.35 > 24.34");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query43Test() throws Exception {
		AbstractExpression expression = getExpression("1 >= 1");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query44Test() throws Exception {
		AbstractExpression expression = getExpression("3 >= 2.99");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query45Test() throws Exception {
		AbstractExpression expression = getExpression("24.35 >= 24.34");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query46Test() throws Exception {
		AbstractExpression expression = getExpression("bag(1,2,3) group as num");
		BinderResult res = getResult(expression, BinderResult.class);
		assertEquals(res.getName(), "num");
		assertTrue(res.getValue() instanceof BagResult);

		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult singleResult : ((BagResult) res.getValue()).getElements()) {
			assertTrue(singleResult instanceof IntegerResult);
			ints.add(((IntegerResult) singleResult).getValue());
		}
		assertEquals(ints, Sets.newHashSet(1,2,3));
	}

	public void query47Test() throws Exception {
		AbstractExpression expression = getExpression("1 group as liczba");
		BinderResult res = getResult(expression, BinderResult.class);
		assertEquals(res.getName(), "liczba");
		assertTrue(res.getValue() instanceof IntegerResult);
		IntegerResult integerResult = (IntegerResult) res.getValue();
		assertEquals(integerResult.getValue(), Integer.valueOf(1));
	}

	public void query48Test() throws Exception {
		AbstractExpression expression = getExpression("bag(1,2,3) intersect bag(2,3)");
		BagResult res = getResult(expression, BagResult.class);
		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult singleResult : res.getElements()) {
			assertTrue(singleResult instanceof IntegerResult);
			ints.add(((IntegerResult) singleResult).getValue());
		}
		assertEquals(ints, Sets.newHashSet(2,3));
	}

	public void query49Test() throws Exception {
		AbstractExpression expression = getExpression("1 intersect 1");
		BagResult res = getResult(expression, BagResult.class);
		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult singleResult : res.getElements()) {
			assertTrue(singleResult instanceof IntegerResult);
			ints.add(((IntegerResult) singleResult).getValue());
		}
		assertEquals(ints, Sets.newHashSet(1));
	}

	public void query50Test() throws Exception {
		AbstractExpression expression = getExpression("(1,2) intersect (2,3)");
		BagResult res = getResult(expression, BagResult.class);
		assertTrue(res.getElements().isEmpty());
	}

	public void query51Test() throws Exception {
		AbstractExpression expression = getExpression("(1,2) intersect (1,2)");
		BagResult res = getResult(expression, BagResult.class);
		assertEquals(res.getElements().size(), 1);
		structEqual(Collections2.transform(res.getElements(), new BagElementsToStruct()), new int[][]{{1,2}});
	}

	public void query52Test() throws Exception {
		AbstractExpression expression = getExpression("bag(\"ala\",2,3) intersect bag(2,3.40)");
		BagResult res = getResult(expression, BagResult.class);
		assertEquals(res.getElements().size(), 1);
		ISingleResult singleResult = res.getElements().iterator().next();
		assertTrue(singleResult instanceof  IntegerResult);
		assertEquals(((IntegerResult) singleResult).getValue(), Integer.valueOf(2));
	}

	public void query53Test() throws Exception {
		AbstractExpression expression = getExpression("1 join 2");
		StructResult res = getResult(expression, StructResult.class);
		structEqual(Arrays.asList(res), new int[][]{{1,2}});
	}

	public void query54Test() throws Exception {
		AbstractExpression expression = getExpression("(1 as n) join n");
		StructResult res = getResult(expression, StructResult.class);
		assertEquals(res.elements().size(), 2);
		ISingleResult first = res.elements().get(0);
		ISingleResult second = res.elements().get(1);

		assertTrue(first instanceof BinderResult);
		assertTrue(second instanceof IntegerResult);

		assertEquals(((IntegerResult) second).getValue(), Integer.valueOf(1));
		BinderResult binderResult = (BinderResult) first;
		assertEquals(binderResult.getName(), "n");
		assertTrue(binderResult.getValue() instanceof IntegerResult);
		assertEquals(((IntegerResult)binderResult.getValue()).getValue(), Integer.valueOf(1));
	}


	public void query55Test() throws Exception {
		AbstractExpression expression = getExpression("(emp) join (married)");
		BagResult res = getResult(expression, BagResult.class);
		assertEquals(res.getElements().size(), 2);
		List<ISingleResult> list = Lists.newArrayList(res.getElements());

		StructResult first = (StructResult) list.get(0);
		StructResult second = (StructResult) list.get(1);

		assertEquals(first.elements().size(), 2);
		assertEquals(second.elements().size(), 2);

		ReferenceResult ref1 = (ReferenceResult) first.elements().get(0);
		BooleanResult booleanResult1 = (BooleanResult) first.elements().get(1);
		assertTrue(booleanResult1.getValue());
		ComplexObject complex1 = (ComplexObject) SBAStore.getInstance().retrieve(ref1.getOIDValue());
		assertEquals(complex1.getName(), "emp");
		assertEquals(complex1.getChildOIDs().size(), 6);

		ReferenceResult ref2 = (ReferenceResult) second.elements().get(0);
		BooleanResult booleanResult2 = (BooleanResult) second.elements().get(1);
		assertTrue(booleanResult2.getValue());
		ComplexObject complex2 = (ComplexObject) SBAStore.getInstance().retrieve(ref2.getOIDValue());
		assertEquals(complex2.getName(), "emp");
		assertEquals(complex2.getChildOIDs().size(), 7);
	}

	public void query56Test() throws Exception {
		AbstractExpression expression = getExpression("(emp as e) join (e.married)");
		BagResult res = getResult(expression, BagResult.class);
		assertEquals(res.getElements().size(), 2);
		List<ISingleResult> list = Lists.newArrayList(res.getElements());

		StructResult first = (StructResult) list.get(0);
		StructResult second = (StructResult) list.get(1);

		assertEquals(first.elements().size(), 2);
		assertEquals(second.elements().size(), 2);

		BinderResult binder1 = (BinderResult) first.elements().get(0);
		assertEquals(binder1.getName(), "e");
		ReferenceResult ref1 = (ReferenceResult) binder1.getValue();
		BooleanResult booleanResult1 = (BooleanResult) first.elements().get(1);
		assertTrue(booleanResult1.getValue());
		ComplexObject complex1 = (ComplexObject) SBAStore.getInstance().retrieve(ref1.getOIDValue());
		assertEquals(complex1.getName(), "emp");
		assertEquals(complex1.getChildOIDs().size(), 6);

		BinderResult binder2 = (BinderResult)second.elements().get(0);
		assertEquals(binder2.getName(), "e");
		ReferenceResult ref2 = (ReferenceResult) binder2.getValue();
		BooleanResult booleanResult2 = (BooleanResult) second.elements().get(1);
		assertTrue(booleanResult2.getValue());
		ComplexObject complex2 = (ComplexObject) SBAStore.getInstance().retrieve(ref2.getOIDValue());
		assertEquals(complex2.getName(), "emp");
		assertEquals(complex2.getChildOIDs().size(), 7);
	}

	public void query57Test() throws Exception {
		AbstractExpression expression = getExpression("1 <= 1");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query58Test() throws Exception {
		AbstractExpression expression = getExpression("24.34 <= 24.35");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query59Test() throws Exception {
		AbstractExpression expression = getExpression("2.99 <= 3");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query60Test() throws Exception {
		AbstractExpression expression = getExpression("1<1");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());
	}

	public void query61Test() throws Exception {
		AbstractExpression expression = getExpression("24.34 < 24.35");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query62Test() throws Exception {
		AbstractExpression expression = getExpression("2.99 < 3");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query63Test() throws Exception {
		AbstractExpression expression = getExpression("max(1)");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(1));
	}

	public void query64Test() throws Exception {
		AbstractExpression expression = getExpression("max (bag(1,3.35,3))");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(3.35));
	}

	public void query65Test() throws Exception {
		AbstractExpression expression = getExpression("min(1)");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(1));
	}

	public void query66Test() throws Exception {
		AbstractExpression expression = getExpression("min (bag(1.01,2.35,3))");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(1.01));
	}

	public void query67Test() throws Exception {
		AbstractExpression expression = getExpression("min (bag(1.01,2.35,3))");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(1.01));
	}

	public void query68Test() throws Exception {
		AbstractExpression expression = getExpression("10-5");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(5));
	}

	public void query69Test() throws Exception {
		AbstractExpression expression = getExpression("5-3.50");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(1.5), 0.001);
	}

	public void query70Test() throws Exception {
		AbstractExpression expression = getExpression("3.50-5");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(-1.5), 0.001);
	}

	public void query71Test() throws Exception {
		AbstractExpression expression = getExpression("3.50-5.50");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(-2));
	}

	public void query72Test() throws Exception {
		AbstractExpression expression = getExpression("10%5");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(0));
	}

	public void query73Test() throws Exception {
		AbstractExpression expression = getExpression("5%3.50");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(1.5));
	}

	public void query74Test() throws Exception {
		AbstractExpression expression = getExpression("3.50%5");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(3.5));
	}

	public void query75Test() throws Exception {
		AbstractExpression expression = getExpression("3.50%5.50");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(3.5));
	}

	public void query76Test() throws Exception {
		AbstractExpression expression = getExpression("10*5");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(50));
	}

	public void query77Test() throws Exception {
		AbstractExpression expression = getExpression("5*3.50");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(17.5));
	}

	public void query78Test() throws Exception {
		AbstractExpression expression = getExpression("3.50*5");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(17.5));
	}

	public void query79Test() throws Exception {
		AbstractExpression expression = getExpression("3.50*5.50");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(19.25));
	}

	public void query80Test() throws Exception {
		AbstractExpression expression = getExpression("true or false");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query81Test() throws Exception {
		AbstractExpression expression = getExpression("booleanValue or false");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query82Test() throws Exception {
		AbstractExpression expression = getExpression("true or false or 1");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query83Test() throws Exception {
		AbstractExpression expression = getExpression("10+5");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(15));
	}

	public void query84Test() throws Exception {
		AbstractExpression expression = getExpression("5+3.50");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(8.5));
	}

	public void query85Test() throws Exception {
		AbstractExpression expression = getExpression("5+3.50");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(8.5));
	}

	public void query86Test() throws Exception {
		AbstractExpression expression = getExpression("3.50+5.50");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(9));
	}

	public void query87Test() throws Exception {
		AbstractExpression expression = getExpression("3+\"Ala\"");
		StringResult res = getResult(expression, StringResult.class);
		assertEquals(res.getValue(), "3Ala");
	}

	public void query88Test() throws Exception {
		AbstractExpression expression = getExpression("3.5+\"Ala\"");
		StringResult res = getResult(expression, StringResult.class);
		assertEquals(res.getValue(), "3.5Ala");
	}

	public void query89Test() throws Exception {
		AbstractExpression expression = getExpression("\"Ala\"+3.7");
		StringResult res = getResult(expression, StringResult.class);
		assertEquals(res.getValue(), "Ala3.7");
	}

	public void query90Test() throws Exception {
		AbstractExpression expression = getExpression("true+\"Ala\"");
		StringResult res = getResult(expression, StringResult.class);
		assertEquals(res.getValue(), "trueAla");
	}

	public void query91Test() throws Exception {
		AbstractExpression expression = getExpression("struct(1)");
		StructResult res = getResult(expression, StructResult.class);
		structEqual(Arrays.asList(res), new int[][]{{1}});
	}

	public void query92Test() throws Exception {
		AbstractExpression expression = getExpression("struct(1,2,3)");
		StructResult res = getResult(expression, StructResult.class);
		structEqual(Arrays.asList(res), new int[][]{{1, 2, 3}});
	}

	public void query93Test() throws Exception {
		AbstractExpression expression = getExpression("struct(1+2,3)");
		StructResult res = getResult(expression, StructResult.class);
		structEqual(Arrays.asList(res), new int[][]{{3,3}});
	}

	public void query94Test() throws Exception {
		AbstractExpression expression = getExpression("struct(struct(1,2,3))");
		StructResult res = getResult(expression, StructResult.class);
		structEqual(Arrays.asList(res), new int[][]{{1,2,3}});
	}

	public void query95Test() throws Exception {
		AbstractExpression expression = getExpression("1 union 2");
		BagResult res = getResult(expression, BagResult.class);
		bagEquals(res, new int[]{1, 2});
	}

	public void query96Test() throws Exception {
		AbstractExpression expression = getExpression("bag(1,2) union bag(3,4)");
		BagResult res = getResult(expression, BagResult.class);
		bagEquals(res, new int[]{1,2,3,4});
	}

	public void query97Test() throws Exception {
		AbstractExpression expression = getExpression("(1,2) union (3,4)");
		BagResult res = getResult(expression, BagResult.class);
		structEqual(Collections2.transform(res.getElements(), new BagElementsToStruct()), new int[][]{{1, 2}, {3, 4}});
	}

	public void query98Test() throws Exception {
		AbstractExpression expression = getExpression("unique(bag(1,2,1))");
		BagResult res = getResult(expression, BagResult.class);
		bagEquals(res, new int[]{1, 2});
	}

	public void query99Test() throws Exception {
		AbstractExpression expression = getExpression("unique(bag(1.01,2,1.01,\"ala\"))");
		BagResult res = getResult(expression, BagResult.class);
		assertEquals(res.getElements().size(), 3);
		List<ISingleResult> list = Lists.newArrayList(res.getElements());
		assertTrue(list.get(0) instanceof DoubleResult);
		assertEquals(((DoubleResult) list.get(0)).getValue(), Double.valueOf(1.01));

		assertTrue(list.get(1) instanceof IntegerResult);
		assertEquals(((IntegerResult) list.get(1)).getValue(), Integer.valueOf(2));

		assertTrue(list.get(2) instanceof StringResult);
		assertEquals(((StringResult) list.get(2)).getValue(), "ala");
	}

	public void query100Test() throws Exception {
		AbstractExpression expression = getExpression("bag(1,2) where true");
		BagResult res = getResult(expression, BagResult.class);
		bagEquals(res, new int[]{1, 2});
	}

	public void query101Test() throws Exception {
		AbstractExpression expression = getExpression("bag(1,2,3) as n where n == 1");
		BagResult res = getResult(expression, BagResult.class);
		assertEquals(res.getElements().size(), 1);
		BinderResult binder = (BinderResult) res.getElements().iterator().next();
		assertEquals(binder.getName(), "n");
		IntegerResult integerResult = (IntegerResult) binder.getValue();
		assertEquals(integerResult.getValue(), Integer.valueOf(1));
	}

	public void query102Test() throws Exception {
		AbstractExpression expression = getExpression("emp where married");
		BagResult res = getResult(expression, BagResult.class);
		assertEquals(res.getElements().size(), 2);
		List<ISingleResult> list = Lists.newArrayList(res.getElements());
		ReferenceResult ref1 = (ReferenceResult) list.get(0);
		ReferenceResult ref2 = (ReferenceResult) list.get(1);

		ComplexObject complex1 = (ComplexObject) SBAStore.getInstance().retrieve(ref1.getOIDValue());
		ComplexObject complex2 = (ComplexObject) SBAStore.getInstance().retrieve(ref2.getOIDValue());

		assertEquals(complex1.getName(), "emp");
		assertEquals(complex2.getName(), "emp");

		assertEquals(complex1.getChildOIDs().size(), 6);
		assertEquals(complex2.getChildOIDs().size(), 7);
	}

	public void query103Test() throws Exception {
		AbstractExpression expression = getExpression("sum(1)");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(1));
	}

	public void query104Test() throws Exception {
		AbstractExpression expression = getExpression("sum(bag(1.01,2.35,3))");
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), Double.valueOf(6.36), 0.001);
	}

	public void query105Test() throws Exception {
		AbstractExpression expression = getExpression("count(1)");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(1));
	}

	public void query106Test() throws Exception {
		AbstractExpression expression = getExpression("count(bag(1.01,2.35,3))");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(3));
	}

	public void query107Test() throws Exception {
		AbstractExpression expression = getExpression("count(1.01,2.35,3)");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(3));
	}

	public void query108Test() throws Exception {
		AbstractExpression expression = getExpression("not true");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());
	}

	public void query109Test() throws Exception {
		AbstractExpression expression = getExpression("not false");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query110Test() throws Exception {
		AbstractExpression expression = getExpression("true xor false");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query111Test() throws Exception {
		AbstractExpression expression = getExpression("true xor true");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());
	}

	public void query112Test() throws Exception {
		AbstractExpression expression = getExpression("false xor true");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query113Test() throws Exception {
		AbstractExpression expression = getExpression("booleanValue xor true");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());
	}

	public void query114Test() throws Exception {
		AbstractExpression expression = getExpression("bag(2,3) in bag(1,2,3)");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query115Test() throws Exception {
		AbstractExpression expression = getExpression("1 in 1");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query116Test() throws Exception {
		AbstractExpression expression = getExpression("(1,2) in (2,3)");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());
	}

	public void query117Test() throws Exception {
		AbstractExpression expression = getExpression("(1,2) in (1,2)");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void query118Test() throws Exception {
		AbstractExpression expression = getExpression("bag(1,2,3) minus bag(2,3)");
		BagResult res = getResult(expression, BagResult.class);
		bagEquals(res, new int[]{1});
	}

	public void query119Test() throws Exception {
		AbstractExpression expression = getExpression("1 minus 1");
		BagResult res = getResult(expression, BagResult.class);
		assertTrue(res.getElements().isEmpty());
	}

	public void query120Test() throws Exception {
		AbstractExpression expression = getExpression("(1,2) minus (2,3)");
		StructResult res = getResult(expression, StructResult.class);
		structEqual(Arrays.asList(res), new int[][]{{1,2}});
	}

	public void query121Test() throws Exception {
		AbstractExpression expression = getExpression("(1,2) minus (1,2)");
		BagResult res = getResult(expression, BagResult.class);
		assertTrue(res.getElements().isEmpty());
	}

	public void query122Test() throws Exception {
		AbstractExpression expression = getExpression("bag(\"ala\",2,3) minus bag(2,3.40)");
		BagResult res = getResult(expression, BagResult.class);
		List<ISingleResult> list = Lists.newArrayList(res.getElements());

		IStringResult string = (IStringResult) list.get(0);
		assertEquals(string.getValue(), "ala");

		IntegerResult intResult = (IntegerResult) list.get(1);
		assertEquals(intResult.getValue(), Integer.valueOf(3));
	}

	private void bagEquals(BagResult res, int[] ints) {
		assertEquals(res.getElements().size(), ints.length);

		Set<Integer> intsSet = Sets.newHashSet();
		Set<Integer> expected = Sets.newHashSet();
		for(int i : ints) {
			expected.add(i);
		}
		for(ISingleResult singleResult : res.getElements()) {
			assertTrue(singleResult instanceof IntegerResult);
			intsSet.add(((IntegerResult) singleResult).getValue());
		}

		assertEquals(intsSet, expected);
	}

	private static final class BagElementsToStruct implements Function<ISingleResult, StructResult> {
		@Override
		public StructResult apply(@Nullable ISingleResult input) {
			assertTrue(input instanceof StructResult);
			return (StructResult) input;
		}
	}

	private static void structEqual(Collection<StructResult> struct, int [][] elements) {
		assertEquals(struct.size(), elements.length);
		List<StructResult> list = Lists.newArrayList(struct);
		for(int i = 0 ; i < list.size(); i++) {
			assertEquals(list.get(i).elements().size(), elements[i].length);
			for(int j = 0 ; j < list.get(i).elements().size(); j++) {
				ISingleResult singleResult = list.get(i).elements().get(j);
				assertTrue(singleResult instanceof IntegerResult);
				int value = ((IntegerResult) singleResult).getValue().intValue();
				assertEquals(value, elements[i][j]);
			}
		}
	}
}
