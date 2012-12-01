package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IGreaterThanExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class GreaterThanExpression extends AbstractBinaryExpression implements IGreaterThanExpression {

	public GreaterThanExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitGreaterThanExpression(this);
	}

}
