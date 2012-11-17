package pl.edu.pjwstk.jps.interpreter.envs;

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
}
