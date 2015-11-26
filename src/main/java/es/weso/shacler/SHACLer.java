package es.weso.shacler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.Period;


import org.joda.time.format.PeriodFormat;

import es.weso.shacl.Schema;

public class SHACLer {
	static boolean verbose = false;

	public static void main(String... args) {
		Logger log = Logger.getLogger(SHACLer.class.getName());
		try {
			Instant start = Instant.now();
			ArgsParser options = new ArgsParser(args);
			
			verbose = options.verbose;
			
			switch (options.processor) {
			case "shex": 
				String contents = new String(Files.readAllBytes(Paths
						.get(options.schema)));
				
				Schema schema = Schema.fromString(contents, options.schemaFormat)
						.get()._1();
				
				String output = schema.serialize(options.outputSchemaFormat);
				
				System.out.print(output);
				break;
			case "shacl":
				System.out.print("SHACL");
				break;
			}
			
			
			Instant end = Instant.now();
			if (options.printTime) printTime(start,end);
			
		} catch (Exception e) {
			System.out.println("Exception raised: " + e.getMessage());
			e.printStackTrace();
		} 
		log.info("End of program");
	}

	static void wellcome() {
		verbose("Wellcome to SHCALer");
	}
	
	static void verbose(String msg) {
	  if (verbose) {
		System.out.println(msg);
	  }
	}
	
	static void printTime(Instant start,Instant end) {
		Period timeElapsed = new Period(start,end);
     	System.out.println("Time elapsed " + 
     			PeriodFormat.getDefault().print(timeElapsed));
	}

}
