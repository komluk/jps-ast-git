package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IUniqueExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class UniqueExpression extends AbstractUnaryExpression implements IUniqueExpression {

	public UniqueExpression(IExpression innerExpression) {
		super(innerExpression);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitUniqueExpression(this);
	}

}
