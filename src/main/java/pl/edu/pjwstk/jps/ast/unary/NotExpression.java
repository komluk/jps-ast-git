package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.INotExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class NotExpression extends AbstractUnaryExpression implements INotExpression {

	public NotExpression(IExpression innerExpression) {
		super(innerExpression);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitNotExpression(this);
	}

}
