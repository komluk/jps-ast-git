package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IOrderByExpression;

public class OrderByExpression extends AbstractBinaryExpression implements IOrderByExpression {

	public OrderByExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}
