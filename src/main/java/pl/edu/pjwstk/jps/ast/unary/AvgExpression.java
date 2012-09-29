package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IAvgExpression;

public class AvgExpression extends AbstractUnaryExpression implements IAvgExpression {

	public AvgExpression(IExpression innerExpression) {
		super(innerExpression);
	}

}
