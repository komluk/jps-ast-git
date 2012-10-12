package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IGreaterOrEqualThanExpression;

public class GreaterOrEqualThanExpression extends AbstractBinaryExpression implements IGreaterOrEqualThanExpression{

	public GreaterOrEqualThanExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}
