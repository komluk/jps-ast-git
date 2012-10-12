package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IWhereExpression;

public class WhereExpression extends AbstractBinaryExpression implements IWhereExpression {
	public WhereExpression(IExpression left, IExpression right) {
		super(left, right);
	}
}
