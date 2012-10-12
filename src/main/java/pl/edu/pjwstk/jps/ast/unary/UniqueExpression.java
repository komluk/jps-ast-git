package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IUniqueExpression;

public class UniqueExpression extends AbstractUnaryExpression implements IUniqueExpression {

	public UniqueExpression(IExpression innerExpression) {
		super(innerExpression);
	}

}
