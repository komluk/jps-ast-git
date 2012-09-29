package pl.edu.pjwstk.jps.ast.terminal;

import edu.pjwstk.jps.ast.terminal.IStringTerminal;

public class StringTerminal extends AbstractTerminal<String> implements IStringTerminal {

	public StringTerminal(String value) {
		super(value);
	}

}
