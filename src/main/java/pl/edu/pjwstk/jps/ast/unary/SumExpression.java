package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.ISumExpression;

public class SumExpression extends AbstractUnaryExpression implements ISumExpression {

	public SumExpression(IExpression innerExpression) {
		super(innerExpression);
	}

}
