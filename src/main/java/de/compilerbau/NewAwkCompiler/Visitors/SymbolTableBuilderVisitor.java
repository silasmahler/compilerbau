package de.compilerbau.NewAwkCompiler.Visitors;

import de.compilerbau.NewAwkCompiler.javacc21.ArrayAccess;
import de.compilerbau.NewAwkCompiler.javacc21.Assignement;
import de.compilerbau.NewAwkCompiler.javacc21.Atom;
import de.compilerbau.NewAwkCompiler.javacc21.BaseNode;
import de.compilerbau.NewAwkCompiler.javacc21.Block;
import de.compilerbau.NewAwkCompiler.javacc21.BooleanLiteral;
import de.compilerbau.NewAwkCompiler.javacc21.COMMA;
import de.compilerbau.NewAwkCompiler.javacc21.Cast;
import de.compilerbau.NewAwkCompiler.javacc21.CharLiteral;
import de.compilerbau.NewAwkCompiler.javacc21.CompExpr;
import de.compilerbau.NewAwkCompiler.javacc21.CompilationUnit;
import de.compilerbau.NewAwkCompiler.javacc21.DIVISION;
import de.compilerbau.NewAwkCompiler.javacc21.DoubleLiteral;
import de.compilerbau.NewAwkCompiler.javacc21.Expr;
import de.compilerbau.NewAwkCompiler.javacc21.ExprStmnt;
import de.compilerbau.NewAwkCompiler.javacc21.ID;
import de.compilerbau.NewAwkCompiler.javacc21.IfStmnt;
import de.compilerbau.NewAwkCompiler.javacc21.IntegerLiteral;
import de.compilerbau.NewAwkCompiler.javacc21.KlammerAffe;
import de.compilerbau.NewAwkCompiler.javacc21.KlammerAffeAusdruck;
import de.compilerbau.NewAwkCompiler.javacc21.KlammerAffeRegex;
import de.compilerbau.NewAwkCompiler.javacc21.KlammerAuf;
import de.compilerbau.NewAwkCompiler.javacc21.KlammerZu;
import de.compilerbau.NewAwkCompiler.javacc21.LogicalAndExpr;
import de.compilerbau.NewAwkCompiler.javacc21.LogicalNotExpr;
import de.compilerbau.NewAwkCompiler.javacc21.LogicalOrExpr;
import de.compilerbau.NewAwkCompiler.javacc21.MINUS;
import de.compilerbau.NewAwkCompiler.javacc21.MODULO;
import de.compilerbau.NewAwkCompiler.javacc21.MULTIPLICATION;
import de.compilerbau.NewAwkCompiler.javacc21.MethodCall;
import de.compilerbau.NewAwkCompiler.javacc21.MethodDecl;
import de.compilerbau.NewAwkCompiler.javacc21.Node;
import de.compilerbau.NewAwkCompiler.javacc21.NullLiteral;
import de.compilerbau.NewAwkCompiler.javacc21.PLUS;
import de.compilerbau.NewAwkCompiler.javacc21.Parameter;
import de.compilerbau.NewAwkCompiler.javacc21.ParameterList;
import de.compilerbau.NewAwkCompiler.javacc21.PrintStmnt;
import de.compilerbau.NewAwkCompiler.javacc21.Product;
import de.compilerbau.NewAwkCompiler.javacc21.ReturnStatement;
import de.compilerbau.NewAwkCompiler.javacc21.Sign;
import de.compilerbau.NewAwkCompiler.javacc21.Stmnt;
import de.compilerbau.NewAwkCompiler.javacc21.StringLiteral;
import de.compilerbau.NewAwkCompiler.javacc21.Sum;
import de.compilerbau.NewAwkCompiler.javacc21.Token;
import de.compilerbau.NewAwkCompiler.javacc21.Type;
import de.compilerbau.NewAwkCompiler.javacc21.VariableDecl;
import de.compilerbau.NewAwkCompiler.javacc21.VariableDeclAndAssignement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class SymbolTableBuilderVisitor extends VisitorAdapter {

    private static final Logger log = LoggerFactory.getLogger(SymbolTableBuilderVisitor.class);

    private SymbolTable symbolTable;
    private Utils utils;

    public SymbolTableBuilderVisitor() {
        symbolTable = new SymbolTable();
        utils = new Utils();
    }

    public SymbolTable getSymTab() {
        return symbolTable;
    }

    public void printEnter(Node node) {
        log.info("<---Entering Class: " + node.getClass().getSimpleName() + "\n" +
                "<---With Content:   " + node.toString());
    }

    public void printExit(Node node) {
        log.info("--->Exiting Class: " + node.getClass().getSimpleName() + "\n" +
                "--->With Content:   " + node.toString());
    }

    /**
     * Returns the context of a node (in a method (methodID) or outside (""))
     */
    public String getContext(Node node) {
        String context = "";
        MethodDecl m = node.firstAncestorOfType(MethodDecl.class);
        if (m != null) {
            log.warn("TEST2: " + m);
            context = m.id.getImage();
        }
        return context;
    }

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
        data = node.childrenAccept(this, data);
        printExit(node);
        return data;
    }

    /**
     * Checks simple Variable Declarations
     * doesn't need to accept children for this usecase
     */
    @Override
    public Object visit(VariableDecl node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);

        //1 Fill Object with needed subtypes
        node.type = node.firstChildOfType(Type.class);
        node.id = node.firstChildOfType(ID.class);
        node.value = null;
        String contextId = getContext(node); //Init with global context && Check if Method-Context

        if (symbolTable.checkAndInsertVariableDecl(node, contextId)) {
            log.info("SUCCESS: insertVariableDecl: Variable: " + node.toString());
        } else {
            throw new TypeCheckingException("Variable has already been declared in the same scope you cant declare " +
                    "it twice. Position: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                    + node.firstChildOfType(ID.class).getEndColumn());
        }
        printExit(node);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(Assignement node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);
        //Subtypes
        node.id = node.firstChildOfType(ID.class);
        node.exprStmnt = node.firstChildOfType(ExprStmnt.class);
        String contextId = getContext(node); //Init with global context && Check if Method-Context
        //Is the assignement-variable declared? If not -> error
        if (!symbolTable.isVariableDeclared(new VariableDecl(null, node.id), contextId)) {
            throw new TypeCheckingException("Variable to assign to hasn't been declared in the same scope. Please declare it. " +
                    "Position of use: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                    + node.firstChildOfType(ID.class).getEndColumn());
        }
        // Variable is declared, check if assignement of value is possible
        else {
            log.info("Variable is declared, checking assignement possible");
            ExprStmnt exprStmnt = node.exprStmnt;
            VariableDecl variableDecl = symbolTable.findVariableDeclFromID(node.id, contextId);
            //Types need to be equal for assignement or boxable (int -> double, all -> String)
            // if ok: Save the assignement Data to the Variable-Decl in the Table
            if (variableDecl.type.type.equals(exprStmnt.type.type)
                    || variableDecl.type.type.equals("double") && exprStmnt.type.type.equals("int")
                    || variableDecl.type.type.equals("String")) {
                variableDecl.value = exprStmnt.value;
                log.info("Update Variable with value: VariableDecl: " + variableDecl);
                symbolTable.updateVariableDeclValue(variableDecl.type, variableDecl.id, variableDecl.value, contextId);
            } else {
                throw new TypeCheckingException("Assignement-Types are not equal or boxable," +
                        " please correct that.");
            }
        }
        printExit(node);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(VariableDeclAndAssignement node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);

        //1 Fill up Object with needed subtypes
        node.type = node.firstChildOfType(Type.class);
        node.id = node.firstChildOfType(ID.class);
        log.warn("TEST: " + node);
        node.exprStmnt = node.firstChildOfType(ExprStmnt.class);
        String contextId = getContext(node); //Init with global context && Check if Method-Context

        if (!symbolTable.checkAndInsertVariableDecl(new VariableDecl(node.type, node.id), contextId)) {
            throw new TypeCheckingException("Variable has already been declared in the same scope you cant declare " +
                    "it twice. Position of first declaration: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                    + node.firstChildOfType(ID.class).getEndColumn());
        } else {
            log.info("SUCCESS: insertVariableDecl: Variable: " + node.toString());
            // Now check if Assignement possible
            node.id = node.firstChildOfType(ID.class);
            node.exprStmnt = node.firstChildOfType(ExprStmnt.class);
            //Is the assignement-variable declared? If not -> error
            if (!symbolTable.isVariableDeclared(new VariableDecl(null, node.id), contextId)) {
                throw new TypeCheckingException("Variable to assign to hasn't been declared in the same scope. Please declare it. " +
                        "Position of use: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                        + node.firstChildOfType(ID.class).getEndColumn());
            }
            // Variable is declared, check if assignement of value is possible
            else {
                log.info("Variable is declared, checking assignement possible");
                ExprStmnt exprStmnt = node.exprStmnt;
                VariableDecl variableDecl = symbolTable.findVariableDeclFromID(node.id, contextId);
                //Types need to be equal for assignement or boxable (int -> double, all -> String)
                // if ok: Save the assignement Data to the Variable-Decl in the Table
                if (variableDecl.type.type.equals(exprStmnt.type.type)
                        || variableDecl.type.type.equals("double") && exprStmnt.type.type.equals("int")
                        || variableDecl.type.type.equals("String")) {
                    variableDecl.value = exprStmnt.value;
                    log.info("Update Variable with value: VariableDecl: " + variableDecl);
                    symbolTable.updateVariableDeclValue(variableDecl.type, variableDecl.id, variableDecl.value, contextId);
                } else {
                    throw new TypeCheckingException("Assignement-Types are not equal or boxable," +
                            " please correct that.");
                }
            }


        }
        printExit(node);
        return data;
    }

    /**
     * -
     */
    @Override
    public Object visit(MethodDecl node, Object data) {
        printEnter(node);
        node.type = node.firstChildOfType(Type.class);
        //node.id = node.firstChildOfType((ID.class)); needs to be put in before
        // we need it to define the context
        node.id = node.firstChildOfType(ID.class);
        node.id.setImage(node.idValue);

        data = node.childrenAccept(this, data);

        node.parameterList = node.firstChildOfType(ParameterList.class);
        node.block = node.firstChildOfType(Block.class);

        //TODO Symboltable-entry for method
        printExit(node);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(ParameterList node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);

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
        printExit(node);
        return data;
    }


    /**
     * <BlockAuf> (Stmnt())+ <BlockZu>
     * => Only needs to accept Stmnt Statements, ignore other Terminals like "{" "}"
     */
    @Override
    public Object visit(Block node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);
        printExit(node);
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
        printExit(node);
        return data;
    }

    /**
     * BaseNode not relevant for visitors
     */
    @Override
    public Object visit(BaseNode node, Object data) {
        printEnter(node);
        printExit(node);
        return data;
    }

    /**
     * Not needed for NewAwk, maybe later
     */
    @Override
    public Object visit(IfStmnt node, Object data) {
        printEnter(node);
        printExit(node);
        return data;
    }

    /**
     * Handles an expression
     * Production: Expr() ;
     */
    @Override
    public Object visit(ExprStmnt node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);
        //TODO Type of Expr
        // Value
        // Wird verwendet bei Assignement und VariableDeclAndAssignement

        node.type = node.firstChildOfType(Expr.class).type;
        node.value = node.firstChildOfType(Expr.class).value;

        printExit(node);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(Expr node, Object data) {
        printEnter(node);
        node.childrenAccept(this, data);

        if (node.children().size() == 1) {
            node.type = node.firstChildOfType(LogicalOrExpr.class).type;
            node.value = node.firstChildOfType(LogicalOrExpr.class).value;
            printExit(node);
            return data;
        }
        //TODO Else

        printExit(node);
        return data;
    }


    @Override
    public Object visit(LogicalOrExpr node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);
        // Operatoren auf boolean: &&, ||, !
        //If 1 child: pass this
        if (node.children().size() == 1) {
            node.type = node.firstChildOfType(LogicalAndExpr.class).type;
            node.value = node.firstChildOfType(LogicalAndExpr.class).value;
            printExit(node);
            return data;
        } else {
            //Start operation with (Token || Token || Token ...)
            for (int i = 0; i < node.children().size(); i++) {
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
                    }
                    //All of the boolean = false, pass false up
                    else {
                        node.type = new Type("boolean");
                        node.value = "false";
                    }
                }
            }
        }
        printExit(node);
        return data;
    }

    @Override
    public Object visit(LogicalAndExpr node, Object data) {
        // Operatoren auf boolean: &&, ||, !
        data = node.childrenAccept(this, data);

        if (node.children().size() == 1) {
            node.type = node.firstChildOfType(LogicalNotExpr.class).type;
            node.value = node.firstChildOfType(LogicalNotExpr.class).value;
            printExit(node);
            return data;
        }
        //TODO ELSE

        printExit(node);
        return data;
    }

    @Override
    public Object visit(LogicalNotExpr node, Object data) {
        printEnter(node);
        // Operatoren auf boolean: &&, ||, !
        // Check if childs boolean -> turn around bool and save
        // else just pass up values
        data = node.childrenAccept(this, data);

        if (node.children().size() == 1) {
            node.type = node.firstChildOfType(CompExpr.class).type;
            node.value = node.firstChildOfType(CompExpr.class).value;
            printExit(node);
            return data;
        }
        //TODO ELSE

        printExit(node);
        return data;
    }

    @Override
    public Object visit(CompExpr node, Object data) {
        printEnter(node);
        // Vergleichsoperationen: ==, >=, !=, <=, <, >
        // Alle Datentypen
        data = node.childrenAccept(this, data);

        if (node.children().size() == 1) {
            node.type = node.firstChildOfType(Sum.class).type;
            node.value = node.firstChildOfType(Sum.class).value;
            printExit(node);
            return data;
        }
        //TODO ELSE

        printExit(node);
        return data;
    }

    @Override
    public Object visit(Sum node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);
        // Operatoren auf int, double, char: +, -,

        // 1. Check how many childs
        int childsCount = node.childrenOfType(PLUS.class).size() + node.childrenOfType(MINUS.class).size() + 1;

        // If 1 no operation required, just hand it up with type and value
        // Type is not important here
        if (childsCount == 1) {
            node.type = node.childrenOfType(Product.class).get(0).type;
            node.value = node.childrenOfType(Product.class).get(0).value;
            printExit(node);
            return data;
        }

        // If more, do the operation
        // First check types, then values later (symboltable not needed (Arrays = Atoms :))
        List<Node> childs = node.children();
        ;
        //Init with first value
        Sum sum = new Sum();
        sum.type = node.firstChildOfType(Product.class).type;
        sum.value = node.firstChildOfType(Product.class).value;
        //Start operation at 2nd value (Token + Token - Token + ...)
        log.warn("Sum-Type: " + sum.type.toString() +
                "Sum-Value: " + sum.value + "\n" +
                String.valueOf(node.children().size()));
        for (int i = 2; i < node.children().size(); i += 2) {
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

        printExit(node);
        return data;
    }

    @Override
    public Object visit(Product node, Object data) {
        // Operatoren auf int, double, char: *, /, %
        printEnter(node);
        data = node.childrenAccept(this, data);

        // If 1 no operation required, just pass it up
        if (node.children().size() == 1) {
            node.type = node.firstChildOfType(Sign.class).type;
            node.value = node.firstChildOfType(Sign.class).value;
            printExit(node);
            return data;
        }
        // Check if only types are int, double and char
        // Then just let them operate to a double result with java this is possible
        //double test = 2 * 5.3 / 'a' % 'f' % 2 / 4.32
        if (node.childrenOfType(Sign.class).stream()
                .filter(child -> !child.type.type.equals("int"))
                .filter(child -> !child.type.type.equals("double"))
                .filter(child -> !child.type.type.equals("char"))
                .collect(Collectors.toList()).size() > 0
        ) {
            throw new TypeCheckingException("There are other types than int, double or char" +
                    "in an operation which uses * / and %");
        } else {
            //Algo: 1) Get 2 operands, 2) parse both 3) operate with them 4) result save double
            Sign firstChild = node.firstChildOfType(Sign.class);
            double result = 0;
            if (firstChild.type.type.equals("int")) {
                result = (double) Integer.parseInt(firstChild.value);
            }
            if (firstChild.type.type.equals("double")) {
                result = Double.parseDouble(firstChild.value);
            }
            if (firstChild.type.type.equals("char")) {
                result = (double) firstChild.value.charAt(0);
            }
            for (int i = 2; i < node.children().size(); i += 2) {
                Sign child = (Sign) node.getChild(i);
                double childValue = 0;
                if (child.type.type.equals("int")) {
                    childValue = (double) Integer.parseInt(child.value);
                }
                if (child.type.type.equals("double")) {
                    childValue = Double.parseDouble(child.value);
                }
                if (child.type.type.equals("char")) {
                    childValue = (double) child.value.charAt(0);
                }
                //Operate
                if (node.getChild(i - 1) instanceof MULTIPLICATION) {
                    result *= childValue;
                }
                if (node.getChild(i - 1) instanceof DIVISION) {
                    result /= childValue;
                }
                if (node.getChild(i - 1) instanceof MODULO) {
                    result %= childValue;
                }
            }
            node.type = new Type("double");
            node.value = String.valueOf(result);
        }


        //TODO Implement Products-Ops
        node.type = new Type("int");
        node.value = "test";

        printExit(node);
        return data;
    }

    @Override
    public Object visit(Sign node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);

        if (node.children().size() == 1) {
            node.type = node.firstChildOfType(Atom.class).type;
            node.value = node.firstChildOfType(Atom.class).value;
            printExit(node);
            return data;
        } else if (node.getFirstChild() instanceof MINUS) {
            node.type = node.firstChildOfType(Atom.class).type;
            switch (node.firstChildOfType(Atom.class).type.type) {
                case "int":
                    node.value = String.valueOf(
                            -Integer.parseInt(node.firstChildOfType(Atom.class).value)
                    );
                case "double":
                    node.value = String.valueOf(
                            -Double.parseDouble(node.firstChildOfType(Atom.class).value)
                    );
                case "char": //Do Nothing, just pass Exception
                case "boolean": //Do Nothing, just pass Exception
                case "String": //Do Nothing, just pass Exception
                default:
                    throw new TypeCheckingException("Sign is used in front of type not applicable at: " +
                            node.getBeginColumn() + ":" + node.getBeginLine());
            }
        }
        printExit(node);
        return data;
    }

    /**
     * | LOOKAHEAD(3) t=<ID>
     * [".length"
     * {jjtThis.hasLength = true;
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
        data = node.childrenAccept(this, data);

        // TODO Checking for .length, wenn vorhanden, dann umwandlung zu Integertyp

        if (node.getFirstChild() instanceof ID) {
            //Length ID: x.length
            if (node.hasLength) {
                log.info("Found Atom with \".length()\" with " + node.children().size() + " children.");
                node.type = new Type("int");
                //TODO Geben: ID, Gesucht: Exists, value, type
                String context = getContext(node);
                VariableDecl variableDecl = symbolTable.findVariableDeclFromID(node.firstChildOfType(ID.class), context);
                if (variableDecl != null && variableDecl.value != null) {
                    log.warn("TEST: " + variableDecl.value.length());
                    variableDecl.value.length();
                } else {
                    throw new TypeCheckingException("Variable: " + node.firstChildOfType(ID.class).getImage()
                            + " with .length() hasn't been defined, it wasn't found in the SymbolTable.");
                }
            }
            // ArrayAccess ID: x[5]
            else if (node.isArrayAccess) {
                Expr expr = node.firstChildOfType(ArrayAccess.class).firstChildOfType(Expr.class);
                //Iterate over Array and store value and type in expr
                ID id = node.firstChildOfType(ID.class); //Use to find array
                // expr.type => needs to be int for array index access
                // expr.value => value to pass to found array and access data
                node.type = new Type("myType"); //TODO Return-Type-From-Array;
                node.value = "1"; // TODO Return-Value-From-Array;
            }
            // Normal ID: x
            else {
                //TODO What to do
                // node.type = ?
                // node.value = ?
                log.warn("Normal String found!" + node.firstChildOfType(ID.class));
            }
        } else if (node.getFirstChild() instanceof KlammerAuf &&
                node.getChild(1) instanceof Expr &&
                node.getChild(2) instanceof KlammerZu) {
            node.type = node.firstChildOfType(Expr.class).type;
            node.value = node.firstChildOfType(Expr.class).value;
        } else if (node.getFirstChild() instanceof BooleanLiteral) {
            node.type = new Type("boolean");
            node.value = node.firstChildOfType(BooleanLiteral.class).getImage();
        } else if (node.getFirstChild() instanceof IntegerLiteral) {
            node.type = new Type("int");
            node.value = node.firstChildOfType(IntegerLiteral.class).getImage();
        } else if (node.getFirstChild() instanceof DoubleLiteral) {
            node.type = new Type("double");
            node.value = node.firstChildOfType(DoubleLiteral.class).getImage();
        } else if (node.getFirstChild() instanceof CharLiteral) {
            node.type = new Type("char");
            node.value = node.firstChildOfType(CharLiteral.class).getImage();
        } else if (node.getFirstChild() instanceof NullLiteral) {
            node.type = new Type("null");
            node.value = node.firstChildOfType(NullLiteral.class).getImage();
        }
        // "String".length => Integer
        else if (node.getFirstChild() instanceof StringLiteral) {
            if (node.hasLength) { //String has definitive length
                node.type = new Type("int");
                node.value = String.valueOf(node.atomLength);
            } else if (node.isInt) {
                node.type = new Type("boolean");
                String str = node.firstChildOfType(StringLiteral.class).getImage();
                str = str.substring(1, str.length() - 1);
                boolean isInt = str.chars().allMatch(Character::isDigit);
                node.value = String.valueOf(isInt);
                log.warn("Int: Str.: " + str + " Node.value: " + node.value);
            } else if (node.isDouble) {
                node.type = new Type("boolean");
                String str = node.firstChildOfType(StringLiteral.class).getImage();
                str = str.substring(1, str.length() - 1);
                boolean isDouble = false;
                if (str == null || str.length() == 0) {
                    isDouble = false;
                } else {
                    try {
                        Double.parseDouble(str);
                        isDouble = true;
                    } catch (NumberFormatException e) {
                        isDouble = false;
                    }
                }
                node.value = String.valueOf(isDouble);
                log.warn("Double: Str.: " + str + " Node.value: " + node.value);
            } else if (node.toInt) {
                node.type = new Type("int");
                String str = node.firstChildOfType(StringLiteral.class).getImage();
                str = str.substring(1, str.length() - 1);
                try {
                    node.value = String.valueOf(Integer.parseInt(str));
                } catch (NumberFormatException e) {
                    throw new TypeCheckingException("Could not convert String value to integer!");
                }
            } else if (node.toDouble) {
                node.type = new Type("double");
                String str = node.firstChildOfType(StringLiteral.class).getImage();
                str = str.substring(1, str.length() - 1);
                try {
                    node.value = String.valueOf(Double.parseDouble(str));
                } catch (NumberFormatException e) {
                    throw new TypeCheckingException("Could not convert String value to double!");
                }
            }
            //Normal String
            else {
                node.type = new Type("String");
                node.value = node.firstChildOfType(StringLiteral.class).getImage();
            }
        }
        printExit(node);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(Cast node, Object data) {
        printEnter(node);
        printExit(node);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(MethodCall node, Object data) {
        printEnter(node);
        printExit(node);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(ArrayAccess node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);
        printExit(node);
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
        printExit(node);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(KlammerAffe node, Object data) {
        printEnter(node);
        printExit(node);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(KlammerAffeRegex node, Object data) {
        printEnter(node);
        printExit(node);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(KlammerAffeAusdruck node, Object data) {
        printEnter(node);
        printExit(node);
        return data;
    }

    /**
     *
     */
    @Override
    public Object visit(PrintStmnt node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);
        printExit(node);
        return data;
    }

    /**
     * Type has only terminals, so it cant have childs to accept.
     */
    @Override
    public Object visit(Type node, Object data) {
        printEnter(node);
        printExit(node);
        return data;
    }

    /**
     * Only Terminal of Type "Token" we recognize is EOF, therefore no
     * childs to accept as well
     */
    @Override
    public Object visit(Token node, Object data) {
        printEnter(node);
        printExit(node);
        return data;

    }
}