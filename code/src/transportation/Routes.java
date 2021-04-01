package transportation;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;


public class Routes {
	public static void main(String[] args) {
		String csvFile = "C:\\Users\\manos\\OneDrive\\Desktop\\Thema\\data\\routes.txt";
		String output = "C:\\Users\\manos\\OneDrive\\Desktop\\Thema\\data\\routes.ttl";
		try {
			FileWriter out = new FileWriter( output );
		
		Model m = ModelFactory.createDefaultModel();
		String NS = "http://example.com/transportation#";
		Property routeName = m.createProperty(NS + "routeName");
		Property hasRoute = m.createProperty(NS + "hasRoute");
		
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			line = reader.readNext();
			while ((line = reader.readNext()) != null) {
				Resource route = m.createResource(NS + line[0]);
				Resource mean = m.createResource(NS + line[1]);

				route.addProperty(routeName, line[2], XSDDatatype.XSDstring);
				mean.addProperty(hasRoute, route);
				
				if (line[4].compareTo("1") == 0) {
					mean.addProperty(RDF.type, NS + "Metro");
				}
				else if (line[4].compareTo("900") == 0) {
					mean.addProperty(RDF.type, NS + "Tram");
				}
				else if (line[4].compareTo("800") == 0) {
					mean.addProperty(RDF.type, NS + "Trolley");
				}
				else if (line[4].compareTo("3") == 0) {
					mean.addProperty(RDF.type, NS + "Bus");
				}
			}
			
			m.write(out, "Turtle");

		} catch (CsvValidationException e) {
			e.printStackTrace();
		}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
