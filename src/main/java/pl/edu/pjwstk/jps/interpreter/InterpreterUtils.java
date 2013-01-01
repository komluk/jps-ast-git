package pl.edu.pjwstk.jps.interpreter;

import edu.pjwstk.jps.datastore.*;
import edu.pjwstk.jps.result.*;
import pl.edu.pjwstk.jps.result.*;
import edu.pjwstk.jps.ast.binary.IBinaryExpression;
import edu.pjwstk.jps.ast.binary.IDotExpression;
import edu.pjwstk.jps.interpreter.envs.IInterpreter;
import edu.pjwstk.jps.interpreter.qres.IQResStack;

import java.util.Arrays;

final class InterpreterUtils {
	private InterpreterUtils() {}
	
	public static IAbstractQueryResult toSingleResult(IAbstractQueryResult queryResult) {
		if(queryResult instanceof IBagResult) {
			IBagResult leftBag = (IBagResult) queryResult;
			if(leftBag.getElements().size() == 1) {
				return leftBag.getElements().iterator().next();
			} else {
				throw new IllegalStateException("Bag with size diffrent than 1");
			}
		}

		if(queryResult instanceof IStructResult) {
			IStructResult leftStruct = (IStructResult) queryResult;
			if(leftStruct.elements().size() == 1) {
				return leftStruct.elements().iterator().next();
			} else {
				throw new IllegalStateException("Struct with size diffrent than 1");
			}
		}
		
		return queryResult;
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
			return binder.getValue();
		}
		
		return ref;
	}
	
//	public static IBagResult toBag(IAbstractQueryResult queryResult) {
//		if(queryResult instanceof IBagResult) {
//			return (IBagResult)queryResult;
//		} else if(queryResult instanceof ISingleResult){
//			ISingleResult singleRes = (ISingleResult) queryResult;
//			return new BagResult(singleRes);
//		} else if(queryResult instanceof IStructResult) {
//			BagResult bag = new BagResult();
//			for(ISingleResult single : ((IStructResult) queryResult).elements()) {
//				bag.add(single);
//			}
//			return bag;
//		} else {
//			throw new IllegalStateException("Only SingleResults are supported but was: " + queryResult.getClass());
//		}
//	}

	public static Iterable<ISingleResult> toIterable(IAbstractQueryResult queryResult) {
		if(queryResult instanceof IBagResult) {
			if(((IBagResult) queryResult).getElements().size() == 1) {
				return toIterable(((IBagResult) queryResult).getElements().iterator().next());
			} else {
				return ((IBagResult)queryResult).getElements();
			}
		} else if(queryResult instanceof IStructResult) {
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

	protected static boolean equals(IDoubleResult doubleRes, IAbstractQueryResult queryResult) {
		if(queryResult instanceof IBooleanResult) {
			IBooleanResult res = (IBooleanResult) queryResult;
			return doubleRes.getValue().doubleValue() == (res.getValue() ? 1.0 : 0.0);
		} else if(queryResult instanceof IIntegerResult) {
			IIntegerResult res = (IIntegerResult) queryResult;
			return (doubleRes.getValue().doubleValue()) == ((double)res.getValue());
		} else if(queryResult instanceof DoubleResult) {
			DoubleResult res = (DoubleResult) queryResult;
			return doubleRes.getValue().doubleValue() == res.getValue().doubleValue();
		} else {
			throw new IllegalStateException("Can not compare double and " + queryResult.getClass());
		}
	}
	
	protected static boolean equals(IStringResult stringResult, IAbstractQueryResult queryResult) {
		if(queryResult instanceof IBooleanResult) {
			IBooleanResult res = (IBooleanResult) queryResult;
			return stringResult.getValue().equals(res.getValue().toString());
		} else if(queryResult instanceof IDoubleResult) {
			IDoubleResult res = (IDoubleResult) queryResult;
			return stringResult.getValue().equals(res.getValue().toString());
		} else if(queryResult instanceof IIntegerResult) {
			IIntegerResult res = (IIntegerResult) queryResult;
			return stringResult.getValue().equals(res.getValue().toString());
		} else if(queryResult instanceof IStringResult) {
			IStringResult res = (IStringResult) queryResult;
			return stringResult.getValue().equals(res.getValue());
		} else {
			throw new IllegalStateException("Can not compare strin and " + queryResult.getClass());
		}
	}

	protected static boolean equlas(IIntegerResult intRes, IAbstractQueryResult queryResult) {
		if(queryResult instanceof DoubleResult) {
			IDoubleResult res = (IDoubleResult) queryResult;
			return intRes.getValue().intValue() == res.getValue().doubleValue();
		} else if(queryResult instanceof IBooleanResult) {
			IBooleanResult res = (IBooleanResult) queryResult;
			return intRes.getValue().intValue() == (res.getValue() ? 1 : 0);
		} else if(queryResult instanceof IIntegerResult) {
			IIntegerResult res = (IIntegerResult) queryResult;
			return intRes.getValue().intValue() == res.getValue().intValue();
		} else {
			throw new IllegalStateException("can not compare int and " + queryResult.getClass());
		}
	}

	protected static boolean equlas(IBooleanResult booleanResult, IAbstractQueryResult queryResult) {
		if(queryResult instanceof DoubleResult) {
			IDoubleResult res = (IDoubleResult) queryResult;
			return (booleanResult.getValue() ? 1.0 : 0.0) == res.getValue().doubleValue();
		} else if(queryResult instanceof IBooleanResult) {
			IBooleanResult res = (IBooleanResult) queryResult;
			return booleanResult.getValue().booleanValue() == res.getValue().booleanValue();
		} else if(queryResult instanceof IIntegerResult) {
			IIntegerResult res = (IIntegerResult) queryResult;
			return (booleanResult.getValue() ? 1 : 0) == res.getValue().intValue();
		} else if(queryResult instanceof IStringResult) {
			IStringResult res = (IStringResult) queryResult;
			return booleanResult.getValue().toString().equals(res.getValue());
		} else {
			throw new IllegalStateException("can not compare boolean and " + queryResult.getClass());
		}
	}
	
	public static boolean equals(IBinaryExpression binnary, IInterpreter interpreter, IQResStack qres, ISBAStore store) {
		binnary.getLeftExpression().accept(interpreter);
		binnary.getRightExpression().accept(interpreter);
		
		IAbstractQueryResult right = qres.pop();
		IAbstractQueryResult left = qres.pop();
		
		left = InterpreterUtils.toSingleResult(left);
		left = InterpreterUtils.deRefrence(left, store);
		
		right = InterpreterUtils.toSingleResult(right);
		right = InterpreterUtils.deRefrence(right, store);
		
		if(left instanceof DoubleResult) {
			return InterpreterUtils.equals((DoubleResult)left, right);
		} else if(left instanceof IStringResult) {
			return InterpreterUtils.equals((IDoubleResult) left, right);
		} else if(left instanceof IIntegerResult) {
			return InterpreterUtils.equlas((IIntegerResult)left, right);
		} else if(left instanceof IBooleanResult) {
			return InterpreterUtils.equlas((IBooleanResult)left, right);
		} else {
			throw new IllegalStateException("Unssuported types for equals expression");
		}
	}
}
