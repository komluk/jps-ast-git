package pl.edu.pjwstk.jps.ast.terminal;

import edu.pjwstk.jps.ast.terminal.IBooleanTerminal;

public class BooleanTerminal extends AbstractTerminal<Boolean> implements IBooleanTerminal {

	public BooleanTerminal(Boolean value) {
		super(value);
	}

}
