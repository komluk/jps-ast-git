package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IInExpression;

public class InExpression extends AbstractBinaryExpression implements IInExpression {

	public InExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}
