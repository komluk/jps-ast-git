package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IIntersectExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class IntersectExpression extends AbstractBinaryExpression implements IIntersectExpression {

	public IntersectExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitIntersectExpression(this);
	}

}
