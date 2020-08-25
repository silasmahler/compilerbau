package de.compilerbau.NewAwkCompiler.Visitors;

import de.compilerbau.NewAwkCompiler.javacc21.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Visitors which build a symbol table for a Mapl AST.
 */
public class SymbolTableBuilderVisitor extends VisitorAdapter {

    private static final Logger log = LoggerFactory.getLogger(SymbolTableBuilderVisitor.class);

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
        log.error("Entering Class: " + node.getClass().getSimpleName() + "\n" +
                        "With Content:   " + node.toString() + "\n");
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


        //Symboltable-entry for method
        node.type = node.firstChildOfType(Type.class);
        node.parameterList = node.firstChildOfType(ParameterList.class);
        node.block = node.firstChildOfType(Block.class);


        data = node.childrenAccept(this, data);
        /*
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
        }*/
        return data;
    }

    @Override
    public Object visit(ParameterList node, Object data) {
        printEnter(node);

        //Empty Parameterlist return no action needed
        if (!node.hasChildNodes()) {
            System.out.println("Detected 0 Parameters");
            return data;
        }
        //There are parameters
        else {
            //Check how many commata to determine how many parameters
            int parameterCount = node.childrenOfType(COMMA.class).size() + 1;
            System.out.println("Detected " + parameterCount + " Parameters");
            //Get all Types
            List<Type> types = node.childrenOfType(Type.class);
            //Get all IDs
            List<ID> ids = node.childrenOfType(ID.class);
            //Marry them
            //Check if correct
            if (!(types.size() == ids.size()) && (types.size() == parameterCount)) {
                throw new TypeCheckingException("Something broke while checking Method Parameters." +
                        "Please declare it like: TYPE ID COMMA TYPE ID ...");
            }
            for (int i = 0; i < types.size(); i++) {
                node.parameterList.add(new Parameter(types.get(i), ids.get(i)));
            }

        }

        return data;
    }

    @Override
    public Object visit(Block node, Object data) {
        printEnter(node);

        return data;
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