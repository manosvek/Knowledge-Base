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


public class StopTimes {
	public static void main(String[] args) throws IOException {
	String csvFile = "C:\\Users\\manos\\OneDrive\\Desktop\\Thema\\data\\xad";
	String output = "C:\\Users\\manos\\OneDrive\\Desktop\\Thema\\data\\stop_times_4.ttl";
		
	FileWriter out = new FileWriter( output );
		
	Model m = ModelFactory.createDefaultModel();
	String NS = "http://example.com/transportation#";
	Property arrivalPlace = m.createProperty(NS + "arrivalPlace");
	Property arrivalTime = m.createProperty(NS + "arrivalTime");
	Property departureTime = m.createProperty(NS + "departureTime");
	Property arrivalSequence = m.createProperty(NS + "arrivalSequence");
	Property arrivesAt = m.createProperty(NS + "arrivesAt");
		
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			line = reader.readNext();
			while ((line = reader.readNext()) != null) {
				Resource arrival = m.createResource(NS + line[0] +"_"+ line[3]);
				Resource stop = m.createResource(NS + line[3]);
				Resource trip = m.createResource(NS + line[0]);

				arrival.addProperty(RDF.type, NS + "Arrival");

				arrival.addProperty(arrivalPlace, stop);
				arrival.addProperty(arrivalTime, line[1], XSDDatatype.XSDstring);
				arrival.addProperty(departureTime, line[2], XSDDatatype.XSDstring);
				arrival.addProperty(arrivalSequence, line[4], XSDDatatype.XSDint);
				trip.addProperty(arrivesAt, arrival);
			}
			
			m.write(out, "Turtle");
			//m.write(System.out, "Turtle");

		} catch (CsvValidationException e) {
			e.printStackTrace();
		}
	}
}


