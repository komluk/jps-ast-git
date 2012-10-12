package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IOrExpression;

public class OrExpression extends AbstractBinaryExpression implements IOrExpression {

	public OrExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}
