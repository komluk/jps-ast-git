package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IMinusExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class MinusExpression extends AbstractBinaryExpression implements IMinusExpression{

	public MinusExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitMinusExpression(this);
	}
}
