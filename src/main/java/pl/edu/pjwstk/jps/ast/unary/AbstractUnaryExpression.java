package pl.edu.pjwstk.jps.ast.unary;

import pl.edu.pjwstk.jps.ast.AbstractExpression;
import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IUnaryExpression;

public class AbstractUnaryExpression extends AbstractExpression implements IUnaryExpression {
	private final IExpression innerExpression;
	
	public AbstractUnaryExpression(IExpression innerExpression) {
		this.innerExpression = innerExpression;
	}
	
	@Override
	public IExpression getInnerExpression() {
		return innerExpression;
	}

}
