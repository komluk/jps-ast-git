package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IExistsExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class ExistsExpression extends AbstractUnaryExpression implements IExistsExpression {

	public ExistsExpression(IExpression innerExpression) {
		super(innerExpression);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitExistsExpression(this);
	}

}
