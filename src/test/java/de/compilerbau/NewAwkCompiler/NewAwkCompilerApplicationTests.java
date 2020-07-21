package de.compilerbau.NewAwkCompiler;

import de.compilerbau.NewAwkCompiler.generated.NewAwkParser;
import de.compilerbau.NewAwkCompiler.generated.Token;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.List;

@SpringBootTest
class NewAwkCompilerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public static List<Token> tokenize(String raw) {
		try {
			return Tokens.tokenize(new NewAwkParser(new StringReader(raw)));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
