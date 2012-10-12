package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.INotExpression;

public class NotExpression extends AbstractUnaryExpression implements INotExpression {

	public NotExpression(IExpression innerExpression) {
		super(innerExpression);
	}

}
