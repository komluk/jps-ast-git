package pl.edu.pjwstk.jps.parser;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pjwstk.jps.ast.AbstractExpression;
import pl.edu.pjwstk.jps.ast.binary.PlusExpression;
import pl.edu.pjwstk.jps.result.DoubleResult;
import pl.edu.pjwstk.jps.result.IntegerResult;

import static org.testng.Assert.assertEquals;

/**
 * User: pawel
 * Date: 12.01.13
 * Time: 17:56
 */
@Test
public class ArithmeticParserTest extends ParserTest {
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		init();
	}

	public void plusExpressionTest() throws Exception {
		PlusExpression expression = getExpression("2+3.5", PlusExpression.class);
		DoubleResult res = getResult(expression, DoubleResult.class);
		assertEquals(res.getValue(), 5.5);
	}

	public void minusExpressionTest() throws Exception {
		AbstractExpression expression = getExpression("2-3");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(-1));
	}

	public void multiplyExpressionTest() throws Exception {
		AbstractExpression expression = getExpression("2*3");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(6));
	}

	public void bracketExpressionTest() throws Exception {
		AbstractExpression expression = getExpression("(2+2)*5.5");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(22));
	}

	public void moduloExpressionTest() throws Exception {
		AbstractExpression expression = getExpression("5%2");
		IntegerResult res = getResult(expression, IntegerResult.class);
		assertEquals(res.getValue(), Integer.valueOf(1));
	}
}
