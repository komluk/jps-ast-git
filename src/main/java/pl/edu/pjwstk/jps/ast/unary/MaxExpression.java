package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IMaxExpression;

public class MaxExpression extends AbstractUnaryExpression implements IMaxExpression {

	public MaxExpression(IExpression innerExpression) {
		super(innerExpression);
	}

}
