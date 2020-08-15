package de.compilerbau.NewAwkCompiler.Visitors;

/**
 * Visitors which build a symbol table for a Mapl AST.
 */
public class SymbolTableBuilderVisitor extends VisitorAdapter {

    private SymbolTable symbolTable;

    /**
     * Initialise a new symbol table builder.
     */
    public SymbolTableBuilderVisitor() {
        symbolTable = new SymbolTable();
    }

    /**
     * The symbol table which has been built so far.
     * @return the symbol table
     */
    public SymbolTable getSymTab() {
        return symbolTable;
    }

    // ProcDecl pd;
    // List<MethodDecl> mds;

}