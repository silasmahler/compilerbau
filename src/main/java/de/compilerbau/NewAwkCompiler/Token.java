package de.compilerbau.NewAwkCompiler;

public class Token {
    enum Type { EOF, ERROR };
    Type type;
    String content;
    SourcePosition startPosition;
    SourcePosition endPosition;
}