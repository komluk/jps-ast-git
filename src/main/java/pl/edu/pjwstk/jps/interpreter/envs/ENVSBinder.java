package pl.edu.pjwstk.jps.interpreter.envs;

import com.google.common.base.Objects;

import edu.pjwstk.jps.interpreter.envs.IENVSBinder;
import edu.pjwstk.jps.result.IAbstractQueryResult;

public class ENVSBinder implements IENVSBinder {
	private final String name;
	private final IAbstractQueryResult queryResult;
	
	public ENVSBinder(String name, IAbstractQueryResult queryResult) {
		this.name = name;
		this.queryResult = queryResult;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IAbstractQueryResult getValue() {
		return queryResult;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("name", getName())
				.add("value", getValue())
				.toString();
	}
}
