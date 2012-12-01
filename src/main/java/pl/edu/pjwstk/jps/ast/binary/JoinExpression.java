package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IJoinExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class JoinExpression extends AbstractBinaryExpression implements IJoinExpression {

	public JoinExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitJoinExpression(this);
	}

}
