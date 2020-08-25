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
        log.info("Entering Class: " + node.getClass().getSimpleName() + "\n" +
                "With Content:   " + node.toString());
    }

    //VariableDecl() | Assignement() |  VariableDeclAndAssignement() | MethodDecl()

    /**
     * Entrypoint, accepts all children
     * <p>
     * Production:
     * (   VariableDecl() |
     * Assignement() |
     * VariableDeclAndAssignement() |
     * MethodDecl()
     * )+
     * <EOF>
     */
    @Override
    public Object visit(CompilationUnit node, Object data) {
        printEnter(node);
        //Accepts the productions children (all relevant for typechecking)
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * Checks simple Variable Declarations
     * doesn't need to accept children for this usecase
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

        //Init with global context && Check if Method-Context
        String id = "";
        if (node.firstAncestorOfType(MethodDecl.class) != null) {
            id = node.firstAncestorOfType(MethodDecl.class).id.getImage();
        }

        if (symbolTable.checkAndInsertVariableDecl(node, id)) {
            log.info("SUCCESS: insertVariableDecl: Variable: " + node.toString());
        } else {
            throw new TypeCheckingException("Variable has already been declared in the same scope you cant declare " +
                    "it twice. Position: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                    + node.firstChildOfType(ID.class).getEndColumn());
        }

        return data;
    }

    /**
     *
     */
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

        //Init with global context && Check if Method-Context
        String id = "";
        if (node.firstAncestorOfType(MethodDecl.class) != null) {
            id = node.firstAncestorOfType(MethodDecl.class).id.getImage();
        }

        if (!symbolTable.isVariableDeclared(new VariableDecl(null, node.id), id)) {
            throw new TypeCheckingException("Used variable hasn't been declared in the same scope. Please declare it. " +
                    "Position of use: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                    + node.firstChildOfType(ID.class).getEndColumn());
        }
        //TODO Variable is declared, check if assignement is possible
        else {
            // TODO Check ExprStmnt von dort kommt die Info

        }

        //3 Check if assignement is possible


        return data;
    }

    /**
     *
     */
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


        //Init with global context && Check if Method-Context
        String id = "";
        if (node.firstAncestorOfType(MethodDecl.class) != null) {
            id = node.firstAncestorOfType(MethodDecl.class).id.getImage();
        }

        if (symbolTable.checkAndInsertVariableDecl(new VariableDecl(node.type, node.id), id)) {
            log.info("SUCCESS: insertVariableDecl: Variable: " + node.toString());
        } else {
            throw new TypeCheckingException("Variable has already been declared in the same scope you cant declare " +
                    "it twice. Position of first declaration: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                    + node.firstChildOfType(ID.class).getEndColumn());
        }
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(MethodDecl node, Object data) {
        printEnter(node);

        //Symboltable-entry for method
        node.type = node.firstChildOfType(Type.class);
        node.parameterList = node.firstChildOfType(ParameterList.class);
        node.block = node.firstChildOfType(Block.class);

        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(ParameterList node, Object data) {
        printEnter(node);

        //Empty Parameterlist return no action needed
        if (!node.hasChildNodes()) {
            log.info("Detected 0 Parameters");
            return data;
        }
        //There are parameters
        else {
            //Check how many commata to determine how many parameters
            int parameterCount = node.childrenOfType(COMMA.class).size() + 1;
            log.info("Detected " + parameterCount + " Parameters");
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
                Parameter p = new Parameter(types.get(i), ids.get(i));
                log.info("Add Parameter to ParameterList: " + p.toString());
                node.parameterList.add(p);
            }

        }

        return data;
    }


    /**
     * <BlockAuf> (Stmnt())+ <BlockZu>
     * => Only needs to accept Stmnt Statements, ignore other Terminals like "{" "}"
     */
    @Override
    public Object visit(Block node, Object data) {
        printEnter(node);
        //Accept children
        for (Node n : node.descendantsOfType(Stmnt.class)) {
            n.jjtAccept(this, data);
        }
        return data;
    }

    /**
     *
     (
     //LOOKAHEAD(3) ExprStmnt() |
     Block()
     | IfStmnt()
     | ReturnStmnt()
     | LOOKAHEAD(3) VariableDecl()
     | LOOKAHEAD(3) Assignement()
     | LOOKAHEAD(3) VariableDeclAndAssignement()
     | PrintStmnt()
     | LOOKAHEAD(3) KlammerAffe()
     )
     */
    /**
     * => No direct influence cause it only redirects to other methods
     */
    @Override
    public Object visit(Stmnt node, Object data) {
        printEnter(node);
        node.childrenAccept(this, data);
        return data;
    }

    /**
     * BaseNode not relevant for visitors
     */
    @Override
    public Object visit(BaseNode node, Object data) {
        printEnter(node);
        return data;
    }

    /**
     * Not needed for NewAwk, maybe later
     */
    @Override
    public Object visit(IfStmnt node, Object data) {
        printEnter(node);
        return data;
    }

    /**
     * Handles an expression
     * Production: Expr() ;
     */
    @Override
    public Object visit(ExprStmnt node, Object data) {
        printEnter(node);
        //TODO Type of Expr
        // Value
        // Wird verwendet bei Assignement und VariableDeclAndAssignement

        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(Expr node, Object data) {
        printEnter(node);
        return data;
    }

    /**
     *    [LOOKAHEAD(2) Cast()]
     *     (
     *         (
     *             LOOKAHEAD(2) MethodCall()
     *             | LOOKAHEAD(3) t=<ID>
     *             [".length"
     *             {jjtThis.hasLength = true;
     *              jjtThis.atomLength = t.getImage().length();}
     *             ]
     *         ) [ArrayAccess()]
     *         | <KlammerAuf> Expr() <KlammerZu>
     *         | t=<BooleanLiteral>
     *         | t=<IntegerLiteral>
     *         | t=<DoubleLiteral>
     *         | t=<CharLiteral>
     *         | t=<NullLiteral>
     *         | LOOKAHEAD(3) t=<StringLiteral>
     *             [".length"
     *                {jjtThis.hasLength = true;
     *                jjtThis.atomLength = t.getImage().length();}
     *             ]
     *     )
     */
    @Override
    public Object visit(Atom node, Object data) {
        printEnter(node);

        // TODO Check if Cast
        if(node.getFirstChild() instanceof Cast){
            Type castType = node.getFirstChild().firstChildOfType(Type.class);
        }


        // TODO Checking for .length, wenn vorhanden, dann umwandlung zu Integertyp
        if (node.hasLength) {
            log.info("Found Atom with \".length\" with " + node.children().size() + " children.");
            // id.lenght => Integer
            if (node.getFirstChild() instanceof ID) {

                // Check if ID contains String or Array
                // TODO Check symbol table for ID and if String or Array

                // TODO if found = no exception

                // TODO Get length from in symbol table stored assignements for ID

            }
            // "String".length => Integer
            else if (node.getFirstChild() instanceof StringLiteral) {
                //String has definitive length
                int length = ((StringLiteral) node.getFirstChild()).getImage().length();
                // Now we know the representation => remove children and put int in
                node.clearChildren();
                IntegerLiteral integerLiteral = new IntegerLiteral();
                integerLiteral.setIntValue(length);
                node.addChild(integerLiteral);
            } else {
                throw new TypeCheckingException("The type can't have \".length\" representation: " +
                        "Error at: "
                        + node.getFirstChild().getEndLine() + ":"
                        + node.getFirstChild().getEndColumn());
            }
        }


        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(Cast node, Object data) {
        printEnter(node);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(MethodCall node, Object data) {
        printEnter(node);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(ArrayAccess node, Object data) {
        printEnter(node);
        return data;
    }


    /**
     *
     */
    @Override
    public Object visit(ReturnStatement node, Object data) {
        printEnter(node);
        // TODO Get return-type from method context, if we are in global => error
        // TODO Check if matches return value (can be expression, variable, plain value, etc...)
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(KlammerAffe node, Object data) {
        printEnter(node);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(KlammerAffeRegex node, Object data) {
        printEnter(node);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(KlammerAffeAusdruck node, Object data) {
        printEnter(node);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(PrintStmnt node, Object data) {
        printEnter(node);
        node.childrenAccept(this, data);
        return data;
    }

    /**
     * Type has only terminals, so it cant have childs to accept.
     */
    @Override
    public Object visit(Type node, Object data) {
        printEnter(node);
        return data;
    }

    /**
     * Only Terminal of Type "Token" we recognize is EOF, therefore no
     * childs to accept as well
     */
    @Override
    public Object visit(Token node, Object data) {
        printEnter(node);
        return data;

    }
}