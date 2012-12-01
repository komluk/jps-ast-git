package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.ICommaExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class CommaExpression extends AbstractBinaryExpression implements ICommaExpression {

	public CommaExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitCommaExpression(this);
	}
}
