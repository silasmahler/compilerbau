package de.compilerbau.NewAwkCompiler.Visitors;

import de.compilerbau.NewAwkCompiler.javacc21.*;

import java.util.List;

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

    public void printEnter(Node node) {
        System.out.println("Class: " + node.getClass().getSimpleName() + "\n" +
                "Content: " + node.toString() + "\n");
    }


    //VariableDecl() | Assignement() |  VariableDeclAndAssignement() | MethodDecl()
    @Override
    public Object visit(CompilationUnit node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);

        return data;
    }

    @Override
    public Object visit(VariableDecl node, Object data) {
        printEnter(node);

        System.out.println("First child: " + node.firstChildOfType(Type.class).type);
        System.out.println("First ID: " + node.getChild(1).toString());
        System.out.println("First Last: " + node.getLastChild());

        //Check syntax ok again (propably never reached parser checks this also)
        if (!(node.getLastChild() instanceof SEMICOLON)) {
            throw new TypeCheckingException("Missing semicolon after: "
                    + node.firstChildOfType(ID.class).getEndLine() + ":" + node.firstChildOfType(ID.class).getEndColumn());
        }
        //


        return data;
    }

    @Override
    public Object visit(Assignement node, Object data) {
        printEnter(node);
        //TODO Store Assignement-Data

        return data;
    }

    @Override
    public Object visit(VariableDeclAndAssignement node, Object data) {
        printEnter(node);
        return data;
    }

    /**
     * @param node
     * @param data
     * @return
     */
    @Override
    public Object visit(MethodDecl node, Object data) {
        printEnter(node);

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
        for (Node n : node.descendantsOfType(ParameterList.class)) {
            node.parameterList = (List<ParameterEntry>) n.jjtAccept(this, data);
            System.out.println("  - - - ParameterList: " + node.parameterList.toString());
        }
        return data;
    }

    @Override
    public List<ParameterEntry> visit(ParameterList node, Object data) {
        printEnter(node);

        return node.parameterList;
    }

    //Return type to methoddecl
    @Override
    public Object visit(Type node, Object data) {
        printEnter(node);
        return data;
    }

    @Override
    public Object visit(Token node, Object data) {
        printEnter(node);
        return data;

    }
}