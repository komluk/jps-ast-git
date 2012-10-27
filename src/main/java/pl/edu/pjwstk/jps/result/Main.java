package pl.edu.pjwstk.jps.result;

import pl.edu.pjwstk.jps.qres.interpreter.QResStack;
import edu.pjwstk.jps.result.IAbstractQueryResult;

public class Main {
	public static void main(String[] args) {
		exampleQuery(true);
	}
	
	public static IAbstractQueryResult exampleQuery(boolean debug) {
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
		return res;
	}
}
