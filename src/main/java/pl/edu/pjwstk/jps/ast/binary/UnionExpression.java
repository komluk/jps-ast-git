package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IUnionExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class UnionExpression extends AbstractBinaryExpression implements IUnionExpression{

	public UnionExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitUnionExpression(this);
	}

}
