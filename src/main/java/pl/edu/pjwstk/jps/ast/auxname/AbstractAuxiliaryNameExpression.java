package pl.edu.pjwstk.jps.ast.auxname;

import pl.edu.pjwstk.jps.ast.AbstractExpression;
import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.auxname.IAuxiliaryNameExpression;

abstract class AbstractAuxiliaryNameExpression extends AbstractExpression implements IAuxiliaryNameExpression {
	private final String name;
	private final IExpression innerExpression;
	
	public AbstractAuxiliaryNameExpression(String name, IExpression innerExpression) {
		super();
		this.name = name;
		this.innerExpression = innerExpression;
	}

	@Override
	public String getAuxiliaryName() {
		return name;
	}

	@Override
	public IExpression getInnerExpression() {
		return innerExpression;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" +
				"name='" + name + '\'' +
				", innerExpression=" + innerExpression +
				'}';
	}
}
