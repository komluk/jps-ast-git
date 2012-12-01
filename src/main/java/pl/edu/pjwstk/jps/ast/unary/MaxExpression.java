package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IMaxExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class MaxExpression extends AbstractUnaryExpression implements IMaxExpression {

	public MaxExpression(IExpression innerExpression) {
		super(innerExpression);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitMaxExpression(this);
	}

}
