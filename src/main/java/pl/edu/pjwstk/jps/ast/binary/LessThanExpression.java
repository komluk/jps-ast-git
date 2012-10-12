package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.ILessThanExpression;

public class LessThanExpression extends AbstractBinaryExpression implements ILessThanExpression {

	public LessThanExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}
