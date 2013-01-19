package pl.edu.pjwstk.jps.interpreter;

import edu.pjwstk.jps.datastore.*;
import edu.pjwstk.jps.result.*;
import pl.edu.pjwstk.jps.result.*;
import edu.pjwstk.jps.ast.binary.IBinaryExpression;
import edu.pjwstk.jps.ast.binary.IDotExpression;
import edu.pjwstk.jps.interpreter.envs.IInterpreter;
import edu.pjwstk.jps.interpreter.qres.IQResStack;

import java.util.Arrays;

public final class InterpreterUtils {
	private InterpreterUtils() {}
	
	public static IAbstractQueryResult toSingleResult(IAbstractQueryResult queryResult) {
		return  toSingleResult(queryResult, IAbstractQueryResult.class);
	}

	public static <T extends IAbstractQueryResult> T toSingleResult(IAbstractQueryResult queryResult, Class<T> clazz) {
		if(queryResult instanceof IBagResult) {
			IBagResult leftBag = (IBagResult) queryResult;
			if(leftBag.getElements().size() == 1) {
				return (T)leftBag.getElements().iterator().next();
			} else {
				throw new IllegalStateException("Bag with size diffrent than 1");
			}
		}

		return (T)queryResult;
	}
	
	public static IAbstractQueryResult deRefrence(IAbstractQueryResult ref, ISBAStore store) {
		if(ref instanceof IReferenceResult) {
			ISBAObject obj = store.retrieve(((IReferenceResult)ref).getOIDValue());
			if(obj instanceof ISimpleObject) {
				if(obj instanceof IIntegerObject) {
					IIntegerObject intObj = (IIntegerObject)obj;
					return new IntegerResult(intObj.getValue());
				} else if(obj instanceof IDoubleObject) {
					IDoubleObject doubleObject = (IDoubleObject)obj;
					return new DoubleResult(doubleObject.getValue());
				} else if (obj instanceof IBooleanObject) {
					return new BooleanResult(((IBooleanObject) obj).getValue());
				} else if(obj instanceof IStringObject) {
					return new StringResult(((IStringObject) obj).getValue());
				}
			} else {
				throw new IllegalStateException("Only simple objects supported here but got: " + obj.getClass());
			}
		} else if(ref instanceof IBinderResult) {
			IBinderResult binder = (IBinderResult) ref;
			return deRefrence(binder.getValue(), store);
		}
		
		return ref;
	}

	public static Iterable<ISingleResult> toIterable(IAbstractQueryResult queryResult) {
		return toIterable(queryResult, false);
	}

	public static Iterable<ISingleResult> toIterable(IAbstractQueryResult queryResult, boolean iterateStruct) {
		if(queryResult instanceof IBagResult) {
			if(((IBagResult) queryResult).getElements().size() == 1) {
				return toIterable(((IBagResult) queryResult).getElements().iterator().next());
			} else {
				return ((IBagResult)queryResult).getElements();
			}
		} else if(iterateStruct && queryResult instanceof IStructResult) {
			return ((IStructResult) queryResult).elements();
		} else if(queryResult instanceof ISingleResult){
			ISingleResult singleRes = (ISingleResult) queryResult;
			return Arrays.asList(singleRes);
		} else {
			throw new IllegalStateException("Only SingleResults are supported but was: " + queryResult.getClass());
		}
	}

	public static ISequenceResult toSequence(IAbstractQueryResult queryResult) {
		if(queryResult instanceof IBagResult) {
			ISequenceResult result = new SequenceResult();
			for(ISingleResult element : ((IBagResult) queryResult).getElements()) {
				result.getElements().add(element);
			}
			return result;
		} else if(queryResult instanceof ISingleResult){
			ISingleResult singleRes = (ISingleResult) queryResult;
			return new SequenceResult(singleRes);
		} else if(queryResult instanceof ISequenceResult) {
			return (ISequenceResult) queryResult;
		} else {
			throw new IllegalStateException("Only SingleResults are supported but was: " + queryResult.getClass());
		}
	}

	public static IBooleanResult toBooleanResult(IAbstractQueryResult leftRes) {
		if(leftRes instanceof IBooleanResult) {
			return (IBooleanResult) leftRes;
		} else if(leftRes instanceof IIntegerResult) {
			int val = ((IIntegerResult) leftRes).getValue().intValue();
			return val > 0 ? new BooleanResult(true) : new BooleanResult(false);
		} else {
			throw new UnsupportedOperationException("Have no idea how to convert [" + leftRes + "] to boolean");
		}
	}

	private static boolean equals(IDoubleResult doubleRes, IAbstractQueryResult queryResult) {
		if(queryResult instanceof IIntegerResult) {
			IIntegerResult res = (IIntegerResult) queryResult;
			return (doubleRes.getValue().doubleValue()) == ((double)res.getValue());
		} else if(queryResult instanceof DoubleResult) {
			DoubleResult res = (DoubleResult) queryResult;
			return doubleRes.getValue().doubleValue() == res.getValue().doubleValue();
		} else {
			return false;
		}
	}

	private static boolean equals(IStringResult stringResult, IAbstractQueryResult queryResult) {
		if(queryResult instanceof IStringResult) {
			IStringResult res = (IStringResult) queryResult;
			return stringResult.getValue().equals(res.getValue());
		} else {
			return false;
		}
	}

	private static boolean equlas(IIntegerResult intRes, IAbstractQueryResult queryResult) {
		if(queryResult instanceof DoubleResult) {
			IDoubleResult res = (IDoubleResult) queryResult;
			return intRes.getValue().intValue() == res.getValue().doubleValue();
		} else if(queryResult instanceof IIntegerResult) {
			IIntegerResult res = (IIntegerResult) queryResult;
			return intRes.getValue().intValue() == res.getValue().intValue();
		} else {
			return false;
		}
	}

	private static boolean equlas(IBooleanResult booleanResult, IAbstractQueryResult queryResult) {
		if(queryResult instanceof IBooleanResult) {
			IBooleanResult res = (IBooleanResult) queryResult;
			return booleanResult.getValue().booleanValue() == res.getValue().booleanValue();
		} else {
			return false;
		}
	}
	
	public static boolean equals(IBinaryExpression binnary, IInterpreter interpreter, IQResStack qres, ISBAStore store) {
		binnary.getLeftExpression().accept(interpreter);
		binnary.getRightExpression().accept(interpreter);
		
		IAbstractQueryResult right = qres.pop();
		IAbstractQueryResult left = qres.pop();
		
		left = toSingleResult(left);
		left = deRefrence(left, store);
		
		right = toSingleResult(right);
		right = deRefrence(right, store);
		
		if(left instanceof DoubleResult) {
			return InterpreterUtils.equals((DoubleResult)left, right);
		} else if(left instanceof IStringResult) {
			return InterpreterUtils.equals((IStringResult) left, right);
		} else if(left instanceof IIntegerResult) {
			return InterpreterUtils.equlas((IIntegerResult)left, right);
		} else if(left instanceof IBooleanResult) {
			return InterpreterUtils.equlas((IBooleanResult)left, right);
		} else {
			throw new IllegalStateException("Unsupported types for equals expression: [" + left + "] and [" + right + "]");
		}
	}
}
