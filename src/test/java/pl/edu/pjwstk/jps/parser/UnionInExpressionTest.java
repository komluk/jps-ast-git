package pl.edu.pjwstk.jps.parser;

import com.google.common.collect.Sets;
import edu.pjwstk.jps.result.ISingleResult;
import org.testng.annotations.BeforeMethod;
import pl.edu.pjwstk.jps.ast.AbstractExpression;
import pl.edu.pjwstk.jps.ast.datastore.SBAStore;
import pl.edu.pjwstk.jps.result.BagResult;
import pl.edu.pjwstk.jps.result.BooleanResult;
import pl.edu.pjwstk.jps.result.IntegerResult;

import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * User: pawel
 * Date: 19.01.13
 * Time: 13:49
 */
public class UnionInExpressionTest extends ParserTest {
	@BeforeMethod
	public void beforeMethod() {
		init();
		String path = UnionInExpressionTest.class.getResource("/envs.xml").getPath();
		SBAStore.getInstance().loadXML(path);
		interpreter.getEnvs().init(SBAStore.getInstance().getEntryOID(), SBAStore.getInstance());
	}

	public void testUnionExpression() throws Exception {
		AbstractExpression expression = getExpression("bag(1,2,3) union bag(4,5,6)");
		BagResult res = getResult(expression, BagResult.class);
		Set<Integer> ints = Sets.newHashSet();
		for(ISingleResult single : res.getElements()) {
			assertTrue(single instanceof IntegerResult);
			ints.add(((IntegerResult) single).getValue());
		}
		assertEquals(ints, Sets.newHashSet(1,2,3,4,5,6));
	}

	public void testInExpression() throws Exception {
		AbstractExpression expression = getExpression("bag(1,2,3) in bag(1,2)");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}


	public void testInExpression2() throws Exception {
		AbstractExpression expression = getExpression("emp.fName in bag(\"Anna\", \"Maciej\")");
		BooleanResult res = getResult(expression, BooleanResult.class);
		assertTrue(res.getValue());
	}
}
