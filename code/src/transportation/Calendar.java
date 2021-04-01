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


public class Calendar {
	public static void main(String[] args) {
		String csvFile = "C:\\Users\\manos\\OneDrive\\Desktop\\Thema\\data\\calendar.txt";
		String output = "C:\\Users\\manos\\OneDrive\\Desktop\\Thema\\data\\calendar.ttl";
		try {
			FileWriter out = new FileWriter( output );
		
		Model m = ModelFactory.createDefaultModel();
		String NS = "http://example.com/transportation#";
		Property monday = m.createProperty(NS + "worksOnMonday");
		Property tuesday = m.createProperty(NS + "worksOnTuesday");
		Property wednesday = m.createProperty(NS + "worksOnWednesday");
		Property thursday = m.createProperty(NS + "worksOnThursday");
		Property friday = m.createProperty(NS + "worksOnFriday");
		Property saturday = m.createProperty(NS + "worksOnSaturday");
		Property sunday = m.createProperty(NS + "worksOnSunday");
		
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			line = reader.readNext();
			while ((line = reader.readNext()) != null) {
				Resource service = m.createResource(NS + line[0]);
				
				if (line[1].compareTo("1") == 0) {
					service.addProperty(monday, "true", XSDDatatype.XSDboolean);
				} else {
					service.addProperty(monday, "false", XSDDatatype.XSDboolean);
				}
				if (line[2].compareTo("1") == 0) {
					service.addProperty(tuesday, "true", XSDDatatype.XSDboolean);
				} else {
					service.addProperty(tuesday, "false", XSDDatatype.XSDboolean);
				}
				if (line[3].compareTo("1") == 0) {
					service.addProperty(wednesday, "true", XSDDatatype.XSDboolean);
				} else {
					service.addProperty(wednesday, "false", XSDDatatype.XSDboolean);
				}
				if (line[4].compareTo("1") == 0) {
					service.addProperty(thursday, "true", XSDDatatype.XSDboolean);
				} else {
					service.addProperty(thursday, "false", XSDDatatype.XSDboolean);
				}
				if (line[5].compareTo("1") == 0) {
					service.addProperty(friday, "true", XSDDatatype.XSDboolean);
				} else {
					service.addProperty(friday, "false", XSDDatatype.XSDboolean);
				}
				if (line[6].compareTo("1") == 0) {
					service.addProperty(saturday, "true", XSDDatatype.XSDboolean);
				} else {
					service.addProperty(saturday, "false", XSDDatatype.XSDboolean);
				}
				if (line[7].compareTo("1") == 0) {
					service.addProperty(sunday, "true", XSDDatatype.XSDboolean);
				} else {
					service.addProperty(sunday, "false", XSDDatatype.XSDboolean);
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
