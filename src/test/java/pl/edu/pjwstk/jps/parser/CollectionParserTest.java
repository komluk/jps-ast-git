package pl.edu.pjwstk.jps.parser;

import com.google.common.collect.Sets;
import edu.pjwstk.jps.result.ISingleResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pjwstk.jps.ast.AbstractExpression;
import pl.edu.pjwstk.jps.result.BagResult;
import pl.edu.pjwstk.jps.result.IntegerResult;

import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * User: pawel
 * Date: 17.01.13
 * Time: 21:27
 */
@Test
public class CollectionParserTest extends  ParserTest {
	@BeforeMethod
	public void beforeMethod() {
		init();
	}

	public void bagExpressionTest() throws Exception {
		AbstractExpression expression = getExpression("bag(1,2,3)");
		BagResult bag = getResult(expression, BagResult.class);
		assertEquals(bag.getElements().size(), 3);
		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult res : bag.getElements()) {
			assertTrue(res instanceof IntegerResult);
			ints.add(((IntegerResult) res).getValue());
		}
		assertEquals(ints, Sets.newHashSet(1, 2, 3));
	}
}
