package pl.edu.pjwstk.jps.interpreter;

import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IDoubleResult;
import edu.pjwstk.jps.result.IIntegerResult;
import edu.pjwstk.jps.result.ISingleResult;
import pl.edu.pjwstk.jps.result.DoubleResult;
import pl.edu.pjwstk.jps.result.IntegerResult;

import static pl.edu.pjwstk.jps.interpreter.InterpreterUtils.toIterable;

/**
 * User: pawel
 * Date: 30.12.12
 * Time: 21:46
 */
abstract class NumberAggregation {
	protected final ISBAStore store;
	private final IAbstractQueryResult result;

	public NumberAggregation(ISBAStore store, IAbstractQueryResult result) {
		this.result = InterpreterUtils.deRefrence(result, store);
		this.store = store;
	}

	public ISingleResult aggregate() {
		for(ISingleResult single : toIterable(result)) {
			if(performDereference()) {
				single = (ISingleResult) InterpreterUtils.deRefrence(single, store);
			}

			if(single instanceof IIntegerResult) {
				aggregateSingle(((IIntegerResult) single).getValue().intValue());
			} else if(single instanceof IDoubleResult) {
				aggregateSingle(((IDoubleResult) single).getValue().intValue());
			} else {
				throw new IllegalStateException("sum can not be applied to " + single.getClass());
			}
		}

		double result = getResult();
		if(result == (int)result) {
			return new IntegerResult((int)result);
		} else {
			return new DoubleResult(result);
		}
	}

	protected boolean performDereference() {
		return true;
	}

	protected abstract void aggregateSingle(double number);
	protected abstract double getResult();
}
