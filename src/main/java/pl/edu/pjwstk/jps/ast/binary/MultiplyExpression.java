package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IMultiplyExpression;

public class MultiplyExpression extends AbstractBinaryExpression implements IMultiplyExpression {

	public MultiplyExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}
