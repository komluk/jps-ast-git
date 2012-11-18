package pl.edu.pjwstk.jps.interpreter.envs;

import java.io.File;

import pl.edu.pjwstk.jps.ast.datastore.IntegerObject;
import pl.edu.pjwstk.jps.ast.datastore.SBAStore;
import pl.edu.pjwstk.jps.ast.datastore.util.SBAStorePrinter;
import pl.edu.pjwstk.jps.qres.interpreter.QResStack;
import pl.edu.pjwstk.jps.result.BagResult;
import pl.edu.pjwstk.jps.result.BooleanResult;
import pl.edu.pjwstk.jps.result.IntegerResult;
import pl.edu.pjwstk.jps.result.StringResult;
import pl.edu.pjwstk.jps.result.StructResult;
import pl.edu.pjwstk.jps.result.util.CartesianProduct;
import edu.pjwstk.jps.datastore.IBooleanObject;
import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.IStringObject;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.IBooleanResult;
import edu.pjwstk.jps.result.IIntegerResult;
import edu.pjwstk.jps.result.IReferenceResult;
import edu.pjwstk.jps.result.ISingleResult;

public class ENVSMain {
	public static void main(String[] args) throws Exception {
		SBAStore.getInstance().loadXML(new File(ENVSMain.class.getResource("/envs.xml").toURI()).getAbsolutePath());
		System.out.println("Loaded data:\n" + new SBAStorePrinter(SBAStore.getInstance()).toXml());
		
		query1();
		query2();
	}

	/**
	 * ((emp where married == true).book.author) union (realNumber)
	 */
	private static void query1() {
		ENVS envs = new ENVS();
		QResStack qres = new QResStack();
		
		envs.init(SBAStore.getInstance().getEntryOID(), SBAStore.getInstance());
		qres.push(envs.bind("emp"));
		
		BagResult marriedResult = new BagResult();
		for(ISingleResult emp : ((IBagResult)qres.pop()).getElements()) {
			envs.push(envs.nested(emp, SBAStore.getInstance()));
			IBagResult marriedEmp = envs.bind("married");
			qres.push(marriedEmp);
			qres.push(new BooleanResult(true));
			IBooleanResult trueResult = (IBooleanResult) qres.pop();
			IBooleanObject married = (IBooleanObject) SBAStore.getInstance().retrieve(retrive(qres, IReferenceResult.class).getOIDValue());
			if(married.getValue() == trueResult.getValue()) {
				marriedResult.getElements().add(emp);
			}
			envs.pop();
		}
		
		qres.push(marriedResult);
		System.out.println("married emps: " + marriedResult);
		IBagResult books = new BagResult();
		for(ISingleResult marriedEmp : ((IBagResult)qres.pop()).getElements()) {
			envs.push(envs.nested(marriedEmp, SBAStore.getInstance()));
			qres.push(envs.bind("book"));
			books.getElements().addAll(((IBagResult)qres.pop()).getElements());
			envs.pop();
		}
		qres.push(books);
		
		IBagResult authors = new BagResult();
		for(ISingleResult book : ((IBagResult)qres.pop()).getElements()) {
			envs.push(envs.nested(book, SBAStore.getInstance()));
			qres.push(envs.bind("author"));
			authors.getElements().addAll(((IBagResult)qres.pop()).getElements());
			envs.pop();
		}
		qres.push(authors);
		
		System.out.println("authors = " + authors);
		
		qres.push(envs.bind("realNumber"));
//		union:
//		Ewaluacja:
//			1. Zainicjalizuj pusty bag (bedziemy odnosic sie do niego jaki eres).
//			2. Wykonaj oba podwyrazenia.
//			3. Podnies dwa elementy z QRES.
//			4. Dodaj
		IBagResult unionResults = new BagResult();
		unionResults.getElements().addAll(((IBagResult)qres.pop()).getElements());
		unionResults.getElements().addAll(((IBagResult)qres.pop()).getElements());
		qres.push(unionResults);
		System.out.println("union: " + unionResults);
		
		for(ISingleResult unionResult : ((IBagResult)qres.pop()).getElements()) {
			ISBAObject object = SBAStore.getInstance().retrieve(((IReferenceResult)unionResult).getOIDValue());
			System.out.println("union element [" + object.getOID() + "] " + object);
		}
	}
	
	/**
	 * ((emp.address) where number>20).(street, city)
	 * */
	private static void query2() {
		ENVS envs = new ENVS();
		QResStack qres = new QResStack();
		
		envs.init(SBAStore.getInstance().getEntryOID(), SBAStore.getInstance());
		qres.push(envs.bind("emp"));
		
		BagResult addressDotResult = new BagResult();
		
		IBagResult bagResult = (IBagResult) qres.pop();
		for(ISingleResult res : bagResult.getElements()) {
			envs.push(envs.nested(res, SBAStore.getInstance()));
			IBagResult address = envs.bind("address");
			qres.push(address);
			envs.pop();
			addressDotResult.getElements().addAll(address.getElements());
		}
		
		qres.push(addressDotResult);
		addressDotResult = (BagResult) qres.pop();
		
		IBagResult whereAddressResult = new BagResult();
		for(ISingleResult address : addressDotResult.getElements()) {
			envs.push(envs.nested(address, SBAStore.getInstance()));
			IBagResult numbers = envs.bind("number");
			qres.push(numbers);
			qres.push(new IntegerResult(20));
			
			IIntegerResult right = (IIntegerResult) qres.pop();
			numbers = (IBagResult) qres.pop();
			
			IntegerObject left = (IntegerObject)SBAStore.getInstance().retrieve(((IReferenceResult)numbers.getElements().iterator().next()).getOIDValue());
			qres.push(new BooleanResult(left.getValue() > right.getValue()));
			
			IBooleanResult booleanResult = (IBooleanResult) qres.pop();
			IBagResult matchedAddress = (IBagResult) qres.pop();
			if(booleanResult.getValue()) {
				whereAddressResult.getElements().addAll(matchedAddress.getElements());
			}
			
			envs.pop();
		}
		qres.push(whereAddressResult);
		
		IBagResult structResults = new BagResult();
		whereAddressResult = (IBagResult) qres.pop();
		for(ISingleResult address : whereAddressResult.getElements()) {
			envs.push(envs.nested(address, SBAStore.getInstance()));
			IBagResult streets = envs.bind("street");
			qres.push(streets);
			
			IBagResult cities = envs.bind("city");
			qres.push(cities);
			
			envs.pop();
			IReferenceResult rightCityReference = (IReferenceResult) ((IBagResult) qres.pop()).getElements().iterator().next();
			IReferenceResult leftStreetReference = (IReferenceResult) ((IBagResult) qres.pop()).getElements().iterator().next();
			
			IStringObject rightCity = (IStringObject) SBAStore.getInstance().retrieve(rightCityReference.getOIDValue());
			IStringObject leftStreet = (IStringObject) SBAStore.getInstance().retrieve(leftStreetReference.getOIDValue());
			
			StructResult struct = (StructResult) new CartesianProduct(new StringResult(leftStreet.getValue()), new StringResult(rightCity.getValue())).getResult().getElements().iterator().next();
			structResults.getElements().add(struct);
		}
		envs.pop();
		qres.push(structResults);
		
		System.out.println("address result = " + qres.pop());
	}
	
	public static <T> T retrive(QResStack qres, Class<T> clazz) {
		IAbstractQueryResult res = qres.pop();
		if(res instanceof IBagResult) {
			IBagResult bag = (IBagResult) res;
			if(bag.getElements().size() > 1) {
				throw new IllegalStateException();
			} else {
				ISingleResult singleResult = bag.getElements().iterator().next();
				return (T)singleResult;
			}
		} else {
			return (T)res;
		}
	}
}
