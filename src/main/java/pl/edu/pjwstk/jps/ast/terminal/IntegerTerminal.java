package pl.edu.pjwstk.jps.ast.terminal;

import edu.pjwstk.jps.ast.terminal.IIntegerTerminal;

public class IntegerTerminal extends AbstractTerminal<Integer> implements IIntegerTerminal {

	public IntegerTerminal(Integer value) {
		super(value);
	}

}
