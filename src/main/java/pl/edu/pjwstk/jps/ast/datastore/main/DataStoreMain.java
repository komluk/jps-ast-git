package pl.edu.pjwstk.jps.ast.datastore.main;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;

import pl.edu.pjwstk.jps.ast.datastore.SBAStore;
import pl.edu.pjwstk.jps.ast.datastore.util.SBAStorePrinter;

public class DataStoreMain {
	public static void main(String[] args) throws Exception {
		SBAStore.getInstance().loadXML(new File(DataStoreMain.class.getResource("/example.xml").toURI()).getAbsolutePath());
		
		List<Employee> employees = Lists.newArrayList(
				new Employee("Jan", "Nowak", 5000),
				new Employee("Adam", "Kowalski", 4000),
				new Employee("Piotr", "Adamczyk", 3000)
				);
		
		List<Product> products = Lists.newArrayList(
				new Product("A", 100),
				new Product("B", 110),
				new Product("C", 120)
				);
		
		Company c = new Company("company", new Location("Polska", "Warszawa", "Krakowska", 10), employees, products);
		SBAStore.getInstance().addJavaObject(c, "company");
		
		System.out.println(new SBAStorePrinter(SBAStore.getInstance()).toXml());
//		System.out.println(SBAStore.getInstance());
	}
}
