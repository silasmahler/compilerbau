package de.compilerbau.NewAwkCompiler.Visitors;

public class TypeCheckingException extends RuntimeException {

    public TypeCheckingException() {
    }

    public TypeCheckingException(String msg) {
        super(msg);
    }

}
