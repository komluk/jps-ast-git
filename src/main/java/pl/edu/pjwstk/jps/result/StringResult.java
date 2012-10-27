package pl.edu.pjwstk.jps.result;

import edu.pjwstk.jps.result.IStringResult;

public class StringResult extends SimpleResult<String> implements IStringResult{
	public StringResult(String value) {
		super(value);
	}
}
