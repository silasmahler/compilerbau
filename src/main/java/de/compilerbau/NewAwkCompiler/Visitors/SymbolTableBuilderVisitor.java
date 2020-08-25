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
        System.out.println("------------------------- \n " +
                "Entering Class: " + node.getClass().getSimpleName() + "\n" +
                "With Content:   " + node.toString() + "\n" +
                "-------------------------");
    }


    //VariableDecl() | Assignement() |  VariableDeclAndAssignement() | MethodDecl()
    @Override
    public Object visit(CompilationUnit node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);

        return data;
    }

    /**
     * Checks if Variable Declarations are made correct
     *
     * @param node
     * @param data
     * @return
     */
    @Override
    public Object visit(VariableDecl node, Object data) {
        printEnter(node);

        //Check syntax ok again (propably never reached parser checks this also)
        if (!(node.getLastChild() instanceof SEMICOLON)) {
            throw new TypeCheckingException("Missing semicolon after: "
                    + node.firstChildOfType(ID.class).getEndLine() + ":" + node.firstChildOfType(ID.class).getEndColumn());
        }

        //1 Fill up VariableDecl-Object with needed subtypes
        node.type = node.firstChildOfType(Type.class);
        node.id = node.firstChildOfType(ID.class);

        //If we are in a Method
        if (node.firstAncestorOfType(MethodDecl.class) != null) {
            String id = (node.firstAncestorOfType(MethodDecl.class)).id.getImage();
            if (symbolTable.checkAndInsertVariableDecl(node, id)) {
                System.out.println("insertVariableDecl: Success in Method: Variable: " + node.toString());
            } else {
                throw new TypeCheckingException("Variable has already been declared in the same scope you cant declare " +
                        "it twice. Position: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                        + node.firstChildOfType(ID.class).getEndColumn());
            }
        } else {
            if (symbolTable.checkAndInsertVariableDecl(node, "")) {
                System.out.println("insertVariableDecl: Success in Global: Variable: " + node.toString());
            } else {
                throw new TypeCheckingException("Variable has already been declared in the same scope you cant declare " +
                        "it twice. Position: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                        + node.firstChildOfType(ID.class).getEndColumn());
            }
        }
        return data;
    }

    @Override
    public Object visit(Assignement node, Object data) {
        printEnter(node);

        //Check syntax ok again (propably never reached parser checks this also)
        if (!(node.getLastChild().getLastChild() instanceof SEMICOLON)) {
            throw new TypeCheckingException("Missing semicolon after: "
                    + node.firstChildOfType(ID.class).getEndLine() + ":" + node.firstChildOfType(ID.class).getEndColumn());
        }

        //1 Fill up VariableDecl-Object with needed subtypes
        node.id = node.firstChildOfType(ID.class);
        node.exprStmnt = node.firstChildOfType(ExprStmnt.class);

        //If we are in a Method
        if (node.firstAncestorOfType(MethodDecl.class) != null) {
            String id = (node.firstAncestorOfType(MethodDecl.class)).id.getImage();
            if (!symbolTable.isVariableDeclared(new VariableDecl(null, node.id), id)) {
                throw new TypeCheckingException("Used variable hasn't been declared in the same scope. Please declare it. " +
                        "Position of use: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                        + node.firstChildOfType(ID.class).getEndColumn());
            }
            //Variable is declared, check if assignement is possible
            else {

            }
        }
        //If we are in global Context
        else {
            if (!symbolTable.isVariableDeclared(new VariableDecl(null, node.id), "")) {
                throw new TypeCheckingException("Used variable hasn't been declared in the same scope. Please declare it. " +
                        "Position of use: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                        + node.firstChildOfType(ID.class).getEndColumn());
            }
            //Variable is declared, check if assignement is possible
            else {

            }

        }


        //3 Check if assignement is possible


        return data;
    }

    @Override
    public Object visit(VariableDeclAndAssignement node, Object data) {
        printEnter(node);

        //Check syntax ok again (propably never reached parser checks this also)
        if (!(node.getLastChild().getLastChild() instanceof SEMICOLON)) {
            throw new TypeCheckingException("Missing semicolon after: "
                    + node.firstChildOfType(ID.class).getEndLine() + ":" + node.firstChildOfType(ID.class).getEndColumn());
        }

        //1 Fill up VariableDecl-Object with needed subtypes
        node.type = node.firstChildOfType(Type.class);
        node.id = node.firstChildOfType(ID.class);
        node.exprStmnt = node.firstChildOfType(ExprStmnt.class);

        //If we are in a Method
        if (node.firstAncestorOfType(MethodDecl.class) != null) {
            String id = (node.firstAncestorOfType(MethodDecl.class)).id.getImage();
            if (symbolTable.checkAndInsertVariableDecl(new VariableDecl(node.type, node.id), id)) {
                System.out.println("insertVariableDecl: Success in Method: Variable: " + node.toString());
            } else {
                throw new TypeCheckingException("Variable has already been declared in the same method scope you cant declare " +
                        "it twice. Position of first declaration: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                        + node.firstChildOfType(ID.class).getEndColumn());
            }
        } else {
            if (symbolTable.checkAndInsertVariableDecl(new VariableDecl(node.type, node.id), "")) {
                System.out.println("insertVariableDecl: Success in Global: Variable: " + node.toString());
            } else {
                throw new TypeCheckingException("Variable has already been declared in the global scope you cant declare " +
                        "it twice. Position of first declaration: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                        + node.firstChildOfType(ID.class).getEndColumn());
            }
        }
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
            n.jjtAccept(this, data);
        }
        for (Node n : node.descendantsOfType(ParameterList.class)) {
            n.jjtAccept(this, data);
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