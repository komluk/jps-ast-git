package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IEqualsExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class EqualsExpression extends AbstractBinaryExpression implements IEqualsExpression {

	public EqualsExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitEqualsExpression(this);
	}
}
