package pl.edu.pjwstk.jps.parser;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pjwstk.jps.ast.AbstractExpression;
import pl.edu.pjwstk.jps.result.DoubleResult;
import pl.edu.pjwstk.jps.result.IntegerResult;

import static org.testng.Assert.assertEquals;

/**
 * User: pawel
 * Date: 16.01.13
 * Time: 21:15
 */
@Test
public class AggregationFunctionTest extends ParserTest {
	@BeforeMethod
	public void beforeMethod() {
		init();
	}

	public void sumExpressionTest() throws Exception {
		AbstractExpression expression = getExpression("sum(1,2,3)");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(6));

		expression = getExpression("sum(1,2,(0-3))");
		res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(0));
	}

	public void countExpressionTest() throws Exception {
		AbstractExpression expression = getExpression("count(1,2,3)");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(3));

		expression = getExpression("count(1,2,(0-3))");
		res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(3));
	}

	public void avgExpressionTest() throws Exception {
		AbstractExpression expression = getExpression("avg(1,2,3)");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(2));

		expression = getExpression("avg(1,2,(0-3))");
		res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(0));

		expression = getExpression("avg(2,3)");
		DoubleResult dRes = getResult(expression, DoubleResult.class);
		assertEquals(dRes.getValue(), Double.valueOf(2.5), 0.001);
	}

	public void minExpressionTest() throws Exception {
		AbstractExpression expression = getExpression("min(1,2,3)");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(1));

		expression = getExpression("min(1,2,(0-3))");
		res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(-3));
	}

	public void maxExpressionTest() throws Exception {
		AbstractExpression expression = getExpression("max(1,2,3)");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(3));

		expression = getExpression("max(1,2,(0-3))");
		res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(2));
	}
}
