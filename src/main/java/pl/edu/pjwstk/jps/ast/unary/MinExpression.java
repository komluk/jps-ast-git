package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IMinExpression;

public class MinExpression extends AbstractUnaryExpression implements IMinExpression {

	public MinExpression(IExpression innerExpression) {
		super(innerExpression);
	}

}
