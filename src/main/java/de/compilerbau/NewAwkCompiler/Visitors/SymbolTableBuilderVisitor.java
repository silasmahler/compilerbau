package de.compilerbau.NewAwkCompiler.Visitors;

import de.compilerbau.NewAwkCompiler.javacc21.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Visitors which build a symbol table for a Mapl AST.
 */
public class SymbolTableBuilderVisitor extends VisitorAdapter {

    private static final Logger log = LoggerFactory.getLogger(SymbolTableBuilderVisitor.class);

    private SymbolTable symbolTable;
    private Utils utils;

    /**
     * Initialise a new symbol table builder.
     */
    public SymbolTableBuilderVisitor() {
        symbolTable = new SymbolTable();
        utils = new Utils();
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

        //1 Fill Object with needed subtypes
        node.type = node.firstChildOfType(Type.class);
        node.id = node.firstChildOfType(ID.class);

        //Init with global context && Check if Method-Context
        String contextId = "";
        if (node.firstAncestorOfType(MethodDecl.class) != null) {
            contextId = node.firstAncestorOfType(MethodDecl.class).id.getImage();
        }

        if (symbolTable.checkAndInsertVariableDecl(node, contextId)) {
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

        //1 Fill up Object with needed subtypes
        node.id = node.firstChildOfType(ID.class);
        node.exprStmnt = node.firstChildOfType(ExprStmnt.class);

        //Init with global context && Check if Method-Context
        String contextId = "";
        if (node.firstAncestorOfType(MethodDecl.class) != null) {
            contextId = node.firstAncestorOfType(MethodDecl.class).id.getImage();
        }
        //Check if declaration possible, if not, throw error
        if (!symbolTable.isVariableDeclared(new VariableDecl(null, node.id), contextId)) {
            throw new TypeCheckingException("Used variable hasn't been declared in the same scope. Please declare it. " +
                    "Position of use: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                    + node.firstChildOfType(ID.class).getEndColumn());
        }
        //TODO Variable is declared, check if assignement is possible
        // We know Variable could be declared
        //
        else {
            log.info("Variable is declared, checking assignement possible");
            // TODO Check ExprStmnt von dort kommt die Info

            List<VariableDecl> decls = symbolTable.getVariableDeclsForContext(contextId);
            Optional<VariableDecl> variableDecl = null;
            if (decls != null) {
                variableDecl = decls.stream().filter(o -> o.id.getImage().equals(node.id.getImage())).findFirst();
            }

            if (utils.checkTypeIsEqual(variableDecl.get().type, node.exprStmnt.type)) {
                log.info("Assignement-Types are equal!");
            }
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
        node.childrenAccept(this,data);

        //1 Fill up Object with needed subtypes
        node.type = node.firstChildOfType(Type.class);
        node.id = node.firstChildOfType(ID.class);
        node.exprStmnt = node.firstChildOfType(ExprStmnt.class);


        //Init with global context && Check if Method-Context
        String contextId = "";
        if (node.firstAncestorOfType(MethodDecl.class) != null) {
            contextId = node.firstAncestorOfType(MethodDecl.class).id.getImage();
        }

        if (!symbolTable.checkAndInsertVariableDecl(new VariableDecl(node.type, node.id), contextId)) {
            throw new TypeCheckingException("Variable has already been declared in the same scope you cant declare " +
                    "it twice. Position of first declaration: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                    + node.firstChildOfType(ID.class).getEndColumn());
        } else {
            log.info("SUCCESS: insertVariableDecl: Variable: " + node.toString());
            // Now check if Assignement possible

            // TODO check types and assignement
            if (utils.checkTypeIsEqual(node.type, node.exprStmnt.type)) {

            }


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
        node.childrenAccept(this, data);

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
        data = node.childrenAccept(this, data);
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
        node.childrenAccept(this, data);
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
        node.childrenAccept(this, data);

        return data;
    }


    @Override
    public Object visit(LogicalOrExpr node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);
        // Operatoren auf boolean: &&, ||, !
        int childsCount = node.children().size();
        //If 1 child: pass this
        if (childsCount == 1) {
            node.type = ((LogicalAndExpr) node.getFirstChild()).type;
            node.value = ((LogicalAndExpr) node.getFirstChild()).value;
            return data;
        } else {


            //Start operation with (Token || Token || Token ...)
            for (int i = 0; i < childsCount; i++) {
                //if any == true return true else false
                List<LogicalAndExpr> logicalAndExprs = node.childrenOfType(LogicalAndExpr.class);
                // Check if any none boolean Type -> then it should be only 1 value -> pass it up or throw diff
                if (logicalAndExprs.stream().filter(child -> !child.type.type.equals("boolean")).findAny().isPresent()
                ) {
                    throw new TypeCheckingException("Inconsistent Types in LogicalOrExpression.  Please only use boolean.");
                }
                //All boolean
                else {
                    //Any of the boolean = true, pass true up
                    if (logicalAndExprs.stream().filter(child -> (child.value.equals("true"))
                    ).findAny().isPresent()) {
                        node.type = new Type("boolean");
                        node.value = "true";
                        return data;
                    }
                    //All of the boolean = false, pass false up
                    else {
                        node.type = new Type("boolean");
                        node.value = "false";
                        return data;
                    }
                }

            }
        }
        return data;
    }

    @Override
    public Object visit(LogicalAndExpr node, Object data) {
        // Operatoren auf boolean: &&, ||, !
        data = node.childrenAccept(this, data);

        return data;
    }

    @Override
    public Object visit(LogicalNotExpr node, Object data) {
        // Operatoren auf boolean: &&, ||, !
        // Check if childs boolean -> turn around bool and save
        // else just pass up values
        data = node.childrenAccept(this, data);

        return super.visit(node, data);
    }

    @Override
    public Object visit(CompExpr node, Object data) {
        // Vergleichsoperationen: ==, >=, !=, <=, <, >
        // Alle Datentypen
        return super.visit(node, data);
    }

    @Override
    public Object visit(Sum node, Object data) {
        printEnter(node);
        // Operatoren auf int, double, char: +, -,

        // 1. Check how many childs
        int childsCount = node.childrenOfType(PLUS.class).size() + node.childrenOfType(MINUS.class).size() + 1;

        // If 1 no operation required, just hand it up with type and value
        // Type is not important here
        if (childsCount == 1) {
            node.type = node.childrenOfType(Product.class).get(0).type;
            node.value = node.childrenOfType(Product.class).get(0).value;
            data = node.childrenAccept(this, data);
            return data;
        }

        // If more, do the operation
        // First check types, then values later (symboltable not needed (Arrays = Atoms :))
        List<Node> childs = node.children();
        int stepsToOperate = node.children().size();
        //Init with first value
        Sum sum = new Sum();
        sum.type = ((Product) childs.get(0)).type;
        sum.value = ((Product) childs.get(0)).value;
        //Start operation at 2nd value (Token + Token - Token + ...)
        for (int i = 2; i < stepsToOperate; i += 2) {
            // 1. Check Types of next 2 operands
            // If same type, operate, same result type (no change)
            // Exception: char + char = int

            // 1. EQUAL TYPES?
            if (sum.type.type.equals(((Product) childs.get(i)).type.type)) {
                // 1.1 INT
                if (sum.type.type.equals("int")) {
                    if (childs.get(i - 1) instanceof PLUS) {
                        sum.value = "" +
                                (Integer.parseInt(sum.value)
                                        + Integer.parseInt(((Product) childs.get(i)).value)
                                );
                    } else if (childs.get(i - 1) instanceof MINUS) {
                        sum.value = "" +
                                (Integer.parseInt(sum.value)
                                        - Integer.parseInt(((Product) childs.get(i)).value)
                                );
                    } else {
                        throw new TypeCheckingException("Operation on sum with same types went wrong.");
                    }
                }
                // 1.2 DOUBLE
                if (sum.type.type.equals("double")) {
                    if (childs.get(i - 1) instanceof PLUS) {
                        sum.value = "" +
                                (Double.parseDouble(sum.value)
                                        + Double.parseDouble(((Product) childs.get(i)).value)
                                );
                    } else if (childs.get(i - 1) instanceof MINUS) {
                        sum.value = "" +
                                (Double.parseDouble((sum.value))
                                        - Double.parseDouble(((Product) childs.get(i)).value)
                                );
                    } else {
                        throw new TypeCheckingException("Operation on sum with same types went wrong.");
                    }
                }
                // 1.3 CHAR
                if (sum.type.type.equals("char")) {
                    if (childs.get(i - 1) instanceof PLUS) {
                        sum.value = "" +
                                (
                                        sum.value.charAt(0)
                                                + ((Product) childs.get(i)).value.charAt(0)
                                );
                        sum.type = new Type("int");
                    } else if (childs.get(i - 1) instanceof MINUS) {
                        sum.value = "" +
                                (
                                        sum.value.charAt(0)
                                                - ((Product) childs.get(i)).value.charAt(0)
                                );
                        sum.type = new Type("int");
                    } else {
                        throw new TypeCheckingException("Operation on sum with same types went wrong.");
                    }
                }
            }
            // 2. NOT EQUAL TYPES
            else {
                // TODO 1st Type is precedence, second needs to follow
                // int + double => double; double + int => double
                // char + int = int; int + char => int
                // double + char => double; char + double => double
            }
        }

        // 2. Check childs types => try to calculate a return type
        // e.g. for 1 + 2 + 3
        //      int int int = int;
        //      int double int = double
        //      char + int = char
        //      char + double = Error
        // 3. Try left to right sum (e.g. 1+2+3 => 1+2=3; 3+3=6;
        // Immer eine Auswertung links nach rechts dann die nächste


        return super.visit(node, data);
    }

    @Override
    public Object visit(Product node, Object data) {
        // Operatoren auf int, double, char: *, /, %
        // Diese Node deckt ab: *, /, %
        return super.visit(node, data);
    }

    @Override
    public Object visit(Sign node, Object data) {
        return super.visit(node, data);
    }

    /**
     * [LOOKAHEAD(2) Cast()]
     * (
     * (
     * LOOKAHEAD(2) MethodCall()
     * | LOOKAHEAD(3) t=<ID>
     * [".length"
     * {jjtThis.hasLength = true;
     * jjtThis.atomLength = t.getImage().length();}
     * ]
     * ) [ArrayAccess()]
     * | <KlammerAuf> Expr() <KlammerZu>
     * | t=<BooleanLiteral>
     * | t=<IntegerLiteral>
     * | t=<DoubleLiteral>
     * | t=<CharLiteral>
     * | t=<NullLiteral>
     * | LOOKAHEAD(3) t=<StringLiteral>
     * [".length"
     * {jjtThis.hasLength = true;
     * jjtThis.atomLength = t.getImage().length();}
     * ]
     * )
     */
    @Override
    public Object visit(Atom node, Object data) {
        printEnter(node);

        // TODO Check if Cast
        if (node.getFirstChild() instanceof Cast) {
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
        data = node.childrenAccept(this, data);
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