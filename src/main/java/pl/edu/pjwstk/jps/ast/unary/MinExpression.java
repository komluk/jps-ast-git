package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IMinExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class MinExpression extends AbstractUnaryExpression implements IMinExpression {

	public MinExpression(IExpression innerExpression) {
		super(innerExpression);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitMinExpression(this);
	}

}
