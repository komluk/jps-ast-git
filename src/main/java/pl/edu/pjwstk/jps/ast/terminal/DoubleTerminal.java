package pl.edu.pjwstk.jps.ast.terminal;

import edu.pjwstk.jps.ast.terminal.IDoubleTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class DoubleTerminal extends AbstractTerminal<Double> implements IDoubleTerminal {

	public DoubleTerminal(Double value) {
		super(value);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitDoubleTerminal(this);
	}

}
