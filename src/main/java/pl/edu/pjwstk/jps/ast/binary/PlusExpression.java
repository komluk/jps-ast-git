package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IPlusExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class PlusExpression extends AbstractBinaryExpression implements IPlusExpression {

	public PlusExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitPlusExpression(this);
	}

}
