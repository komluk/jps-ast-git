package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;
import pl.edu.pjwstk.jps.interpreter.Interpreter;

/**
 * User: pawel
 * Date: 16.01.13
 * Time: 20:56
 */
public class MoreOrEqualThanExpression extends AbstractBinaryExpression {
	public MoreOrEqualThanExpression(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public void accept(ASTVisitor astVisitor) {
		if(astVisitor instanceof Interpreter) {
			((Interpreter)astVisitor).visitMoreOrEqualExpression(this);
		}
	}
}
