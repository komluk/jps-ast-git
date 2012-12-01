package pl.edu.pjwstk.jps.ast.terminal;

import edu.pjwstk.jps.ast.terminal.IIntegerTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class IntegerTerminal extends AbstractTerminal<Integer> implements IIntegerTerminal {

	public IntegerTerminal(Integer value) {
		super(value);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitIntegerTerminal(this);
	}

}
