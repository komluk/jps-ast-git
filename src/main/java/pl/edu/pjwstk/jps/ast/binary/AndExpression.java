package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IAndExpression;

public class AndExpression extends AbstractBinaryExpression implements IAndExpression {
	public AndExpression(IExpression left, IExpression right) {
		super(left, right);
	}
}
