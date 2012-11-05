package pl.edu.pjwstk.jps.ast.datastore.main;

public class Location {
	public String country, city, street;
	public int number;

	public Location(String country, String city, String street, int number) {
		this.country = country;
		this.city = city;
		this.street = street;
		this.number = number;
	}
}
