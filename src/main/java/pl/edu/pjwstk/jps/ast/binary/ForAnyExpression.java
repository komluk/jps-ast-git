package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IForAnyExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class ForAnyExpression extends AbstractBinaryExpression implements IForAnyExpression {

	public ForAnyExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitAnyExpression(this);
	}
}
