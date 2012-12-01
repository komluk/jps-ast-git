package pl.edu.pjwstk.jps.ast.auxname;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.auxname.IAsExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class AsExpression extends AbstractAuxiliaryNameExpression implements IAsExpression {

	public AsExpression(String name, IExpression innerExpression) {
		super(name, innerExpression);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitAsExpression(this);
	}
}
