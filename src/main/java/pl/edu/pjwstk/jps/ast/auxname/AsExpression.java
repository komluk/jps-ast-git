package pl.edu.pjwstk.jps.ast.auxname;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.auxname.IAsExpression;

public class AsExpression extends AbstractAuxiliaryNameExpression implements IAsExpression {

	public AsExpression(String name, IExpression innerExpression) {
		super(name, innerExpression);
	}

}
