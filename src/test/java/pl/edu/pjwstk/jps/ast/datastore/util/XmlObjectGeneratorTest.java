package pl.edu.pjwstk.jps.ast.datastore.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import org.testng.annotations.Test;


public class XmlObjectGeneratorTest {
	String simpleXml = "<db>" +
			"<person>" +
			"	<first-name>Jan</first-name>" +
			"	<last-name>Kowalski</last-name>" +
			"	<salary>2500.5</salary>" +
			"	<addresses>" +
			"		<address>" +
			"			<city>Warszawa</city>" +
			"			<street>Warszawska</street>" +
			"			<number>34</number>" +
			"		</address>" +
			"		<address>" +
			"			<city>Kraków</city>" +
			"			<street>Krakowska</street>" +
			"			<number>10</number>" +
			"		</address>" +
			"	</addresses>" +
			"</person>" +
			"<person>" +
			"	<first-name>Jan</first-name>" +
			"	<last-name>Nowak</last-name>" +
			"	<salary>5000</salary>" +
			"	<addresses>" +
			"		<address>" +
			"			<city>Łódź</city>" + 
			"			<street>Morska</street>" +
			"			<number>11</number>" +
			"		</address>" +
			"		<address>" +
			"			<city>Kielce</city>" +
			"			<street>Kielecka</street>" +
			"			<number>16</number>" +
			"		</address>" +
			"	</addresses>" +
			"</person>" +
			"</db>";
	
	@Test
	public void testGetObject() {
		new XmlObjectGenerator(new ByteArrayInputStream(simpleXml.getBytes())).getObject();
	}
			
}
