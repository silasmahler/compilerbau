/* Generated by: JavaCC 21 Parser Generator. Do not edit. 
  * Generated Code for ID Token subclass
  * by the ASTToken.java.ftl template
  */
package de.compilerbau.NewAwkCompiler.javacc21;

import static de.compilerbau.NewAwkCompiler.javacc21.NewAwkConstants.TokenType.*;
@SuppressWarnings("unused")
public class ID extends Token {

    public ID(TokenType type, String image, String inputSource) {
        super(type, image, inputSource);
    }

    public ID(TokenType type, String image, FileLineMap fileLineMap) {
        super(type, image, fileLineMap);
    }


    @Override
    public String toString() {
        return ("ID{"
                + super.getImage() +
                "}");
    }
}
