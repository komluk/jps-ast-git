package pl.edu.pjwstk.jps.ast.datastore.main;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

public class Company {
	public List<Employee> employees;
	public List<Product> products;
	
	public Location location;
	
	public String name;
	
	public Company(String name, Location location, Collection<Employee> employees, Collection<Product> products) {
		this.name = name;
		this.location = location;
		this.employees = Lists.newArrayList(employees);
		this.products = Lists.newArrayList(products);
	}
}
