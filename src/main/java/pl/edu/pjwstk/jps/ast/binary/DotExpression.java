package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IDotExpression;

public class DotExpression extends AbstractBinaryExpression implements IDotExpression {

	public DotExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}
