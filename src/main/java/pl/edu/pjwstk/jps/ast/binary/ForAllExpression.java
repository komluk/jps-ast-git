package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IForAllExpression;

public class ForAllExpression extends AbstractBinaryExpression implements IForAllExpression {

	public ForAllExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}
