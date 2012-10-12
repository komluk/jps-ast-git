package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.ICountExpression;

public class CountExpression extends AbstractUnaryExpression implements ICountExpression {

	public CountExpression(IExpression innerExpression) {
		super(innerExpression);
	}

}
