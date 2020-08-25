package de.compilerbau.NewAwkCompiler.Visitors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisitorException extends RuntimeException{

    private static final Logger log = LoggerFactory.getLogger(SymbolTableBuilderVisitor.class);


    public VisitorException() {
    }

    public VisitorException(String msg) {
        super(msg);
        log.error(msg);
    }
}
