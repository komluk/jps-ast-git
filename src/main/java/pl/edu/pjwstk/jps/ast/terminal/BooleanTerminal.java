package pl.edu.pjwstk.jps.ast.terminal;

import edu.pjwstk.jps.ast.terminal.IBooleanTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class BooleanTerminal extends AbstractTerminal<Boolean> implements IBooleanTerminal {

	public BooleanTerminal(Boolean value) {
		super(value);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitBooleanTerminal(this);
	}

}
