package de.compilerbau.NewAwkCompiler;

import de.compilerbau.NewAwkCompiler.javacc21.NewAwkParser;
import de.compilerbau.NewAwkCompiler.javacc21.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
public class NewAwkCompilerApplication {

	public static void main(String[] args) {

		SpringApplication.run(NewAwkCompilerApplication.class, args);

		String[] sources = {"src/main/java/de/compilerbau/NewAwkCompiler/testfiles/NewAwkSimpleTest.txt"};
		try {
			NewAwkParser.main(sources);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
