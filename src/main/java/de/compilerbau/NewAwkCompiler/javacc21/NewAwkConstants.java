/* Generated by: JavaCC 21 Parser Generator. Do not edit. NewAwkConstants.java */
package de.compilerbau.NewAwkCompiler.javacc21;

/**
 * Token literal values and constants.
 */
public interface NewAwkConstants {
    public enum TokenType {
        EOF, WHITESPACE, TAB, NEW_LINE, CARRIAGE_RETURN, PLUS, MINUS, MULTIPLICATION, DIVISION, MODULO, Zuweisung, EQUAL, NOT_EQUAL, G_OR_EQUAL, S_OR_EQUAL, GREATER, SMALLER, Colon, ConditionalAnd, ConditionalOr, ConditionalNot, Exponentiation, RETURN, VOID, NullLiteral, IF, ELSE, KlammerAuf, KlammerZu, BlockAuf, BlockZu, ArrayAuf, ArrayZu, SEMICOLON, COMMA, DOT, Apostrophe, DataType, LETTER, DIGIT, EXPO, TRUE, FALSE, BooleanValue, CharLiteral, IntegerLiteral, DoubleLiteral, Bezeichner, StringLiteral, STRING_CONTENT, INVALID
    }
    /**
   * Lexical States
   */
    public enum LexicalState {
        DEFAULT, 
    }
    String[] tokenImage= {"<EOF>", "\" \"", "\"\\t\"", "\"\\n\"", "\"\\r\"", "\"+\"", "\"-\"", "\"*\"", "\"/\"", "\"%\"", "\"=\"", "\"==\"", "\"!=\"", "\">=\"", "\"<=\"", "\">\"", "\"<\"", "\":\"", "\"&&\"", "\"||\"", "\"!\"", "\"^\"", "\"return\"", "\"void\"", "\"null\"", "\"if\"", "\"else\"", "\"(\"", "\")\"", "\"{\"", "\"}\"", "\"[\"", "\"]\"", "\";\"", "\",\"", "\".\"", "\"\\\'\"", "<DataType>", "<LETTER>", "<DIGIT>", "<EXPO>", "\"true\"", "\"false\"", "<BooleanValue>", "<CharLiteral>", "<IntegerLiteral>", "<DoubleLiteral>", "<Bezeichner>", "<StringLiteral>", "<STRING_CONTENT>", };
}
