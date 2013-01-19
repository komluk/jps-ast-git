package pl.edu.pjwstk.jps.ast.terminal;

import edu.pjwstk.jps.ast.terminal.ITerminalExpression;
import pl.edu.pjwstk.jps.ast.AbstractExpression;

abstract class AbstractTerminal<T> extends AbstractExpression implements ITerminalExpression<T> {
	private final T value;
	
	public AbstractTerminal(T value) {
		this.value = value;
	}
	
	@Override
	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" +
				"value=" + value +
				'}';
	}
}
