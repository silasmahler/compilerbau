package de.compilerbau.NewAwkCompiler.Visitors;

import de.compilerbau.NewAwkCompiler.javacc21.*;

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
     *
     * @return the symbol table
     */
    public SymbolTable getSymTab() {
        return symbolTable;
    }

    //VariableDecl() | Assignement() |  VariableDeclAndAssignement() | MethodDecl()
    @Override
    public Object visit(CompilationUnit node, Object data) {
        System.out.println("Enter SymbolTableBuilderVisitor: visit.CompilationUnit \n" +
                "     Class: " + node.getClass().getSimpleName() + "\n" +
                "     Content: " + node.toString());
        data = node.childrenAccept(this, data);

        return data;
    }

    @Override
    public Object visit(VariableDecl node, Object data) {
        System.out.println("Enter SymbolTableBuilderVisitor: visit.VariableDecl \n" +
                "     Class: " + node.getClass().getSimpleName() + "\n" +
                "     Content: " + node.toString());

        System.out.println("First child: " + node.getFirstChild().getAttribute("type").toString());
        System.out.println("First ID: " + node.getChild(1).toString());
        System.out.println("First Last: " + node.getLastChild().toString());

            return data;
    }

    @Override
    public Object visit(Assignement node, Object data) {
        System.out.println("Enter SymbolTableBuilderVisitor: visit.Assignement \n" +
                "     Class: " + node.getClass().getSimpleName() + "\n" +
                "     Content: " + node.toString());
        return "42";
    }

    @Override
    public Object visit(VariableDeclAndAssignement node, Object data) {
        System.out.println("Enter SymbolTableBuilderVisitor: visit.VariableDeclAndAssignement \n" +
                "     Class: " + node.getClass().getSimpleName() + "\n" +
                "     Content: " + node.toString());
        return data;
    }

    /**
     * @param node
     * @param data
     * @return
     */
    @Override
    public Object visit(MethodDecl node, Object data) {
        System.out.println("Enter SymbolTableBuilderVisitor: visit.MethodDecl \n" +
                "     Class: " + node.getClass().getSimpleName() + "\n" +
                "     Content: " + node.toString());

        //Accept possible children, that are interesting for the table
        for (Node n : node.descendantsOfType(VariableDecl.class)) {
            n.jjtAccept(this, data);
        }
        for (Node n : node.descendantsOfType(VariableDeclAndAssignement.class)) {
            n.jjtAccept(this, data);
        }
        for (Node n : node.descendantsOfType(Assignement.class)) {
            Object s = n.jjtAccept(this, data);
            System.out.println("Data-Test (should be 42): " + s.toString());
        }
        return data;
    }

    //Return type to methoddecl
    @Override
    public Object visit(Type node, Object data) {
        System.out.println("Enter SymbolTableBuilderVisitor: visit.Type \n" +
                "    Class: " + node.getClass().getSimpleName() + "\n" +
                "    Content: " + node.toString());
        return data;
    }

    @Override
    public Object visit(Token node, Object data) {
        System.out.println("Enter SymbolTableBuilderVisitor: visit.Token \n" +
                "    Class: " + node.getClass().getSimpleName() + "\n" +
                "    Content: " + node.toString());
        return data;

    }
}