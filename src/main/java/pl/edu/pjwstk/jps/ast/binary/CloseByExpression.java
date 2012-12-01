package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.ICloseByExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class CloseByExpression extends AbstractBinaryExpression implements ICloseByExpression {
	public CloseByExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitCloseByExpression(this);
	}
}
