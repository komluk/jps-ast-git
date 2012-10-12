package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IForAnyExpression;

public class ForAnyExpression extends AbstractBinaryExpression implements IForAnyExpression {

	public ForAnyExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}
