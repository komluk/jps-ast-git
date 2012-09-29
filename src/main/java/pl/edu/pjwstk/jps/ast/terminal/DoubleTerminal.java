package pl.edu.pjwstk.jps.ast.terminal;

import edu.pjwstk.jps.ast.terminal.IDoubleTerminal;

public class DoubleTerminal extends AbstractTerminal<Double> implements IDoubleTerminal {

	public DoubleTerminal(Double value) {
		super(value);
	}

}
