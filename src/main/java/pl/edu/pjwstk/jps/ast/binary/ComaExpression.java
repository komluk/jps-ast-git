package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.ICommaExpression;

public class ComaExpression extends AbstractBinaryExpression implements ICommaExpression {

	public ComaExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}
