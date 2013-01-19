package pl.edu.pjwstk.jps.ast.binary;

import pl.edu.pjwstk.jps.ast.AbstractExpression;
import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IBinaryExpression;

abstract class AbstractBinaryExpression extends AbstractExpression implements IBinaryExpression {
	private final IExpression leftExpression;
	private final IExpression rightExpression;
	
	public AbstractBinaryExpression(IExpression left, IExpression right) {
		this.leftExpression = left;
		this.rightExpression = right;
	}
	
	@Override
	public IExpression getLeftExpression() {
		return leftExpression;
	}

	@Override
	public IExpression getRightExpression() {
		return rightExpression;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" +
				"leftExpression=" + leftExpression +
				", rightExpression=" + rightExpression +
				'}';
	}
}
