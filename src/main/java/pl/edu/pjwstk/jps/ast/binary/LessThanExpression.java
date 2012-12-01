package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.ILessThanExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class LessThanExpression extends AbstractBinaryExpression implements ILessThanExpression {

	public LessThanExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitLessThanExpression(this);
	}

}
