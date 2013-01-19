package pl.edu.pjwstk.jps.parser;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pjwstk.jps.ast.AbstractExpression;
import pl.edu.pjwstk.jps.result.AbstractQueryResult;
import pl.edu.pjwstk.jps.result.BooleanResult;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * User: pawel
 * Date: 16.01.13
 * Time: 19:41
 */
@Test
public class LogicalParserTest extends ParserTest {
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		init();
	}

	public void andExpressionTest() throws Exception {
		for(String and : new String[] { "and", "&", "&&" }) {
			AbstractExpression expression = getExpression("true " + and + " false");
			BooleanResult res = getResult(expression, BooleanResult.class);
			assertFalse(res.getValue());

			expression = getExpression("false " + and + " true");
			res = getResult(expression, BooleanResult.class);
			assertFalse(res.getValue());

			expression = getExpression("true " + and + " true");
			res = getResult(expression, BooleanResult.class);
			assertTrue(res.getValue());
		}
	}

	public void orExpressionTest() throws Exception {
		for(String or : new String[]{"or", "||", "|"}) {
			AbstractExpression expression = getExpression("true " + or + " false");
			BooleanResult res = getResult(expression, BooleanResult.class);
			assertTrue(res.getValue());

			expression = getExpression("false " + or + " true");
			res = getResult(expression, BooleanResult.class);
			assertTrue(res.getValue());

			expression = getExpression("true " + or + " true");
			res = getResult(expression, BooleanResult.class);
			assertTrue(res.getValue());

			expression = getExpression("false " + or + " false");
			res = getResult(expression, BooleanResult.class);
			assertFalse(res.getValue());
		}
	}

	public void xorExpressionTest() throws Exception {
		AbstractExpression expression = getExpression("true xor false");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());

		expression = getExpression("false xor true");
		res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());

		expression = getExpression("true xor true");
		res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());

		expression = getExpression("false xor false");
		res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());
	}

	public void notExpressionTest() throws Exception {
		for(String not : new String[] { "!", "not" }) {
			AbstractExpression expression = getExpression(not + " true");
			BooleanResult res = getResult(expression, BooleanResult.class);
			assertFalse(res.getValue());

			expression = getExpression(not + " false");
			res = getResult(expression, BooleanResult.class);
			assertTrue(res.getValue());
		}
	}

	public void equalsExpressionTest() throws Exception {
		for(String eq : new String[] { "eq", "==", "=" }) {
			AbstractExpression expression = getExpression("1.22 " + eq + " 1.22");
			BooleanResult res = getResult(expression, BooleanResult.class);
			assertTrue(res.getValue());

			expression = getExpression("1.23 " + eq + " 1.22");
			res = getResult(expression, BooleanResult.class);
			assertFalse(res.getValue());
		}
	}

	public void notEqualsExpressionTest() throws Exception {
		for(String eq : new String[] { "!=" }) {
			AbstractExpression expression = getExpression("1.22 " + eq + " 1.22");
			BooleanResult res = getResult(expression, BooleanResult.class);
			assertFalse(res.getValue());

			expression = getExpression("1.23 " + eq + " 1.22");
			res = getResult(expression, BooleanResult.class);
			assertTrue(res.getValue());
		}
	}

	public void lessThanExpressionTest() throws Exception {
		AbstractExpression expression = getExpression("1.22 < 1.22");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());

		expression = getExpression("1.23 < 1.22");
		res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());

		expression = getExpression("1.21 < 1.22");
		res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void lessOrEqualExpressionTest() throws Exception {
		AbstractExpression expression = getExpression("1.22 <= 1.22");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());

		expression = getExpression("1.23 <= 1.22");
		res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());

		expression = getExpression("1.21 <= 1.22");
		res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}

	public void moreThanExpressionTest() throws Exception {
		AbstractExpression expression = getExpression("1.22 > 1.22");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());

		expression = getExpression("1.23 > 1.22");
		res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());

		expression = getExpression("1.21 > 1.22");
		res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());
	}

	public void moreOrEqualExpressionTest() throws Exception {
		AbstractExpression expression = getExpression("1.22 >= 1.22");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());

		expression = getExpression("1.23 >= 1.22");
		res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());

		expression = getExpression("1.21 >= 1.22");
		res = getResult(expression, BooleanResult.class);
		assertFalse(res.getValue());
	}
}
