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


public class Trips {
	public static void main(String[] args) {
		String csvFile = "C:\\Users\\manos\\OneDrive\\Desktop\\Thema\\data\\trips.txt";
		String output = "C:\\Users\\manos\\OneDrive\\Desktop\\Thema\\data\\trips.ttl";
		try {
			FileWriter out = new FileWriter( output );
		
		Model m = ModelFactory.createDefaultModel();
		String NS = "http://example.com/transportation#";
		Property hasHeadsign = m.createProperty(NS + "hasHeadsign");
		Property hasService = m.createProperty(NS + "hasService");
		Property hasTrip = m.createProperty(NS + "hasTrip");
		
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			line = reader.readNext();
			while ((line = reader.readNext()) != null) {
				Resource route = m.createResource(NS + line[0]);
				Resource service = m.createResource(NS + line[1]);
				Resource trip = m.createResource(NS + line[2]);

				service.addProperty(RDF.type, NS + "Service");
				trip.addProperty(RDF.type, NS + "Trip");

				trip.addProperty(hasHeadsign, line[3], XSDDatatype.XSDstring);
				route.addProperty(hasService, service);
				route.addProperty(hasTrip, trip);
			}
			
			m.write(out, "RDF/XML");
		//	m.write(System.out, "Turtle");

		} catch (CsvValidationException e) {
			e.printStackTrace();
		}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
