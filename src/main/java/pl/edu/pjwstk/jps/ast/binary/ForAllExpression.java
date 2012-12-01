package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IForAllExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class ForAllExpression extends AbstractBinaryExpression implements IForAllExpression {

	public ForAllExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitAllExpression(this);
	}
}
