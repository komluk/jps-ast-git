package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IOrExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class OrExpression extends AbstractBinaryExpression implements IOrExpression {

	public OrExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitOrExpression(this);
	}

}
