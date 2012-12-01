package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.ICountExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class CountExpression extends AbstractUnaryExpression implements ICountExpression {

	public CountExpression(IExpression innerExpression) {
		super(innerExpression);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitCountExpression(this);
	}

}
