package pl.edu.pjwstk.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IBagExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class BagExpression extends AbstractUnaryExpression implements IBagExpression {

	public BagExpression(IExpression innerExpression) {
		super(innerExpression);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitBagExpression(this);
	}

}
