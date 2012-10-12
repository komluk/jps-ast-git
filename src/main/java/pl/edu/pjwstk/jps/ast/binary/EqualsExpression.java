package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IEqualsExpression;

public class EqualsExpression extends AbstractBinaryExpression implements IEqualsExpression {

	public EqualsExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}
