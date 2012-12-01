package pl.edu.pjwstk.jps.ast.auxname;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.auxname.IGroupAsExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class GroupAsExpression extends AbstractAuxiliaryNameExpression implements IGroupAsExpression {

	public GroupAsExpression(String name, IExpression innerExpression) {
		super(name, innerExpression);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitGroupAsExpression(this);
	}
}
