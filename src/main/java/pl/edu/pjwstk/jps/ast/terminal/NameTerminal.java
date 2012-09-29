package pl.edu.pjwstk.jps.ast.terminal;

import edu.pjwstk.jps.ast.terminal.INameTerminal;

//FIXME czy tak jest good?
@SuppressWarnings("rawtypes")
public class NameTerminal extends AbstractTerminal implements INameTerminal {
	@SuppressWarnings("unchecked")
	public NameTerminal(Object value) {
		super(value);
	}

	@Override
	public String getName() {
		return getValue().toString();
	}
}
