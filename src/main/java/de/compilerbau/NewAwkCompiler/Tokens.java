package de.compilerbau.NewAwkCompiler;

import de.compilerbau.NewAwkCompiler.generated.NewAwkParser;
import de.compilerbau.NewAwkCompiler.generated.NewAwkParserConstants;
import de.compilerbau.NewAwkCompiler.generated.Token;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Tokens {

    public static void main(String[] args) throws FileNotFoundException {
        NewAwkParser parser = new NewAwkParser(new FileInputStream(new File(args[0])));

        for (Token token : tokenize(parser)) {
            String name = NewAwkParserConstants.tokenImage[token.kind];
            System.out.println(token.beginLine + ":" + name + " => " + token.image);
        }
    }

    public static List<Token> tokenize(NewAwkParser parser) throws FileNotFoundException {
        List<Token> tokens = new ArrayList<>();

        Token token = parser.getNextToken();
        while (token.kind != NewAwkParserConstants.EOF) {
            tokens.add(token);
            token = parser.getNextToken();
        }
        return tokens;
    }
}
