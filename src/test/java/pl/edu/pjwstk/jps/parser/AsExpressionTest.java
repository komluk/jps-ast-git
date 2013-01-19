package pl.edu.pjwstk.jps.parser;

import com.google.common.collect.Sets;
import edu.pjwstk.jps.result.ISingleResult;
import org.testng.annotations.BeforeMethod;
import pl.edu.pjwstk.jps.ast.AbstractExpression;
import pl.edu.pjwstk.jps.result.*;

import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * User: pawel
 * Date: 18.01.13
 * Time: 20:31
 */
public class AsExpressionTest extends ParserTest {
	@BeforeMethod
	public void beforeMethod() {
		init();
	}

	public void testAsExpression() throws Exception {
		AbstractExpression expression = getExpression("bag(1,2,3) as liczba");
		BagResult res = getResult(expression, BagResult.class);
		System.out.println(res);

		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult single : res.getElements()) {
			assertTrue(single instanceof BinderResult);
			BinderResult binder = (BinderResult) single;
			assertEquals(binder.getName(), "liczba");


			assertTrue(binder.getValue() instanceof IntegerResult);
			IntegerResult intRes = (IntegerResult) binder.getValue();
			ints.add(intRes.getValue());
		}

		assertEquals(ints, Sets.newHashSet(1,2,3));
	}

	public void testGroupAsExpression() throws Exception {
		AbstractExpression expression = getExpression("bag(1,2,3) groupas liczba");
		BinderResult res = getResult(expression, BinderResult.class);
		assertEquals(res.getName(), "liczba");
		assertTrue(res.getValue() instanceof BagResult);
		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult single : ((BagResult)res.getValue()).getElements()) {
			assertTrue(single instanceof IntegerResult);
			IntegerResult intRes = (IntegerResult) single;
			ints.add(intRes.getValue());
		}
		assertEquals(ints, Sets.newHashSet(1,2,3));
	}
}
