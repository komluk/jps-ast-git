package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IExistsExpression;

public class ExistsExpression extends AbstractUnaryExpression implements IExistsExpression {

	public ExistsExpression(IExpression innerExpression) {
		super(innerExpression);
	}

}
