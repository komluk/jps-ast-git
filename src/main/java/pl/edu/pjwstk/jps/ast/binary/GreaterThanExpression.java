package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IGreaterThanExpression;

public class GreaterThanExpression extends AbstractBinaryExpression implements IGreaterThanExpression {

	public GreaterThanExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}