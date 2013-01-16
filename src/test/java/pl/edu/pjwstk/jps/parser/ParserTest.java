package pl.edu.pjwstk.jps.parser;

import org.testng.annotations.Test;
import pl.edu.pjwstk.jps.ast.AbstractExpression;
import pl.edu.pjwstk.jps.ast.datastore.SBAStore;
import pl.edu.pjwstk.jps.interpreter.Interpreter;
import pl.edu.pjwstk.jps.result.AbstractQueryResult;

/**
 * User: pawel
 * Date: 16.01.13
 * Time: 19:41
 */
@Test
abstract class ParserTest {
	protected JpsParser parser;
	protected Interpreter interpreter;

	protected void init() {
		interpreter = new Interpreter(SBAStore.getInstance());
	}

	protected  <T extends AbstractQueryResult> T getResult(AbstractExpression expression, Class<T> expectedClass) {
		return (T) interpreter.eval(expression);
	}

	protected AbstractExpression getExpression(String expression) throws Exception {
		parser = new JpsParser(expression);
		parser.user_init();
		parser.parse();
		return parser.RESULT;
	}

	protected  <T extends AbstractExpression> T getExpression(String expression, Class<T> expectedClass) throws Exception {
		return (T)getExpression(expression);
	}
}
