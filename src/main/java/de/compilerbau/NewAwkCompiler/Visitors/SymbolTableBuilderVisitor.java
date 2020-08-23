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

    /**
     * Checks if Variable Declarations are made correct
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
        //2 Put it in the table
        //  Important: Scope of Decl (which method or outside, and line-nr (provided by Base-Node))

        //2.1   We arent in a method, so just declarations in order,
        //      declarations only need to check if the variable already has been declared

        //If we are in a Method
        if(node.getParent() instanceof MethodDecl){
            //TODO Insert (call Insert-Method on symboltable with boolean return if it was successfull)
            if(symbolTable.insertVariableDecl(node, ((MethodDecl) node.getParent()).id)){
                System.out.println("insertVariableDecl: Success in Method: Variable: " + node.toString());            }
            else {
                throw new TypeCheckingException("Variable has already been declared in the same scope you cant declare " +
                        "it twice. Position: "  + node.firstChildOfType(ID.class).getEndLine() + ":"
                        + node.firstChildOfType(ID.class).getEndColumn());
            }
        }
        else {
            if(symbolTable.insertVariableDecl(node, null)){
                System.out.println("insertVariableDecl: Success: Variable: " + node.toString());
            }
            else {
                throw new TypeCheckingException("Variable has already been declared in the same scope you cant declare " +
                        "it twice. Position: "  + node.firstChildOfType(ID.class).getEndLine() + ":"
                        + node.firstChildOfType(ID.class).getEndColumn());
            }        }
        return data;
    }

    @Override
    public Object visit(Assignement node, Object data) {
        printEnter(node);
        //Check syntax ok again (propably never reached parser checks this also)
        if (!(node.getLastChild() instanceof SEMICOLON)) {
            throw new TypeCheckingException("Missing semicolon after: "
                    + node.firstChildOfType(ID.class).getEndLine() + ":" + node.firstChildOfType(ID.class).getEndColumn());
        }

        //1 Fill up VariableDecl-Object with needed subtypes
        node.id = node.firstChildOfType(ID.class);
        node.exprStmnt = node.firstChildOfType(ExprStmnt.class);

        //2 Check if id has been declared

            //If we are in a Method
            if(node.getParent() instanceof MethodDecl){
                MethodDecl methodDecl = (MethodDecl) node.getParent();
                List<VariableDecl> decls = symbolTable.getVariableDeclTable().get(methodDecl.id);
                if(!decls.contains(node.id)){
                    throw new TypeCheckingException("Used variable hasn't been declared in the same scope. Please declare it. " +
                            "Position of use: "  + node.firstChildOfType(ID.class).getEndLine() + ":"
                            + node.firstChildOfType(ID.class).getEndColumn());
                }
                else {
                    
                }
            }
            //If we are in global Context
            else {
                List<VariableDecl> decls = symbolTable.getVariableDeclTable().get("");
                if (!decls.contains(node.id)) {
                    throw new TypeCheckingException("Used variable hasn't been declared in the same scope. Please declare it. " +
                            "Position of use: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                            + node.firstChildOfType(ID.class).getEndColumn());
                }
                else {

                }

            }



        //3 Check if assignement is possible


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