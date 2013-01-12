package pl.edu.pjwstk.jps.interpreter;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import edu.pjwstk.jps.ast.unary.IBagExpression;
import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.result.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pjwstk.jps.ast.auxname.AsExpression;
import pl.edu.pjwstk.jps.ast.auxname.GroupAsExpression;
import pl.edu.pjwstk.jps.ast.binary.*;
import pl.edu.pjwstk.jps.ast.terminal.BooleanTerminal;
import pl.edu.pjwstk.jps.ast.terminal.DoubleTerminal;
import pl.edu.pjwstk.jps.ast.terminal.IntegerTerminal;
import pl.edu.pjwstk.jps.ast.terminal.StringTerminal;
import pl.edu.pjwstk.jps.ast.unary.*;
import pl.edu.pjwstk.jps.interpreter.envs.ENVS;
import pl.edu.pjwstk.jps.qres.interpreter.QResStack;

import java.util.Iterator;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.*;
import static pl.edu.pjwstk.jps.interpreter.InterpreterUtils.toIterable;

/**
 * User: pawel
 * Date: 01.01.13
 * Time: 18:01
 */
public class InterpreterTest {
	private ISBAStore sbaStore;
	private QResStack stack;
	private ENVS envs;

	private Interpreter interpreter;

	@BeforeMethod
	public void setUp() throws Exception {
		sbaStore = mock(ISBAStore.class);
		envs = mock(ENVS.class);

		stack = new QResStack();
		interpreter = new Interpreter(sbaStore, stack, envs);
	}

	@Test	//DONE
	public void testVisitAsExpression() throws Exception {
		interpreter.visitAsExpression(new AsExpression("test", new StringTerminal("string")));

		IBinderResult result = (IBinderResult) InterpreterUtils.toSingleResult(stack.pop());
		assertEquals(stack.size(), 0);

		assertEquals(result.getName(), "test");
		if(result.getValue() instanceof IStringResult) {
			assertEquals(((IStringResult) result.getValue()).getValue(), "string");
		} else {
			fail("Expected " + IStringResult.class + " but got " + result.getValue().getClass());
		}
	}

	@Test	//DONE
	public void testVisitGroupAsExpression() throws Exception {
		interpreter.visitGroupAsExpression(new GroupAsExpression("test", new IntegerTerminal(1)));
		IBinderResult binder = (IBinderResult) InterpreterUtils.toSingleResult(stack.pop());
		assertEquals(stack.size(), 0);

		assertEquals(binder.getName(), "test");
		if(binder.getValue() instanceof IIntegerResult) {
			assertEquals(((IIntegerResult) binder.getValue()).getValue(), Integer.valueOf(1));
		} else {
			fail("Expected " + IIntegerResult.class + " but got " + binder.getValue().getClass());
		}
	}

	@Test	//DONE
	public void testVisitAllExpression() throws Exception {
		interpreter.visitAllExpression(new ForAllExpression(new StringTerminal("string"),
				new BooleanTerminal(true)));

		IAbstractQueryResult result = stack.pop();
		assertEquals(stack.size(), 0);
		IBooleanResult booleanResult = (IBooleanResult) InterpreterUtils.toSingleResult(result);
		assertEquals(booleanResult.getValue(), Boolean.TRUE);

		interpreter.visitAllExpression(new ForAllExpression(new StringTerminal("string"),
				new BooleanTerminal(false)));

		result = stack.pop();
		assertEquals(stack.size(), 0);
		booleanResult = (IBooleanResult) InterpreterUtils.toSingleResult(result);
		assertEquals(booleanResult.getValue(), Boolean.FALSE);
	}

	@Test	//DONE
	public void testVisitAndExpression() throws Exception {
		IAbstractQueryResult res = interpreter.eval(new AndExpression(new BooleanTerminal(true), new BooleanTerminal(false)));
		assertEquals(stack.size(), 0);

		IBooleanResult booleanRes = (IBooleanResult) res;
		assertEquals(booleanRes.getValue(), Boolean.FALSE);

		res = interpreter.eval(new AndExpression(new BooleanTerminal(false), new BooleanTerminal(true)));
		assertEquals(stack.size(), 0);

		booleanRes = (IBooleanResult) res;
		assertEquals(booleanRes.getValue(), Boolean.FALSE);

		res = interpreter.eval(new AndExpression(new BooleanTerminal(true), new BooleanTerminal(true)));
		assertEquals(stack.size(), 0);

		booleanRes = (IBooleanResult) res;
		assertEquals(booleanRes.getValue(), Boolean.TRUE);

		res = interpreter.eval(new AndExpression(new BooleanTerminal(false), new BooleanTerminal(false)));
		assertEquals(stack.size(), 0);

		booleanRes = (IBooleanResult) res;
		assertEquals(booleanRes.getValue(), Boolean.FALSE);
	}

	@Test	//DONE
	public void testVisitAnyExpression() throws Exception {
		IAbstractQueryResult result = interpreter.eval(new ForAnyExpression(new StringTerminal("string"),
				new BooleanTerminal(true)));

		assertEquals(stack.size(), 0);
		IBooleanResult booleanResult = (IBooleanResult) InterpreterUtils.toSingleResult(result);
		assertEquals(booleanResult.getValue(), Boolean.TRUE);

		result = interpreter.eval(new ForAnyExpression(new StringTerminal("string"),
				new BooleanTerminal(false)));

		assertEquals(stack.size(), 0);
		booleanResult = (IBooleanResult) InterpreterUtils.toSingleResult(result);
		assertEquals(booleanResult.getValue(), Boolean.FALSE);
	}

	@Test	//TODO
	public void testVisitCloseByExpression() throws Exception {
	}

	@Test	//DONE
	public void testVisitCommaExpression() throws Exception {
		StringTerminal string1 = new StringTerminal("string1");
		StringTerminal string2 = new StringTerminal("string2");
		IAbstractQueryResult result = interpreter.eval(new CommaExpression(string1,  string2));

		assertEquals(stack.size(), 0);

		IBagResult bag = (IBagResult) result;
		assertEquals(bag.getElements().size(), 1);

		Iterator<ISingleResult> it = bag.getElements().iterator();
		IStructResult struct = (IStructResult) it.next();

		assertEquals(struct.elements().size(), 2);

		assertEquals(((IStringResult) struct.elements().get(0)).getValue(), "string1");
		assertEquals(((IStringResult) struct.elements().get(1)).getValue(), "string2");
	}

	@Test	//DONE
	public void testVisitDivideExpression() throws Exception {
		IAbstractQueryResult result = interpreter.eval(new DivideExpression(new IntegerTerminal(10), new DoubleTerminal(2.5)));

		assertEquals(stack.size(), 0);

		IIntegerResult intRes = (IIntegerResult) InterpreterUtils.toSingleResult(result);
		assertEquals(intRes.getValue().intValue(), 4);
	}

	@Test	//DONE
	public void testVisitDotExpression() throws Exception {
		IAbstractQueryResult result = interpreter.eval(new DotExpression(new StringTerminal("string1"), new StringTerminal("string2")));
		assertEquals(stack.size(), 0);

		ISingleResult singleResult = InterpreterUtils.toIterable(result).iterator().next();
		assertTrue(singleResult instanceof IStringResult);
		assertEquals(((IStringResult) singleResult).getValue(), "string2");
	}

	@Test	//DONE
	public void testVisitEqualsExpression() throws Exception {
		IAbstractQueryResult result = interpreter.eval(new EqualsExpression(new IntegerTerminal(10), new DoubleTerminal(10.0)));
		IBooleanResult booleanRes = (IBooleanResult)InterpreterUtils.toSingleResult(result);
		assertEquals(stack.size(), 0);
		assertEquals(booleanRes.getValue(), Boolean.TRUE);

		result = interpreter.eval(new EqualsExpression(new IntegerTerminal(10), new DoubleTerminal(10.1)));
		booleanRes = (IBooleanResult)InterpreterUtils.toSingleResult(result);
		assertEquals(stack.size(), 0);
		assertEquals(booleanRes.getValue(), Boolean.FALSE);
	}

	@Test	//DONE
	public void testVisitNotEqualsExpression() throws Exception {
		IAbstractQueryResult result = interpreter.eval(new NotEqualsExpression(new IntegerTerminal(10), new DoubleTerminal(10.0)));
		IBooleanResult booleanRes = (IBooleanResult)InterpreterUtils.toSingleResult(result);
		assertEquals(stack.size(), 0);
		assertEquals(booleanRes.getValue(), Boolean.FALSE);

		result = interpreter.eval(new NotEqualsExpression(new IntegerTerminal(10), new DoubleTerminal(10.1)));
		booleanRes = (IBooleanResult)InterpreterUtils.toSingleResult(result);
		assertEquals(stack.size(), 0);
		assertEquals(booleanRes.getValue(), Boolean.TRUE);
	}

	@Test	//DONE
	public void testVisitGreaterThanExpression() throws Exception {
		IAbstractQueryResult result = interpreter.eval(new GreaterThanExpression(new IntegerTerminal(10), new IntegerTerminal(5)));
		IBooleanResult booleanRes = (IBooleanResult)InterpreterUtils.toSingleResult(result);
		assertEquals(stack.size(), 0);
		assertEquals(booleanRes.getValue(), Boolean.TRUE);

		result = interpreter.eval(new GreaterThanExpression(new IntegerTerminal(10), new IntegerTerminal(10)));
		booleanRes = (IBooleanResult)InterpreterUtils.toSingleResult(result);
		assertEquals(stack.size(), 0);
		assertEquals(booleanRes.getValue(), Boolean.FALSE);

		result = interpreter.eval(new GreaterThanExpression(new IntegerTerminal(5), new IntegerTerminal(10)));
		booleanRes = (IBooleanResult)InterpreterUtils.toSingleResult(result);
		assertEquals(stack.size(), 0);
		assertEquals(booleanRes.getValue(), Boolean.FALSE);
	}

	@Test	//DONE
	public void testVisitGreaterOrEqualThanExpression() throws Exception {
		IAbstractQueryResult result = interpreter.eval(new GreaterOrEqualThanExpression(new IntegerTerminal(10), new IntegerTerminal(5)));
		IBooleanResult booleanRes = (IBooleanResult)InterpreterUtils.toSingleResult(result);
		assertEquals(stack.size(), 0);
		assertEquals(booleanRes.getValue(), Boolean.TRUE);

		result = interpreter.eval(new GreaterOrEqualThanExpression(new IntegerTerminal(10), new IntegerTerminal(10)));
		booleanRes = (IBooleanResult)InterpreterUtils.toSingleResult(result);
		assertEquals(stack.size(), 0);
		assertEquals(booleanRes.getValue(), Boolean.TRUE);

		result = interpreter.eval(new GreaterOrEqualThanExpression(new IntegerTerminal(5), new IntegerTerminal(10)));
		booleanRes = (IBooleanResult)InterpreterUtils.toSingleResult(result);
		assertEquals(stack.size(), 0);
		assertEquals(booleanRes.getValue(), Boolean.FALSE);
	}

	@Test	//DONE
	public void testVisitInExpression() throws Exception {
		IBagExpression bag1 = new BagExpression(
				new CommaExpression(
						new IntegerTerminal(1),
						new CommaExpression(
								new IntegerTerminal(2),
								new CommaExpression(
										new IntegerTerminal(3),
										new IntegerTerminal(4)))));

		IBagExpression bag2 = new BagExpression(
				new CommaExpression(
						new IntegerTerminal(3),
						new CommaExpression(
								new IntegerTerminal(4),
								new CommaExpression(
										new IntegerTerminal(5),
										new IntegerTerminal(6)))));

		IAbstractQueryResult res = interpreter.eval(new IntersectExpression(bag1, bag2));
		assertEquals(stack.size(), 0);

		IBagResult bag = (IBagResult) res;
		assertEquals(bag.getElements().size(), 2);

		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult single : bag.getElements()) {
			ints.add(((IIntegerResult)single).getValue());
		}

		assertTrue(ints.contains(3));
		assertTrue(ints.contains(4));
	}

	@Test	//DONE
	public void testVisitIntersectExpression() throws Exception {
		IBagExpression bag1 = new BagExpression(
				new CommaExpression(
						new IntegerTerminal(1),
						new CommaExpression(
								new IntegerTerminal(2),
								new CommaExpression(
										new IntegerTerminal(3),
										new IntegerTerminal(4)))));

		IBagExpression bag2 = new BagExpression(
				new CommaExpression(
						new IntegerTerminal(3),
						new CommaExpression(
								new IntegerTerminal(4),
								new CommaExpression(
										new IntegerTerminal(5),
										new IntegerTerminal(6)))));

		IAbstractQueryResult res = interpreter.eval(new IntersectExpression(bag1, bag2));
		assertEquals(stack.size(), 0);

		IBagResult bag = (IBagResult) res;
		assertEquals(bag.getElements().size(), 2);

		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult single : bag.getElements()) {
			ints.add(((IIntegerResult)single).getValue());
		}

		assertTrue(ints.contains(3));
		assertTrue(ints.contains(4));
	}

	@Test	//DONE
	public void testVisitJoinExpression() throws Exception {
		IBagExpression bag1 = new BagExpression(new CommaExpression(new IntegerTerminal(1), new IntegerTerminal(2)));
		IBagExpression bag2 = new BagExpression(new CommaExpression(new StringTerminal("a"), new StringTerminal("b")));

		IAbstractQueryResult res = interpreter.eval(new JoinExpression(bag1,  bag2));
		assertEquals(stack.size(), 0);

		assertTrue(res instanceof IBagResult);

		Set<String> expected = Sets.newHashSet();
		for(ISingleResult struct : toIterable(res)) {
			assertTrue(struct instanceof  IStructResult);
			StringBuilder sb = new StringBuilder();
			for(ISingleResult single : ((IStructResult) struct).elements()) {
				assertTrue(single instanceof ISimpleResult);
				sb.append(((ISimpleResult) single).getValue());
			}
			expected.add(sb.toString());
		}

		assertEquals(expected.size(), 4);
		assertTrue(expected.contains("1a"));
		assertTrue(expected.contains("1b"));
		assertTrue(expected.contains("2a"));
		assertTrue(expected.contains("2b"));
	}

	@Test	//DONE
	public void testVisitLessOrEqualThanExpression() throws Exception {
		IAbstractQueryResult result = interpreter.eval(new LessOrEqualThanExpression(new IntegerTerminal(10), new IntegerTerminal(5)));
		IBooleanResult booleanRes = (IBooleanResult)InterpreterUtils.toSingleResult(result);
		assertEquals(stack.size(), 0);
		assertEquals(booleanRes.getValue(), Boolean.FALSE);

		result = interpreter.eval(new LessOrEqualThanExpression(new IntegerTerminal(10), new IntegerTerminal(10)));
		booleanRes = (IBooleanResult)InterpreterUtils.toSingleResult(result);
		assertEquals(stack.size(), 0);
		assertEquals(booleanRes.getValue(), Boolean.TRUE);

		result = interpreter.eval(new LessOrEqualThanExpression(new IntegerTerminal(5), new IntegerTerminal(10)));
		booleanRes = (IBooleanResult)InterpreterUtils.toSingleResult(result);
		assertEquals(stack.size(), 0);
		assertEquals(booleanRes.getValue(), Boolean.TRUE);
	}

	@Test	//DONE
	public void testVisitLessThanExpression() throws Exception {
		IAbstractQueryResult result = interpreter.eval(new LessThanExpression(new IntegerTerminal(10), new IntegerTerminal(5)));
		IBooleanResult booleanRes = (IBooleanResult)InterpreterUtils.toSingleResult(result);
		assertEquals(stack.size(), 0);
		assertEquals(booleanRes.getValue(), Boolean.FALSE);

		result = interpreter.eval(new LessThanExpression(new IntegerTerminal(10), new IntegerTerminal(10)));
		booleanRes = (IBooleanResult)InterpreterUtils.toSingleResult(result);
		assertEquals(stack.size(), 0);
		assertEquals(booleanRes.getValue(), Boolean.FALSE);

		result = interpreter.eval(new LessThanExpression(new IntegerTerminal(5), new IntegerTerminal(10)));
		booleanRes = (IBooleanResult)InterpreterUtils.toSingleResult(result);
		assertEquals(stack.size(), 0);
		assertEquals(booleanRes.getValue(), Boolean.TRUE);
	}

	@Test	//DONE
	public void testVisitMinusExpression() throws Exception {
		IAbstractQueryResult res = interpreter.eval(new MinusExpression(new IntegerTerminal(10), new IntegerTerminal(3)));
		assertEquals(stack.size(), 0);

		IIntegerResult intRes = (IIntegerResult) res;
		assertEquals(intRes.getValue().intValue(), 7);
	}

	@Test	//DONE
	public void testVisitMinusSetExpression() throws Exception {
		IBagExpression bag1 = new BagExpression(
				new CommaExpression(
						new IntegerTerminal(1),
						new CommaExpression(
								new IntegerTerminal(2),
								new CommaExpression(
										new IntegerTerminal(3),
										new IntegerTerminal(4)))));

		IBagExpression bag2 = new BagExpression(
				new CommaExpression(
						new IntegerTerminal(3),
						new CommaExpression(
								new IntegerTerminal(4),
								new CommaExpression(
										new IntegerTerminal(5),
										new IntegerTerminal(6)))));

		IAbstractQueryResult res = interpreter.eval(new MinusSetExpression(bag1, bag2));
		assertEquals(stack.size(), 0);

		IBagResult bag = (IBagResult) res;
		assertEquals(bag.getElements().size(), 2);

		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult single : bag.getElements()) {
			ints.add(((IIntegerResult)single).getValue());
		}

		assertTrue(ints.contains(1));
		assertTrue(ints.contains(2));
	}

	@Test	//DONE
	public void testVisitModuloExpression() throws Exception {
		IAbstractQueryResult res = interpreter.eval(new ModuloExpression(new IntegerTerminal(10), new IntegerTerminal(3)));
		assertEquals(stack.size(), 0);

		IIntegerResult intRes = (IIntegerResult) res;
		assertEquals(intRes.getValue().intValue(), 1);
	}

	@Test	//DONE
	public void testVisitMultiplyExpression() throws Exception {
		IAbstractQueryResult res = interpreter.eval(new MultiplyExpression(new IntegerTerminal(10), new DoubleTerminal(2.5)));
		assertEquals(stack.size(), 0);

		IIntegerResult intRes = (IIntegerResult) res;
		assertEquals(intRes.getValue().intValue(), 25);
	}

	@Test(expectedExceptions =  UnsupportedOperationException.class)	//TODO
	public void testVisitOrderByExpression() throws Exception {
		interpreter.eval(new OrderByExpression(new StringTerminal("a"), new StringTerminal("b")));
	}

	@Test	//DONE
	public void testVisitOrExpression() throws Exception {
		IAbstractQueryResult res = interpreter.eval(new OrExpression(new BooleanTerminal(true), new BooleanTerminal(false)));
		assertEquals(stack.size(), 0);

		IBooleanResult booleanRes = (IBooleanResult) res;
		assertEquals(booleanRes.getValue(), Boolean.TRUE);

		res = interpreter.eval(new OrExpression(new BooleanTerminal(false), new BooleanTerminal(true)));
		assertEquals(stack.size(), 0);

		booleanRes = (IBooleanResult) res;
		assertEquals(booleanRes.getValue(), Boolean.TRUE);

		res = interpreter.eval(new OrExpression(new BooleanTerminal(true), new BooleanTerminal(true)));
		assertEquals(stack.size(), 0);

		booleanRes = (IBooleanResult) res;
		assertEquals(booleanRes.getValue(), Boolean.TRUE);

		res = interpreter.eval(new OrExpression(new BooleanTerminal(false), new BooleanTerminal(false)));
		assertEquals(stack.size(), 0);

		booleanRes = (IBooleanResult) res;
		assertEquals(booleanRes.getValue(), Boolean.FALSE);
	}

	@Test	//DONE
	public void testVisitPlusExpression() throws Exception {
		IAbstractQueryResult res = interpreter.eval(new PlusExpression(new IntegerTerminal(10), new DoubleTerminal(2.5)));
		assertEquals(stack.size(), 0);

		IDoubleResult result = (IDoubleResult) res;
		assertEquals(result.getValue().doubleValue(), 12.5);
	}

	@Test	//DONE
	public void testVisitUnionExpression() throws Exception {
		IBagExpression bag1 = new BagExpression(
				new CommaExpression(
						new IntegerTerminal(1),
						new CommaExpression(
								new IntegerTerminal(2),
								new CommaExpression(
										new IntegerTerminal(3),
										new IntegerTerminal(4)))));

		IBagExpression bag2 = new BagExpression(
				new CommaExpression(
						new IntegerTerminal(3),
						new CommaExpression(
								new IntegerTerminal(4),
								new CommaExpression(
										new IntegerTerminal(5),
										new IntegerTerminal(6)))));

		IAbstractQueryResult res = interpreter.eval(new UnionExpression(bag1, bag2));
		assertEquals(stack.size(), 0);

		IBagResult bag = (IBagResult) res;
		assertEquals(bag.getElements().size(), 6);

		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult single : bag.getElements()) {
			ints.add(((IIntegerResult)single).getValue());
		}

		assertTrue(ints.contains(1));
		assertTrue(ints.contains(2));
		assertTrue(ints.contains(3));
		assertTrue(ints.contains(4));
		assertTrue(ints.contains(5));
		assertTrue(ints.contains(6));
	}

	@Test	//DONE
	public void testVisitWhereExpression() throws Exception {
		IBagExpression bag = new BagExpression(
				new CommaExpression(
						new IntegerTerminal(1),
						new CommaExpression(
								new IntegerTerminal(2),
								new CommaExpression(
										new IntegerTerminal(3),
										new IntegerTerminal(4)))));
		IAbstractQueryResult res = interpreter.eval(new WhereExpression(bag, new EqualsExpression(new IntegerTerminal(1), new IntegerTerminal(2))));
		assertEquals(stack.size(), 0);

		assertTrue(res instanceof IBagResult);
		assertFalse(InterpreterUtils.toIterable(res).iterator().hasNext());

		res = interpreter.eval(new WhereExpression(bag, new EqualsExpression(new IntegerTerminal(1), new IntegerTerminal(1))));
		assertEquals(stack.size(), 0);

		assertTrue(res instanceof IBagResult);
		assertEquals(Iterables.size(toIterable(res)), 4);
		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult single : toIterable(res)) {
			IIntegerResult singleInt = (IIntegerResult) single;
			ints.add(singleInt.getValue());
		}

		assertTrue(ints.contains(1));
		assertTrue(ints.contains(2));
		assertTrue(ints.contains(3));
		assertTrue(ints.contains(4));
	}

	@Test	//DONE
	public void testVisitXORExpression() throws Exception {
		IAbstractQueryResult res = interpreter.eval(new XORExpression(new BooleanTerminal(true), new BooleanTerminal(false)));
		assertEquals(stack.size(), 0);

		IBooleanResult booleanRes = (IBooleanResult) res;
		assertEquals(booleanRes.getValue(), Boolean.TRUE);

		res = interpreter.eval(new XORExpression(new BooleanTerminal(false), new BooleanTerminal(true)));
		assertEquals(stack.size(), 0);

		booleanRes = (IBooleanResult) res;
		assertEquals(booleanRes.getValue(), Boolean.TRUE);

		res = interpreter.eval(new XORExpression(new BooleanTerminal(true), new BooleanTerminal(true)));
		assertEquals(stack.size(), 0);

		booleanRes = (IBooleanResult) res;
		assertEquals(booleanRes.getValue(), Boolean.FALSE);

		res = interpreter.eval(new OrExpression(new BooleanTerminal(false), new BooleanTerminal(false)));
		assertEquals(stack.size(), 0);

		booleanRes = (IBooleanResult) res;
		assertEquals(booleanRes.getValue(), Boolean.FALSE);
	}

	@Test	//DONE
	public void testVisitCountExpression() throws Exception {
		IBagExpression bag = new BagExpression(
				new CommaExpression(
						new IntegerTerminal(1),
						new CommaExpression(
								new IntegerTerminal(2),
								new CommaExpression(
										new IntegerTerminal(3),
										new IntegerTerminal(4)))));

		IAbstractQueryResult res = interpreter.eval(new CountExpression(bag));
		assertEquals(stack.size(), 0);

		IIntegerResult result = (IIntegerResult) res;
		assertEquals(result.getValue().intValue(), 4);
	}

	@Test(expectedExceptions = UnsupportedOperationException.class)
	public void testVisitExistsExpression() throws Exception {
		interpreter.eval(new ExistsExpression(new StringTerminal("a")));
	}

	@Test	//DONE
	public void testVisitMaxExpression() throws Exception {
		IBagExpression bag = new BagExpression(
				new CommaExpression(
						new IntegerTerminal(1),
						new CommaExpression(
								new IntegerTerminal(2),
								new CommaExpression(
										new IntegerTerminal(3),
										new IntegerTerminal(4)))));

		IAbstractQueryResult res = interpreter.eval(new MaxExpression(bag));
		assertEquals(stack.size(), 0);

		IIntegerResult result = (IIntegerResult) res;
		assertEquals(result.getValue().intValue(), 4);
	}

	@Test	//DONE
	public void testVisitMinExpression() throws Exception {
		IBagExpression bag = new BagExpression(
				new CommaExpression(
						new IntegerTerminal(4),
						new CommaExpression(
								new IntegerTerminal(3),
								new CommaExpression(
										new IntegerTerminal(2),
										new IntegerTerminal(1)))));

		IAbstractQueryResult res = interpreter.eval(new MinExpression(bag));
		assertEquals(stack.size(), 0);

		IIntegerResult result = (IIntegerResult) res;
		assertEquals(result.getValue().intValue(), 1);
	}

	@Test	//DONE
	public void testVisitNotExpression() throws Exception {
		IBooleanResult result = (IBooleanResult) interpreter.eval(new NotExpression(new BooleanTerminal(false)));
		assertEquals(stack.size(), 0);

		assertEquals(result.getValue(), Boolean.TRUE);

		result = (IBooleanResult) interpreter.eval(new NotExpression(new BooleanTerminal(true)));
		assertEquals(stack.size(), 0);

		assertEquals(result.getValue(), Boolean.FALSE);
	}

	@Test	//DONE
	public void testVisitSumExpression() throws Exception {
		IBagExpression bag = new BagExpression(
				new CommaExpression(
						new IntegerTerminal(1),
						new CommaExpression(
								new IntegerTerminal(2),
								new CommaExpression(
										new IntegerTerminal(3),
										new IntegerTerminal(4)))));

		IAbstractQueryResult res = interpreter.eval(new SumExpression(bag));
		assertEquals(stack.size(), 0);

		IIntegerResult result = (IIntegerResult) res;
		assertEquals(result.getValue().intValue(), 10);
	}

	@Test	//DONE
	public void testVisitUniqueExpression() throws Exception {
		IBagExpression bag = new BagExpression(
				new CommaExpression(
						new IntegerTerminal(2),
						new CommaExpression(
								new IntegerTerminal(2),
								new CommaExpression(
										new IntegerTerminal(3),
										new IntegerTerminal(3)))));

		IAbstractQueryResult res = interpreter.eval(new UniqueExpression(bag));
		assertEquals(stack.size(), 0);

		IBagResult bagRes = (IBagResult) res;
		assertEquals(bagRes.getElements().size(), 2);

		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult integerResult : toIterable(bagRes)) {
			ints.add(((IIntegerResult)integerResult).getValue().intValue());
		}

		assertTrue(ints.contains(2));
		assertTrue(ints.contains(3));
	}

	@Test	//DONE
	public void testVisitAvgExpression() throws Exception {
		IBagExpression bag = new BagExpression(
				new CommaExpression(
						new IntegerTerminal(2),
						new CommaExpression(
								new IntegerTerminal(2),
								new CommaExpression(
										new IntegerTerminal(3),
										new IntegerTerminal(3)))));

		IAbstractQueryResult res = interpreter.eval(new AvgExpression(bag));
		assertEquals(stack.size(), 0);

		IDoubleResult result = (IDoubleResult) res;
		assertEquals(result.getValue().doubleValue(), 2.5);
	}
}
