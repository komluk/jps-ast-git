package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IStructExpression;

public class StructExpression extends AbstractUnaryExpression implements IStructExpression {

	public StructExpression(IExpression innerExpression) {
		super(innerExpression);
	}

}
