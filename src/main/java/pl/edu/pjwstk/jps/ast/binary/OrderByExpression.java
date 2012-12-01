package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IOrderByExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class OrderByExpression extends AbstractBinaryExpression implements IOrderByExpression {

	public OrderByExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitOrderByExpression(this);
	}

}
