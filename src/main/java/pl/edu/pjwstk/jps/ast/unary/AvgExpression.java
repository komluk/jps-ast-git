package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IAvgExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class AvgExpression extends AbstractUnaryExpression implements IAvgExpression {

	public AvgExpression(IExpression innerExpression) {
		super(innerExpression);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitAvgExpression(this);
	}

}
