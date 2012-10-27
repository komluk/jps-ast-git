package pl.edu.pjwstk.jps.result;

import edu.pjwstk.jps.result.IDoubleResult;

public class DoubleResult extends SimpleResult<Double> implements IDoubleResult {

	public DoubleResult(Double value) {
		super(value);
	}

}
