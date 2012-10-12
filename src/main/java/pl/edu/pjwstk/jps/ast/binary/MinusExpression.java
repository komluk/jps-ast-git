package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IMinusExpression;

public class MinusExpression extends AbstractBinaryExpression implements IMinusExpression{

	public MinusExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}
