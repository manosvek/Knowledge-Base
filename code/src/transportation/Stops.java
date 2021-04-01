package transportation;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;


public class Stops {
	public static void main(String[] args) {
		String csvFile = "C:\\Users\\manos\\OneDrive\\Desktop\\Thema\\data\\stops.txt";
		String output = "C:\\Users\\manos\\OneDrive\\Desktop\\Thema\\data\\stops.ttl";
		try {
			FileWriter out = new FileWriter( output );
		
		Model m = ModelFactory.createDefaultModel();
		String NS = "http://example.com/transportation#";
		Property stopName = m.createProperty(NS + "stopName");
		Property stopDescription = m.createProperty(NS + "stopDescription");
		Property stopLocation = m.createProperty(NS + "stopLocation");
		
		Property geometry = m.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#geometry");
		
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			line = reader.readNext();
			while ((line = reader.readNext()) != null) {
				Resource stop = m.createResource(NS + line[0]);
				Resource point = m.createResource(NS + line[0]+"Point");

				stop.addProperty(RDF.type, NS + "Stop");
				stop.addProperty(stopName, line[2], XSDDatatype.XSDstring);
				stop.addProperty(stopDescription, line[3], XSDDatatype.XSDstring);
				stop.addProperty(stopLocation, point);
				
				Literal loc = m.createTypedLiteral("point("+line[4]+" "+line[5] + ")", "http://www.openlinksw.com/schemas/virtrdf#Geometry");
				point.addProperty(geometry, loc);
			}
			
			m.write(out, "Turtle");
			//m.write(System.out, "Turtle");

		} catch (CsvValidationException e) {
			e.printStackTrace();
		}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}


