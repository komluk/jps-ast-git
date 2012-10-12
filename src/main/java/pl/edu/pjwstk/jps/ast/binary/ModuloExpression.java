package pl.edu.pjwstk.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IModuloExpression;

public class ModuloExpression extends AbstractBinaryExpression implements IModuloExpression {

	public ModuloExpression(IExpression left, IExpression right) {
		super(left, right);
	}

}
