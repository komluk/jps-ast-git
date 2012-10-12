package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.ILessOrEqualThanExpression;

public class LessOrEqualThanExpression extends AbstractBinaryExpression implements ILessOrEqualThanExpression {

	public LessOrEqualThanExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}
