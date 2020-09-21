/* Generated by: JavaCC 21 Parser Generator. Do not edit. NewAwkConstants.java */
package de.compilerbau.NewAwkCompiler.javacc21;

/**
 * Token literal values and constants.
 */
public interface NewAwkConstants {
    public enum TokenType {
        EOF, WHITESPACE, TAB, NEW_LINE, CARRIAGE_RETURN, PLUS, MINUS, MULTIPLICATION, DIVISION, MODULO, Zuweisung, EQUAL, NOT_EQUAL, G_OR_EQUAL, S_OR_EQUAL, GREATER, SMALLER, Colon, ConditionalAnd, ConditionalOr, ConditionalNot, Exponentiation, RETURN, VOID, NullLiteral, IF, ELSE, THIS, INTEGER_CLASS, DOUBLE_CLASS, CHAR_CLASS, BOOLEAN_CLASS, BEGIN, END, KlammerAuf, KlammerZu, BlockAuf, BlockZu, ArrayAuf, ArrayZu, SEMICOLON, COMMA, DOT, Apostrophe, AT, TypeInt, TypeDouble, TypeChar, TypeBoolean, TypeString, LETTER, DIGIT, EXPO, TRUE, FALSE, BooleanLiteral, CharLiteral, IntegerLiteral, DoubleLiteral, ID, StringLiteral, STRING_CONTENT, PRINT_LINE, LENGTH, PRINT, NEXT, NEXT_INT, NEXT_DOUBLE, NEXT_CHAR, NEXT_BOOLEAN, NEXT_STRING, _TOKEN_71, _TOKEN_72, _TOKEN_73, _TOKEN_74, _TOKEN_75, INVALID
    }
    /**
   * Lexical States
   */
    public enum LexicalState {
        DEFAULT, 
    }
    String[] tokenImage= {"<EOF>", "\" \"", "\"\\t\"", "\"\\n\"", "\"\\r\"", "\"+\"", "\"-\"", "\"*\"", "\"/\"", "\"%\"", "\"=\"", "\"==\"", "\"!=\"", "\">=\"", "\"<=\"", "\">\"", "\"<\"", "\":\"", "\"&&\"", "\"||\"", "\"!\"", "\"^\"", "\"return\"", "\"void\"", "\"null\"", "\"if\"", "\"else\"", "\"this\"", "\"Integer\"", "\"Double\"", "\"Char\"", "\"Boolean\"", "\"Begin\"", "\"End\"", "\"(\"", "\")\"", "\"{\"", "\"}\"", "\"[\"", "\"]\"", "\";\"", "\",\"", "\".\"", "\"\\\'\"", "\"@\"", "\"int\"", "\"double\"", "\"char\"", "\"boolean\"", "\"String\"", "<LETTER>", "<DIGIT>", "<EXPO>", "\"true\"", "\"false\"", "<BooleanLiteral>", "<CharLiteral>", "<IntegerLiteral>", "<DoubleLiteral>", "<ID>", "<StringLiteral>", "<STRING_CONTENT>", "\"System.out.println\"", "\"length()\"", "\"System.out.print\"", "\"System.next\"", "\"System.nextInt\"", "\"System.nextDouble\"", "\"System.nextChar\"", "\"System.nextBoolean\"", "\"System.nextString\"", "\".length()\"", "\".isInt()\"", "\".isDouble()\"", "\".toInt()\"", "\".toDouble()\"", };
}
