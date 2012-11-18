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
import edu.pjwstk.jps.datastore.IStringObject;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.IBooleanResult;
import edu.pjwstk.jps.result.IIntegerResult;
import edu.pjwstk.jps.result.IReferenceResult;
import edu.pjwstk.jps.result.ISingleResult;

public class ENVSMain {
	public static void main(String[] args) throws Exception {
		SBAStore.getInstance().loadXML(new File(ENVSMain.class.getResource("/envs.xml").toURI()).getAbsolutePath());
		System.out.println("Loaded data:\n" + new SBAStorePrinter(SBAStore.getInstance()).toXml());
		
		query2();
	}

	/**
	 * ((emp where married == true).book.author) union (realNumber)
	 */
	private static void query1() {
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
		qres.push(structResults);
		
		System.out.println("address result = " + qres.pop());
	}
}
