package pl.edu.pjwstk.jps.result;

import edu.pjwstk.jps.result.IBooleanResult;

public class BooleanResult extends SimpleResult<Boolean> implements IBooleanResult {

	public BooleanResult(Boolean value) {
		super(value);
	}
}
