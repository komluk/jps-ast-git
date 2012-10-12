package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.INotEqualsExpression;

public class NotEqualsExpression extends AbstractBinaryExpression implements INotEqualsExpression {

	public NotEqualsExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}
