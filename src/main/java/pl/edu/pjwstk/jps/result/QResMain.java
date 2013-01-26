package pl.edu.pjwstk.jps.result;

import pl.edu.pjwstk.jps.ast.auxname.AsExpression;
import pl.edu.pjwstk.jps.qres.interpreter.QResStack;
import pl.edu.pjwstk.jps.result.util.CartesianProduct;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.ISingleResult;

public class QResMain {
	public static void main(String[] args) {
		System.out.println("");
		System.out.println("");
		
		exampleQuery();
		System.out.println("");
		
		query1();
		System.out.println("");
		
		query2();
		System.out.println("");
		
		query3();
		System.out.println("");
	}
	
	private static void query1() {
		QResStack qres = new QResStack();
		
		qres.push(new BagResult(
				new IntegerResult(1),
				new DoubleResult(2.1)));
		
		qres.push(new IntegerResult(3));
		qres.push(new IntegerResult(4));
		
		IntegerResult right = (IntegerResult) qres.pop();
		IntegerResult left = (IntegerResult) qres.pop();
		
		qres.push(new IntegerResult(left.getValue() + right.getValue()));
		qres.push(new StringResult("test"));
		
		BagResult bag = new BagResult();
		bag.getElements().add((ISingleResult) qres.pop());
		bag.getElements().add((ISingleResult) qres.pop());
		qres.push(bag);
		
		BagResult rightBag = (BagResult) qres.pop();
		BagResult leftBag = (BagResult) qres.pop();
		
		qres.push(new CartesianProduct(leftBag, rightBag).getResult());
		IBagResult res = (IBagResult) qres.pop();

		BagResult totalRes = new BagResult();
		for(ISingleResult single : res.getElements()) {
			totalRes.add(new BinderResult("nazwa", single));
		}
		qres.push(totalRes);
		
		System.out.println("(bag(1, 2.1), bag(3+4, \"test\")) as nazwa = " + qres.pop());
	}
	
	private static void query2() {
		QResStack stack = new QResStack();
		stack.push(new BagResult(
				new StringResult("ala"),
				new StringResult("ma"),
				new StringResult("kota")));
		
		stack.push(new IntegerResult(8));
		stack.push(new IntegerResult(10));
		
		IntegerResult right = (IntegerResult) stack.pop();
		IntegerResult left = (IntegerResult) stack.pop();
		stack.push(new IntegerResult(left.getValue() * right.getValue()));
		stack.push(new BooleanResult(false));
		
		BagResult bag = new BagResult();
		bag.getElements().add((ISingleResult) stack.pop());
		bag.getElements().add((ISingleResult) stack.pop());
		stack.push(bag);
		
		IAbstractQueryResult second = stack.pop();
		IAbstractQueryResult first = stack.pop();
		
		System.out.println("(bag(\"ala\", \"ma\", \"kota\"), (8*10, false)) = " + new CartesianProduct(first, second).getResult());
	}
	
	private static void query3() {
		QResStack stack = new QResStack();
		stack.push(new BagResult(new StringResult("JPS"), new StringResult("rules")));
		
		IAbstractQueryResult bag = stack.pop();
		
		stack.push(new BinderResult("x", bag));
		stack.push(new DoubleResult(2.2));

		DoubleResult number = (DoubleResult) stack.pop();
		BinderResult binder = (BinderResult) stack.pop();
		
		stack.push(new CartesianProduct(binder, number).getResult());
		stack.push(new BooleanResult(true));
		
		IAbstractQueryResult second = stack.pop();
		IAbstractQueryResult first = stack.pop();
		
		System.out.println("(((bag(\"JPS\", \"rules\")) groupas x), 2.2, true) = " + new CartesianProduct(first, second).getResult());
	}
	
	private static void exampleQuery() {
		QResStack qres = new QResStack();
		qres.push(new IntegerResult(1));
		qres.push(new IntegerResult(2));
		qres.push(new IntegerResult(3));
		
		IntegerResult multiRight = (IntegerResult) qres.pop(); //3
		IntegerResult multiLeft = (IntegerResult) qres.pop(); //2
		IntegerResult multiRes = new IntegerResult(multiLeft.getValue() * multiRight.getValue());
		qres.push(multiRes);
		
		
		IntegerResult plusRight = (IntegerResult) qres.pop(); //6
		IntegerResult plusLeft = (IntegerResult) qres.pop(); //1
		
		IntegerResult plusRes = new IntegerResult(plusLeft.getValue() + plusRight.getValue());
		qres.push(plusRes);
		
		qres.push(new IntegerResult(4));
		
		IntegerResult minusRight = (IntegerResult) qres.pop(); //4
		IntegerResult minusLeft = (IntegerResult) qres.pop(); //7
		
		IntegerResult minusRes = new IntegerResult(minusLeft.getValue() - minusRight.getValue());
		qres.push(minusRes);
		
		IAbstractQueryResult res = qres.pop();
		System.out.println("1 + 2 * 3 - 4 = " + res);
	}
}
