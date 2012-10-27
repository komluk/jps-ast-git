package pl.edu.pjwstk.jps.result;

import edu.pjwstk.jps.result.IIntegerResult;

public class IntegerResult extends SimpleResult<Integer> implements IIntegerResult {
	public IntegerResult(Integer value) {
		super(value);
	}
}
