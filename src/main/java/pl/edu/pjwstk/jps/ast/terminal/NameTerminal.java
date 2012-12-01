package pl.edu.pjwstk.jps.ast.terminal;

import edu.pjwstk.jps.ast.terminal.INameTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

@SuppressWarnings("rawtypes")
public class NameTerminal extends AbstractTerminal implements INameTerminal {
	@SuppressWarnings("unchecked")
	public NameTerminal(String value) {
		super(value);
	}

	@Override
	public String getName() {
		return getValue().toString();
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitNameTerminal(this);
	}
}
