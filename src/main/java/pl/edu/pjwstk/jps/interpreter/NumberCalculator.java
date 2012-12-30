package pl.edu.pjwstk.jps.interpreter;

import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IDoubleResult;
import edu.pjwstk.jps.result.IIntegerResult;
import edu.pjwstk.jps.result.ISimpleResult;
import pl.edu.pjwstk.jps.result.DoubleResult;
import pl.edu.pjwstk.jps.result.IntegerResult;

/**
 * User: pawel
 * Date: 30.12.12
 * Time: 21:45
 */
abstract class NumberCalculator {
	private final ISBAStore store;

	private IAbstractQueryResult left;
	private IAbstractQueryResult right;

	public NumberCalculator(ISBAStore store, IAbstractQueryResult left, IAbstractQueryResult right) {
		this.store = store;
		this.left = left;
		this.right = right;

		init();
	}

	private void init() {
		left = InterpreterUtils.toSingleResult(left);
		left = InterpreterUtils.deRefrence(left, store);

		right = InterpreterUtils.toSingleResult(right);
		right = InterpreterUtils.deRefrence(right, store);
	}

	public ISimpleResult<?> calculate() {
		if(left instanceof IIntegerResult && right instanceof IIntegerResult) {
			IIntegerResult leftInt = (IIntegerResult) left;
			IIntegerResult rightInt = (IIntegerResult) right;

			double res = calculate(leftInt.getValue().intValue(), rightInt.getValue().intValue());
			if(res == (int)res) {
				return new IntegerResult((int)res);
			} else {
				return new DoubleResult(res);
			}
		} else if(left instanceof IDoubleResult && right instanceof IIntegerResult) {
			IDoubleResult leftDouble = (IDoubleResult) left;
			IDoubleResult rightDouble = new DoubleResult(((IIntegerResult) right).getValue());

			return new DoubleResult(calculate(leftDouble.getValue().doubleValue(), rightDouble.getValue().doubleValue()));
		} else if(left instanceof  IIntegerResult && right instanceof IDoubleResult) {
			IDoubleResult leftDouble = new DoubleResult(((IIntegerResult) left).getValue());
			IDoubleResult rightDouble = (IDoubleResult) right;

			return new DoubleResult(calculate(leftDouble.getValue().doubleValue(), rightDouble.getValue().doubleValue()));
		} else if(left instanceof  IDoubleResult && right instanceof IDoubleResult) {
			IDoubleResult leftDouble = (IDoubleResult) left;
			IDoubleResult rightDouble = (IDoubleResult) right;

			return new DoubleResult(calculate(leftDouble.getValue().doubleValue(), rightDouble.getValue().doubleValue()));
		} else {
			throw new IllegalStateException("Can not execute minus expression on: " + left.getClass() + " and " + right.getClass());
		}
	}

	protected abstract double calculate(double firstNumber, double secondNumber);
}