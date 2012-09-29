package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IBagExpression;

public class BagExpression extends AbstractUnaryExpression implements IBagExpression {

	public BagExpression(IExpression innerExpression) {
		super(innerExpression);
	}

}
