package de.compilerbau.NewAwkCompiler;

import de.compilerbau.NewAwkCompiler.Visitors.SymbolTableBuilderVisitor;
import de.compilerbau.NewAwkCompiler.javacc21.NewAwkParser;
import de.compilerbau.NewAwkCompiler.javacc21.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class NewAwkCompilerApplication {
	private static final Logger log = LoggerFactory.getLogger(NewAwkCompilerApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(NewAwkCompilerApplication.class, args);

		log.info("Arguments-Count: " + args.length);
		String[] arguments = new String[args.length];
		for(int i = 0; i < args.length; i++) {
			arguments[i] = args[i];
		}
		log.info("Arguments: " + Arrays.deepToString(arguments));

		String path = "src/main/java/de/compilerbau/NewAwkCompiler/testfiles/";
		String txt = ".txt";
		String[] sources = {
				path + "Test_Fail_ArrayIndexAccessError" + txt,
				path + "Test_Fail_Arrays3IndexBooleanAccess" + txt,
				path + "Test_Fail_CompExpr" + txt,
				path + "Test_Fail_KlammerAffe4" + txt,
				path + "Test_Fail_LogicalNot" + txt,
				path + "Test_Fail_LogicalNotUsedInNonBool" + txt,
				path + "Test_Fail_LogicalOr" + txt,
				path + "Test_Fail_NotBoxableTypeError" + txt,
				path + "Test_Fail_TypesNotAllowedInOperation" + txt,
				path + "Test_Success_AddNumbers" + txt,
				path + "Test_Success_Arrays1" + txt,
				path + "Test_Success_Arrays2" + txt,
				path + "Test_Success_CompExpr" + txt,
				path + "Test_Success_isDoubleAndLength" + txt,
				path + "Test_Success_KlammerAffe1" + txt,
				path + "Test_Success_KlammerAffe2" + txt,
				path + "Test_Success_KlammerAffe3" + txt,
				path + "Test_Success_KlammerAffe4" + txt,
				path + "Test_Success_KlammerAffe5StringRegex" + txt,
				path + "Test_Success_KlammerAffe5StringRegexNegated" + txt,
				path + "Test_Success_LogicalNot" + txt,
				path + "Test_Success_LogicalOr" + txt,
				path + "Test_Success_MethodParameters" + txt,
				path + "Test_Success_NextMethods" + txt,
				path + "Test_Success_PrintlnStmnt" + txt,
				path + "Test_Success_ReturnStatement" + txt,
				path + "Test_Success_SimpleDeclarationsAndOps" + txt,
				path + "Test_Success_SimpleDeclarationsAndOps2" + txt,
				path + "NewAwkSimpleTest" + txt,
		};
		String[] sources2 = {path + "NewAwkSimpleTest" + txt};
		try {
			NewAwkParser.main(arguments);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
