package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IMultiplyExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class MultiplyExpression extends AbstractBinaryExpression implements IMultiplyExpression {

	public MultiplyExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitMultiplyExpression(this);
	}

}
