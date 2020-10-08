package de.compilerbau.NewAwkCompiler.Visitors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SemanticException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(SymbolTableBuilderVisitor.class);

    public SemanticException(String msg) {
        super(msg);
        log.error(msg);
    }

}
