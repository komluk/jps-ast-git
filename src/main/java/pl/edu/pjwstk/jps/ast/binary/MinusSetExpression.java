package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IMinusSetExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

/**
 * User: pawel
 * Date: 01.01.13
 * Time: 21:22
 */
public class MinusSetExpression extends AbstractBinaryExpression implements IMinusSetExpression {
	public MinusSetExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor astVisitor) {
		astVisitor.visitMinusSetExpression(this);
	}
}
