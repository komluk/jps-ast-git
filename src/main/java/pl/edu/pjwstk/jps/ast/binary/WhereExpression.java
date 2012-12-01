package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IWhereExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class WhereExpression extends AbstractBinaryExpression implements IWhereExpression {
	public WhereExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitWhereExpression(this);
	}
}
