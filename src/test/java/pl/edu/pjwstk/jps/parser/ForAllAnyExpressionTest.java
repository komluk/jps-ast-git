package pl.edu.pjwstk.jps.parser;

import org.testng.annotations.BeforeMethod;
import pl.edu.pjwstk.jps.ast.AbstractExpression;
import pl.edu.pjwstk.jps.result.AbstractQueryResult;
import pl.edu.pjwstk.jps.result.BooleanResult;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * User: pawel
 * Date: 19.01.13
 * Time: 13:56
 */
public class ForAllAnyExpressionTest extends ParserTest {
	@BeforeMethod
	public void beforeMethod() {
		init();
	}

	public void forAllPositiveTest() throws Exception {
		AbstractExpression expression = getExpression("all bag(1, 2, 3) true");
		AbstractQueryResult res = getResult(expression, AbstractQueryResult.class);
		assertTrue(res instanceof BooleanResult);
		assertTrue(((BooleanResult) res).getValue());
	}

	public void forAllNegativeTest() throws Exception {
		AbstractExpression expression = getExpression("all bag(1, 2, 3) false");
		AbstractQueryResult res = getResult(expression, AbstractQueryResult.class);
		assertTrue(res instanceof BooleanResult);
		assertFalse(((BooleanResult) res).getValue());
	}

	public void forAnyPositiveTest() throws Exception {
		AbstractExpression expression = getExpression("any bag(1, 2, 3) true");
		AbstractQueryResult res = getResult(expression, AbstractQueryResult.class);
		assertTrue(res instanceof BooleanResult);
		assertTrue(((BooleanResult) res).getValue());
	}

	public void forAnyNegativeTest() throws Exception {
		AbstractExpression expression = getExpression("any bag(1, 2, 3) false");
		AbstractQueryResult res = getResult(expression, AbstractQueryResult.class);
		assertTrue(res instanceof BooleanResult);
		assertFalse(((BooleanResult) res).getValue());
	}
}
