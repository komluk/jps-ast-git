package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IDivideExpression;

public class DivideExpression extends AbstractBinaryExpression implements IDivideExpression{

	public DivideExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}
