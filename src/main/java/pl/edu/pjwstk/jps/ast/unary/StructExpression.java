package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IStructExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class StructExpression extends AbstractUnaryExpression implements IStructExpression {

	public StructExpression(IExpression innerExpression) {
		super(innerExpression);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitStructExpression(this);
	}

}
