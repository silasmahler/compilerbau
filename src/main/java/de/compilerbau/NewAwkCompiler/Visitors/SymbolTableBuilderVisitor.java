package de.compilerbau.NewAwkCompiler.Visitors;

import de.compilerbau.NewAwkCompiler.javacc21.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
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
        log.info("Enter: " + node.getClass().getSimpleName() + "\n" +
                "Content: " + node.toString());
    }

    public void printExit(Node node) {
        log.info("Exit: " + node.getClass().getSimpleName() + "\n" +
                "Content: " + node.toString());
    }

    /**
     * Returns the context of a node (in a method (methodID) or outside (""))
     */
    public String getContext(Node node) {
        String context = "";
        MethodDecl m = node.firstAncestorOfType(MethodDecl.class);
        if (m != null) {
            log.info("getContext for MethodDecl: " + m);
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
            throw new SemanticException("Variable has already been declared in the same scope you cant declare " +
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
            throw new SemanticException("Variable to assign to hasn't been declared in the same scope. Please declare it. " +
                    "Position of use: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                    + node.firstChildOfType(ID.class).getEndColumn());
        }
        // Variable is declared, check if assignement of value is possible
        else {
            log.info("Assignement: Variable is declared, checking assignement possible");
            ExprStmnt exprStmnt = node.exprStmnt;
            VariableDecl variableDecl = symbolTable.findVariableDeclFromID(node.id, contextId);
            log.info("Assignement: Found VariableDecl in the Symboltable: " + variableDecl + "\n" +
                    "Comparing it with ExprStmt by type next: " + exprStmnt);
            //TODO How to get Init-Stmnt Dimension? (Check how many braces would be possible)
            //Check Dimension equal
            if (variableDecl.type.arrayTypeDimension == exprStmnt.type.arrayTypeDimension ||
                    exprStmnt.type.isArray) {
                // Decl Type == Assignement Type or boxable
                if (variableDecl.type.type.equals(exprStmnt.type.type)
                        || variableDecl.type.type.equals("double") && exprStmnt.type.type.equals("int")
                        || variableDecl.type.type.equals("double") && exprStmnt.type.type.equals("char")
                        || variableDecl.type.type.equals("String")) {
                    variableDecl.value = exprStmnt.value;
                    log.info("Assignement: Update Variable with value: VariableDecl: " + variableDecl);
                    symbolTable.updateVariableDeclValue(variableDecl.type, variableDecl.id, variableDecl.value, contextId);
                } else {
                    throw new SemanticException("Assignement-Types are not equal or boxable," +
                            " please correct thatat: " + node.getBeginLine() + ":" + node.getBeginColumn() + "\n" +
                            "VariableDecl-Type: " + variableDecl.type + " Expr-Type: " + exprStmnt.type);
                }
            } else {
                throw new SemanticException("Dimensions of Array and Variable dont fit.");
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
        node.exprStmnt = node.firstChildOfType(ExprStmnt.class);
        String contextId = getContext(node); //Init with global context && Check if Method-Context

        if (!symbolTable.checkAndInsertVariableDecl(new VariableDecl(node.type, node.id), contextId)) {
            throw new SemanticException("VariableDeclAndAssignement: Variable has already been declared in the same scope you cant declare " +
                    "it twice. Position of first declaration: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                    + node.firstChildOfType(ID.class).getEndColumn());
        } else {
            log.info("VariableDeclAndAssignement: SUCCESS: insertVariableDecl: " + node.toString());
            // Now check if Assignement possible
            node.id = node.firstChildOfType(ID.class);
            node.exprStmnt = node.firstChildOfType(ExprStmnt.class);
            //Is the assignement-variable declared? If not -> error
            if (!symbolTable.isVariableDeclared(new VariableDecl(null, node.id), contextId)) {
                throw new SemanticException("VariableDeclAndAssignement: Variable to assign to hasn't been declared in the same scope. Please declare it. " +
                        "Position of use: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                        + node.firstChildOfType(ID.class).getEndColumn());
            }
            // Variable is declared, check if assignement of value is possible
            else {
                log.info("VariableDeclAndAssignement: Variable is declared, checking assignement possible");
                ExprStmnt exprStmnt = node.exprStmnt;
                VariableDecl variableDecl = symbolTable.findVariableDeclFromID(node.id, contextId);
                log.info("VariableDeclAndAssignement: Found VariableDecl in the Symboltable: " + variableDecl + "\n" +
                        "Comparing it with ExprStmt by type next: " + exprStmnt);
                //Check Dimension equal
                //TODO Fix NPE from not handing up multiple Array Accesses
                if (variableDecl.type.arrayTypeDimension == exprStmnt.type.arrayTypeDimension ||
                        exprStmnt.type.isArray
                ) {
                    // Decl Type == Assignement Type or boxable
                    if (variableDecl.type.type.equals(exprStmnt.type.type)
                            || variableDecl.type.type.equals("double") && exprStmnt.type.type.equals("int")
                            || variableDecl.type.type.equals("double") && exprStmnt.type.type.equals("char")
                            || variableDecl.type.type.equals("String")) {
                        variableDecl.value = exprStmnt.value;
                        log.info("VariableDeclAndAssignement: Update Variable with value: VariableDecl: " + variableDecl);
                        symbolTable.updateVariableDeclValue(variableDecl.type, variableDecl.id, variableDecl.value, contextId);
                    } else {
                        throw new SemanticException("VariableDeclAndAssignement: Assignement-Types are not equal or boxable," +
                                " please correct that at: " + node.getBeginLine() + ":" + node.getBeginColumn() + "\n" +
                                "VariableDecl-Type: " + variableDecl.type + " Expr-Type: " + exprStmnt.type);
                    }
                } else {
                    throw new SemanticException("Dimensions of Array and Variable dont fit.");
                }
            }
        }
        printExit(node);
        return data;
    }

    @Override
    public Object visit(MethodDecl node, Object data) {
        printEnter(node);
        node.type = node.firstChildOfType(Type.class);
        //node.id = node.firstChildOfType((ID.class)); needs to be put in before
        // we need it to define the context
        node.id = node.firstChildOfType(ID.class);
        if (node.isVoid) {
            node.id.setImage("void");
        }
        //node.id.setImage(node.idValue);

        data = node.childrenAccept(this, data);

        node.parameterList = node.firstChildOfType(ParameterList.class);
        node.block = node.firstChildOfType(Block.class);

        //TODO Symboltable-entry for method
        printExit(node);
        return data;
    }

    @Override
    public Object visit(ParameterList node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);

        //Empty Parameterlist return no action needed
        if (!node.hasChildNodes()) {
            log.info("Detected 0 Parameters in ParamaterList");
            return data;
        }
        //There are parameters
        else {
            //Check how many commata to determine how many parameters
            int parameterCount = node.childrenOfType(COMMA.class).size() + 1;
            log.info("Detected " + parameterCount + " Parameters in ParamaterList");
            List<Type> types = node.childrenOfType(Type.class); //Get all Types
            List<ID> ids = node.childrenOfType(ID.class); //Get all IDs
            //Marry them
            if (!(types.size() == ids.size()) && (types.size() == parameterCount)) {
                throw new SemanticException("Something broke while checking Method Parameters." +
                        "Please declare it like: TYPE ID COMMA TYPE ID ...");
            }
            String contextId = getContext(node); //Init with global context && Check if Method-Context

            for (int i = 0; i < types.size(); i++) {
                Parameter p = new Parameter(types.get(i), ids.get(i));
                log.info("Add Parameter to ParameterList: " + p.toString());
                node.parameterList.add(p);
                symbolTable.checkAndInsertVariableDecl(new VariableDecl(types.get(i), ids.get(i)), contextId);

                /*TODO Insert VariableDecl for Method
                if (!symbolTable.checkAndInsertVariableDecl(new VariableDecl(types.get(i), node.id), contextId)) {
                    throw new SemanticException("ParameterList: Variable has already been declared in the same scope you cant declare " +
                            "it twice. Position of first declaration: " + node.firstChildOfType(ID.class).getEndLine() + ":"
                            + node.firstChildOfType(ID.class).getEndColumn());
                }*/
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

    @Override
    public Object visit(Stmnt node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);
        printExit(node);
        return data;
    }

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

    @Override
    public Object visit(Expr node, Object data) {
        printEnter(node);
        node.childrenAccept(this, data);

        if (node.children().size() == 1) {
            log.info("Node has only 1 child, pass up data value and type.");
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
            log.info("Node has only 1 child, pass up data value and type.");
            node.type = node.firstChildOfType(LogicalAndExpr.class).type;
            node.value = node.firstChildOfType(LogicalAndExpr.class).value;
            printExit(node);
            return data;
        } else if (node.childrenOfType(LogicalAndExpr.class).stream().allMatch(
                child -> child.type.type.equals("boolean"))) {
            List<Boolean> bools = node.childrenOfType(LogicalAndExpr.class).stream().map(child
                    -> Boolean.parseBoolean(child.value)).collect(Collectors.toList());
            if (bools.stream().filter(b -> b.booleanValue() == true).findAny().isPresent()) {
                node.type = new Type("boolean");
                node.value = "true";
            } else {
                node.type = new Type("boolean");
                node.value = "false";
            }

        } else {
            throw new SemanticException("LogicalOrExpr: Not all operands boolean at: " +
                    node.getBeginLine() + ":" + node.getBeginColumn());
        }
        printExit(node);
        return data;
    }

    @Override
    public Object visit(LogicalAndExpr node, Object data) {
        // Operatoren auf boolean: &&, ||, !
        data = node.childrenAccept(this, data);

        if (node.children().size() == 1) {
            log.info("Node has only 1 child, pass up data value and type.");
            node.type = node.firstChildOfType(LogicalNotExpr.class).type;
            node.value = node.firstChildOfType(LogicalNotExpr.class).value;
            printExit(node);
            return data;
        } else if (node.childrenOfType(LogicalNotExpr.class).stream().allMatch(
                child -> child.type.type.equals("boolean"))) {
            List<Boolean> bools = node.childrenOfType(LogicalNotExpr.class).stream().map(child
                    -> Boolean.parseBoolean(child.value)).collect(Collectors.toList());
            if (bools.stream().filter(b -> b.booleanValue() == false).findAny().isPresent()) {
                node.type = new Type("boolean");
                node.value = "false";
            } else {
                node.type = new Type("boolean");
                node.value = "true";
            }

        } else {
            throw new SemanticException("LogicalAndExpr: Not all operands boolean at: " +
                    node.getBeginLine() + ":" + node.getBeginColumn());
        }
        printExit(node);
        return data;
    }

    @Override
    public Object visit(LogicalNotExpr node, Object data) {
        printEnter(node);
        // Operatoren auf boolean: &&, ||, !
        data = node.childrenAccept(this, data);

        if (node.children().size() == 1) {
            log.info("Node has only 1 child, pass up data value and type.");
            node.type = node.firstChildOfType(CompExpr.class).type;
            node.value = node.firstChildOfType(CompExpr.class).value;
            printExit(node);
            return data;
        } else if (node.children().size() == 2) {
            CompExpr ce = node.firstChildOfType(CompExpr.class);
            if (ce.type.type.equals("boolean")) {
                if (ce.value.equals("true")) {
                    node.type = ce.type;
                    node.value = "false";
                } else if (ce.value.equals("false")) {
                    node.type = ce.type;
                    node.value = "true";
                } else {
                    throw new SemanticException("LogicalNot: boolean type CompExpr-Type has a non-bool-value at:" +
                            ce.getBeginLine() + ":" + ce.getBeginColumn());
                }
            } else {
                throw new SemanticException("LogicalNot: is used on a non-boolean expression at: " +
                        ce.getBeginLine() + ":" + ce.getBeginColumn());
            }
        } else {
            throw new SemanticException("LogicalNot: Please don't use multiple \"!\" together at: " +
                    node.getBeginLine() + ":" + node.getBeginColumn());
        }

        printExit(node);
        return data;
    }

    @Override
    public Object visit(CompExpr node, Object data) {
        printEnter(node);
        // Vergleichsoperationen:
        // ==, !=           Datentypen: int, double, char, boolean
        // >=, <=, <, >     Datentypen: int, double, char
        data = node.childrenAccept(this, data);

        if (node.children().size() == 1) {
            log.info("CompExpr: Node has only 1 child, pass up data value and type.");
            node.type = node.firstChildOfType(Sum.class).type;
            node.value = node.firstChildOfType(Sum.class).value;
            printExit(node);
            return data;
        }
        log.warn("CompExpr: Node has more than 1 child, begin computing...");

        //1) Handle ==, != for all Boolean
        if (node.childrenOfType(Sum.class).stream().allMatch(child -> child.type.type.equals("boolean"))) {
            log.warn("CompExpr: All Types are boolean.");
            // Alle children zu boolean parsen für einfachere ops
            List<Boolean> bools = node.childrenOfType(Sum.class).stream().map(child -> Boolean.parseBoolean(child.value)).collect(Collectors.toList());
            //Get Operands List
            List<Node> operands = node.children().stream().filter(child ->
                    (child instanceof EQUAL) || (child instanceof NOT_EQUAL)).collect(Collectors.toList());
            log.info("CompExpr: BooleanList for only-bool Expr: " + bools + "\n" +
                    "and Operands: " + operands);
            if ((operands.size() + 1) != bools.size()) {
                throw new SemanticException("There are operators other than == and != " +
                        "applied to a boolean CompExpr. Please correct this at: "
                        + node.getBeginLine() + ":" + node.getBeginColumn());
            }
            //Iterate over lists and operate
            boolean result = bools.get(0).booleanValue();
            for (int i = 1; i < bools.size(); i++) {
                if (operands.get(i - 1) instanceof EQUAL) {
                    result = result == bools.get(i);
                } else if (operands.get(i - 1) instanceof NOT_EQUAL) {
                    result = result != bools.get(i);
                }
            }
            node.type = new Type("boolean");
            node.value = result ? "true" : "false";
        }
        //2) Handle ==, !=, >=, <=, <, > for int, double & char
        else if (node.childrenOfType(Sum.class).stream().allMatch(child -> child.type.type.equals("int") ||
                child.type.type.equals("double") || child.type.type.equals("char"))) {
            log.warn("CompExpr: All types are int, double or char");
            //Maximum of 2 doubles possible
            List<Double> doubles = node.childrenOfType(Sum.class).stream().map(child -> {
                Double value = 0.0;
                if (child.type.type.equals("int") || child.type.type.equals("double")) {
                    value = Double.parseDouble(child.value);
                } else if (child.type.type.equals("char")) {
                    value = (double) child.value.charAt(0);
                }
                return value;
            }).collect(Collectors.toList());
            //Get Operands List
            List<Node> operands = node.children().stream().filter(child ->
                    (child instanceof GREATER) || (child instanceof SMALLER) || (child instanceof G_OR_EQUAL) ||
                            (child instanceof S_OR_EQUAL) || (child instanceof EQUAL) || (child instanceof NOT_EQUAL))
                    .collect(Collectors.toList());
            log.info("CompExpr: List for int, double, char Expr: " + doubles + "\n" +
                    "and Operands: " + operands);
            if ((operands.size() + 1) != doubles.size() || doubles.size() != 2) {
                throw new SemanticException("There are Operations with a comparator-operator (==, !=, >=, <=, <, >)" +
                        " and more than 2 doubles or uncompatible types (e.g. boolean, double) " +
                        "applied to a boolean CompExpr. Please use int, double and char correct this at: "
                        + node.getBeginLine() + ":" + node.getBeginColumn());
            }
            Node op = node.getChild(1);
            boolean result = false;
            if (op instanceof GREATER) {
                result = doubles.get(0) > doubles.get(1);
            } else if (op instanceof GREATER) {
                result = doubles.get(0) > doubles.get(1);
            } else if (op instanceof SMALLER) {
                result = doubles.get(0) < doubles.get(1);
            } else if (op instanceof S_OR_EQUAL) {
                result = doubles.get(0) <= doubles.get(1);
            } else if (op instanceof G_OR_EQUAL) {
                result = doubles.get(0) >= doubles.get(1);
            } else if (op instanceof EQUAL) {
                result = doubles.get(0) == doubles.get(1);
            } else if (op instanceof NOT_EQUAL) {
                result = doubles.get(0) != doubles.get(1);
            }
            node.type = new Type("boolean");
            node.value = result ? "true" : "false";
        } else {
            throw new SemanticException("There are incompatible types in an Comparable-Expression beginning at:" +
                    +node.getBeginLine() + ":" + node.getBeginColumn() +
                    " Please use only boolean or int, double and char or adjust the paranthesis.");
        }
        printExit(node);
        return data;
    }

    @Override
    public Object visit(Sum node, Object data) {
        // Operatoren auf int, double, char: +, -,
        printEnter(node);
        data = node.childrenAccept(this, data);
        if (node.children().size() == 1) {
            log.info("Sum: Only 1 child, pass up data and type.");
            node.type = node.childrenOfType(Product.class).get(0).type;
            node.value = node.childrenOfType(Product.class).get(0).value;
            printExit(node);
            return data;
        }
        // If more, do the operation, first check types, values later (symboltable not needed (Arrays = Atoms :))
        List<Node> childs = node.children();
        //Init with first value
        Sum sum = new Sum();
        sum.type = node.firstChildOfType(Product.class).type;
        sum.value = node.firstChildOfType(Product.class).value;
        //Start operation at 2nd value (Token + Token - Token + ...)
        log.info("Sum-Type: " + sum.type.toString() +
                "Sum-Value: " + sum.value + "\n" + node.children().size());
        for (int i = 2; i < node.children().size(); i += 2) {
            log.warn("INFO: i: " + i + " childs: " + childs);
            String childType = ((Product) childs.get(i)).type.type;
            String childValue = ((Product) childs.get(i)).value;
            String sumType = sum.type.type;
            Node op = childs.get(i - 1);
            // 1. EQUAL TYPES?
            if (sumType.equals(childType)) {
                // 1.1 INT x2
                if (sumType.equals("int")) {
                    if (op instanceof PLUS) {
                        sum.value = "" + (Integer.parseInt(sum.value) + Integer.parseInt(childValue));
                    } else if (op instanceof MINUS) {
                        sum.value = "" + (Integer.parseInt(sum.value) - Integer.parseInt(childValue));
                    } else {
                        throw new SemanticException("Operation on sum with same types went wrong.");
                    }
                }
                // 1.2 DOUBLE x2
                else if (sumType.equals("double")) {
                    if (op instanceof PLUS) {
                        sum.value = "" + (Double.parseDouble(sum.value) + Double.parseDouble(childValue));
                    } else if (op instanceof MINUS) {
                        sum.value = "" + (Double.parseDouble(sum.value) - Double.parseDouble(childValue));
                    } else {
                        throw new SemanticException("Operation on sum with same types went wrong.");
                    }
                }
                // 1.3 CHAR x2
                else if (sumType.equals("char")) {
                    if (op instanceof PLUS) {
                        sum.value = "" + (sum.value.charAt(0) + childValue.charAt(0));
                        sum.type = new Type("int");
                    } else if (op instanceof MINUS) {
                        sum.value = "" + (sum.value.charAt(0) - childValue.charAt(0));
                        sum.type = new Type("int");
                    } else {
                        throw new SemanticException("Operation on sum with same types went wrong.");
                    }
                }
                //1.4 Strings x2
                else if (sumType.equals("String")) {
                    if (op instanceof PLUS) {
                        sum.value = sum.value + childValue;
                        sum.type = sum.type; // Dont change it
                    } else if (op instanceof MINUS) {
                        throw new SemanticException("You can not operate with MINUS on 2 strings.");
                    } else {
                        throw new SemanticException("Operation on sum with same types went wrong.");
                    }
                }
            }
            // 2. NOT EQUAL TYPES
            // 1st Type is precedence, second needs to follow
            // int + double => double; double + int => double
            // char + int = int; int + char => int
            // double + char => double; char + double => double
            // sum.type needs to be adjusted
            else {
                //Sum is inited, typechange depending on op
                if (sumType.equals("int") && childType.equals("double")) {
                    sum.type = new Type("double");
                    if (op instanceof PLUS) {
                        sum.value = String.valueOf(Integer.parseInt(sum.value) + Double.parseDouble(childValue));
                    } else if (op instanceof MINUS) {
                        sum.value = String.valueOf(Integer.parseInt(sum.value) - Double.parseDouble(childValue));
                    }
                } else if (sumType.equals("double") && childType.equals("int")) {
                    sum.type = new Type("double");
                    if (op instanceof PLUS) {
                        sum.value = String.valueOf(Double.parseDouble(sum.value) + Integer.parseInt(childValue));
                    } else if (op instanceof MINUS) {
                        sum.value = String.valueOf(Double.parseDouble(sum.value) - Integer.parseInt(childValue));
                    }
                } else if (sumType.equals("char") && childType.equals("int")) {
                    sum.type = new Type("int");
                    if (op instanceof PLUS) {
                        sum.value = String.valueOf(sum.value.charAt(0) + Integer.parseInt(childValue));
                    } else if (op instanceof MINUS) {
                        sum.value = String.valueOf(sum.value.charAt(0) - Integer.parseInt(childValue));
                    }
                } else if (sumType.equals("int") && childType.equals("char")) {
                    sum.type = new Type("int");
                    if (op instanceof PLUS) {
                        sum.value = String.valueOf(Integer.parseInt(sum.value) + childValue.charAt(0));
                    } else if (op instanceof MINUS) {
                        sum.value = String.valueOf(Integer.parseInt(sum.value) - childValue.charAt(0));
                    }
                } else if (sumType.equals("double") && childType.equals("char")) {
                    sum.type = new Type("double");
                    if (op instanceof PLUS) {
                        sum.value = String.valueOf(Double.parseDouble(sum.value) + childValue.charAt(0));
                    } else if (op instanceof MINUS) {
                        sum.value = String.valueOf(Double.parseDouble(sum.value) - childValue.charAt(0));
                    }
                } else if (sumType.equals("char") && childType.equals("double")) {
                    sum.type = new Type("double");
                    if (op instanceof PLUS) {
                        sum.value = String.valueOf(sum.value.charAt(0) + Double.parseDouble(childValue));
                    } else if (op instanceof MINUS) {
                        sum.value = String.valueOf(sum.value.charAt(0) - Double.parseDouble(childValue));
                    }
                } else if (sumType.equals("String") && !childType.equals("String") ||
                        !sumType.equals("String") && childType.equals("String")) {
                    if (op instanceof PLUS) {
                        sum.type = new Type("String");
                        sum.value = sum.value + childValue;
                    }
                } else if (op instanceof MINUS) {
                    throw new SemanticException("You can not operate with MINUS on strings.");
                }
            }
            node.type = sum.type;
            node.value = sum.value;
        }
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
            log.info("Node has only 1 child, pass up data value and type.");
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
            throw new SemanticException("There are other types than int, double or char" +
                    " in an operation which uses * / and %");
        } else {
            //Algo: 1) Get 2 operands, 2) parse both 3) operate with them 4) result save double
            Sign firstChild = node.firstChildOfType(Sign.class);
            String firstChildType = firstChild.type.type;
            String firstChildValue = firstChild.value;
            double result = 0;
            if (firstChildType.equals("int")) {
                result = (double) Integer.parseInt(firstChildValue);
            } else if (firstChildType.equals("double")) {
                result = Double.parseDouble(firstChildValue);
            } else if (firstChildType.equals("char")) {
                result = (double) firstChildValue.charAt(0);
            }
            for (int i = 2; i < node.children().size(); i += 2) {
                Sign child = (Sign) node.getChild(i);
                double childValue = 0;
                String childType = child.type.type;
                if (childType.equals("int")) {
                    childValue = (double) Integer.parseInt(child.value);
                } else if (childType.equals("double")) {
                    childValue = Double.parseDouble(child.value);
                } else if (childType.equals("char")) {
                    childValue = (double) child.value.charAt(0);
                }
                //Operation
                Node n = node.getChild(i - 1);
                if (n instanceof MULTIPLICATION) {
                    result *= childValue;
                } else if (n instanceof DIVISION) {
                    result /= childValue;
                } else if (n instanceof MODULO) {
                    result %= childValue;
                }
            }
            //double can hold any ops on the 3 datatypes with the 3 operands, so we return it
            node.type = new Type("double");
            node.value = String.valueOf(result);
        }
        printExit(node);
        return data;
    }

    @Override
    public Object visit(Sign node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);

        Atom child = node.firstChildOfType(Atom.class);
        if (node.children().size() == 1 || node.getFirstChild() instanceof PLUS) {
            node.type = child.type;
            node.value = child.value;
            printExit(node);
            return data;
        } else if (node.getFirstChild() instanceof MINUS) {
            node.type = child.type;
            switch (child.type.type) {
                case "int":
                    node.value = String.valueOf(-Integer.parseInt(child.value)
                    );
                case "double":
                    node.value = String.valueOf(-Double.parseDouble(child.value)
                    );
                case "char":
                    node.value = String.valueOf(-child.value.charAt(0));
                default:
                    throw new SemanticException("Sign is used in front of type not applicable " +
                            "(Type != int, double or char) at: " +
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
                    log.info("Atom: VariableDecl-Value-Length: " + variableDecl.value.length());
                    //TODO Find length zu ID in symboltable

                } else {
                    throw new SemanticException("Variable: " + node.firstChildOfType(ID.class).getImage()
                            + " with .length() hasn't been defined, it wasn't found in the SymbolTable.");
                }
            }
            // ArrayAccess ID: x[5]
            else if (node.isArrayAccess) {
                //Get 1. ArrayAccess and ID
                ID id = node.firstChildOfType(ID.class); //Use to find array
                List<ArrayAccess> arrayAccesses = node.childrenOfType(ArrayAccess.class);
                ArrayAccess arrayAccess = arrayAccesses.get(0);

                // 2. check type == int || boolean, int => Return Single Value
                if (arrayAccess.type.type.equals("int")) {
                    // 3. get type and value id
                    VariableDecl decl = symbolTable.findVariableDeclFromID(id, getContext(node));
                    log.info("Atom: ArrayAccess found VariableDecl: " + decl);
                    if (node.arrayAccessDimension == 1) { // Single Dim
                        log.info("Atom: ArrayAccess detected with Type int and dimension 1.");
                        // Get Array-Type and Value from symboltable
                        ArrayTypeAndValue a = symbolTable.getArrayValAndTypeForIDAndIntAccess(id,
                                Integer.parseInt(arrayAccess.value), getContext(node));
                        if (decl.type.type.equals(a.type.type)) {
                            node.type = a.type;
                            node.value = a.value;
                        } else {
                            throw new SemanticException("VariableDecl and return-type of " +
                                    "one-dimensional arrayAccess (ArrayTypeAndValue) not equal.");
                        }
                    } else {
                        log.info("Atom: ArrayAccess detected with Type int and dimension > 1.");
                        ArrayTypeAndValue atv = symbolTable.getArrayAccessValAndTypeForIDAndInts(id,
                                arrayAccesses.stream().map(a -> Integer.parseInt(a.value))
                                        .collect(Collectors.toList()), getContext(node));
                        if (decl.type.type.equals(atv.type.type)) {
                            node.type = atv.type;
                            node.value = atv.value;
                        } else {
                            throw new SemanticException("VariableDecl and return-type of " +
                                    "one-dimensional arrayAccess (ArrayTypeAndValue) not equal.");
                        }
                    }
                }
                // 3.2 boolean => Return field for truthy condition
                // Always return field of same dim but delete all values that arent truthy by condition
                else if (arrayAccess.type.type.equals("boolean")) {
                    log.info("Atom: ArrayAccess detected with Type boolean.");
                    // 3.3. get type and value id
                    VariableDecl decl = symbolTable.findVariableDeclFromID(id, getContext(node));
                    log.info("Atom: ArrayAccess found VariableDecl: " + decl);

                    //TODO Impl
                    //arrayAccesses.stream().map(a -> )
                    //Table.getSubArrayTruthyBoolean();

                } else {
                    throw new SemanticException("Array-Access has a non-boolean or non-int Type at: "
                            + node.getBeginLine() + ":" + node.getBeginColumn());
                }
            }
            // Normal ID: x -> Check if it is an array access!
            else {
                // Check if variable in Symboltable and if yes then return it
                ID id = node.firstChildOfType(ID.class);
                log.warn("Atom: Normal ID detected!" + node.firstChildOfType(ID.class));
                VariableDecl v = symbolTable.findVariableDeclFromID(id, getContext(node));
                log.warn("Atom: Found VariableDecl to ID: " + v);
                node.type = v.type;
                node.value = v.value;
            }
        } else if (node.isArrayInit) {
            log.info("Atom: is arrayInit #1");
            // 1. Build initial Array Data, check all Subtypes equal, easy way, no boxing considered
            if (node.childrenOfType(Expr.class).stream().allMatch(e ->
                    e.type.type.equals(node.firstChildOfType(Expr.class).type.type))) {
                node.type = node.firstChildOfType(Expr.class).type;
            } else {
                throw new SemanticException("Not all value types are equal in Array init at: " +
                        node.getBeginLine() + ":" + node.getBeginColumn());
            }
            log.info("Atom: is arrayInit #2");
            // 2. Build Value
            String valueString = "";
            // If 1 Child no iteration
            if (node.childrenOfType(Expr.class).size() == 1) {
                valueString = node.firstChildOfType(Expr.class).value;
            } //more childs, make comma-list
            else {
                for (Expr e : node.childrenOfType(Expr.class)) {
                    valueString += e.value + ", ";
                }
                valueString = valueString.substring(0, valueString.length() - 2);
            }
            log.info("Atom: is arrayInit #3");

            // Add outer Parenthesis
            valueString = "[" + valueString + "]";
            node.value = valueString;

            boolean stopped = false;
            int dimCounter = 0;
            for (int i = 0; i < valueString.length(); i++) {
                if (valueString.charAt(i) == '[' && !stopped) {
                    dimCounter++;
                } else {
                    stopped = true;
                }
            }
            node.type.arrayTypeDimension = dimCounter;
            node.type.isArray = true;
            log.info("Atom: .isArrayInit: Passed up Atom-Node: " + node);

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
        } else if (node.getFirstChild() instanceof StringLiteral) {
            if (node.hasLength) { //String has definitive length
                node.type = new Type("int");
                node.value = String.valueOf(node.atomLength);
            } else if (node.isInt) {
                node.type = new Type("boolean");
                String str = node.firstChildOfType(StringLiteral.class).getImage();
                str = str.substring(1, str.length() - 1);
                boolean isInt = str.chars().allMatch(Character::isDigit);
                node.value = String.valueOf(isInt);
                log.info("Atom: Int: Str.: " + str + " Node.value: " + node.value);
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
                log.info("Atom: Double: Str.: " + str + " Node.value: " + node.value);
            } else if (node.toInt) {
                node.type = new Type("int");
                String str = node.firstChildOfType(StringLiteral.class).getImage();
                str = str.substring(1, str.length() - 1);
                try {
                    node.value = String.valueOf(Integer.parseInt(str));
                } catch (NumberFormatException e) {
                    throw new SemanticException("Could not convert String value to integer!");
                }
            } else if (node.toDouble) {
                node.type = new Type("double");
                String str = node.firstChildOfType(StringLiteral.class).getImage();
                str = str.substring(1, str.length() - 1);
                try {
                    node.value = String.valueOf(Double.parseDouble(str));
                } catch (NumberFormatException e) {
                    throw new SemanticException("Could not convert String value to double!");
                }
            }
            //Normal String
            else {
                node.type = new Type("String");
                String imageString = node.firstChildOfType(StringLiteral.class).getImage();
                node.value = imageString.substring(1, imageString.length() - 1); // Trim "
                log.info("Normal String detected: " + node.value);
            }
        } else if (node.firstChildOfType(NextStmnt.class) != null) {
            log.info("Atom: NextStmnt detected.");
            NextStmnt n = node.firstChildOfType(NextStmnt.class);
            node.value = n.value;
            node.type = n.type;
        }
        else if(node.getFirstChild() instanceof KlammerAffe){
            log.info("Atom: KlammerAffe detected.");
            KlammerAffe k = node.firstChildOfType(KlammerAffe.class);
            node.value = k.value;
            node.type = k.type;
        }
        printExit(node);
        return data;
    }

    @Override
    public Object visit(Cast node, Object data) {
        printEnter(node);
        printExit(node);
        return data;
    }

    @Override
    public Object visit(MethodCall node, Object data) {
        printEnter(node);
        printExit(node);
        return data;
    }

    @Override
    public Object visit(ArrayAccess node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);
        Expr e = node.firstChildOfType(Expr.class);
        if (!(e.type.type.equals("int") || e.type.type.equals("boolean"))) {
            throw new SemanticException("Value-Type of Array-Accessor is not int or boolean at: " +
                    node.getBeginLine() + ":" + node.getBeginColumn());
        }
        log.info("Node has only 1 child, pass up data value and type.");
        node.type = e.type;
        node.value = e.value;
        printExit(node);
        return data;
    }

    @Override
    public Object visit(ReturnStatement node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);
        Expr e = node.firstChildOfType(Expr.class);
        node.type = e.type;
        node.value = e.value;
        printExit(node);
        return data;
    }

    @Override
    public Object visit(KlammerAffe node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);
        // Immer return eines Array
        // KlammerAffeAusdruck = return "this" or value of type or type
        // :Integer: { return this; } all Integers return original value
        // :Integer: { return 1; } all Integers return 1
        // !:Integer: { return ; } all !Integers delete
        // !:Integer: { return 2; } all !Integers return 2
        List<KlammerAffeAusdruck> regexes = node.childrenOfType(KlammerAffeAusdruck.class);
        if (regexes == null) {
            node.type = new Type("String");
            node.value = node.firstChildOfType(StringLiteral.class).getImage();
            return data;
        }
        String literal = node.firstChildOfType(StringLiteral.class).getImage();
        literal = literal.substring(1, literal.length() - 1);
        //s = s.replaceAll(" ", "");
        List<String> strings = Arrays.stream(literal.split(" ")).collect(Collectors.toList());
        // For every "Regex" go throught the whole string and do the ops defined
        log.warn("Regexes: " + regexes);
        for (KlammerAffeAusdruck ka : regexes) {
            log.info("----------");
            boolean negated = ka.regexConditionalNot;
            String rType = ka.regexType.type;
            String aType = ka.actionType.type;
            String aVal = ka.actionValue;
            String regexString = null;
            if(ka.regexString != null){
                regexString = ka.regexString;
            }

            log.warn("List: " + strings + " rType: " +  rType +  " aType: " + aType + " aValue: " + aVal);
            for (int i = 0; i < strings.size(); i++) {

                //Modify Begin
                if(!negated && rType.equals("Begin")  && (i == 0)){
                    log.info("Found Begin: " + strings.get(i) + " : " + i + " : " + rType);
                    if(aType.equals("this")){} // Dont change value
                    else if(aType.equals("delete")){ strings.set(i, null);}
                    else { strings.set(i, aVal); } //Give new value
                }
                //Modify End
                else if(!negated && rType.equals("End") && (i == strings.size() - 1)){
                    log.info("Found End: " + strings.get(i));
                    if(aType.equals("this")){}
                    else if(aType.equals("delete")){ strings.set(i, null);}
                    else { strings.set(i, aVal); }
                }

                //Modify only integers
                else if (!negated && rType.equals("int") && strings.get(i).matches("-?\\d+")) {
                    log.info("Found int: " + strings.get(i));
                    if(aType.equals("this")){}
                    else if(aType.equals("delete")){ strings.set(i, null);}
                    else { strings.set(i, aVal); }
                } else if (negated && rType.equals("int") && !(strings.get(i).matches("-?\\d+"))) {
                    log.info("Found negated int: " + strings.get(i));
                    if(aType.equals("this")){}
                    else if(aType.equals("delete")){ strings.set(i, null);}
                    else { strings.set(i, aVal); }
                }
                //Modify only doubles
                else if (!negated && (rType.equals("double") && strings.get(i).matches("-?\\d+(\\.\\d+)"))) {
                    log.info("Found double: " + strings.get(i));
                    if(aType.equals("this")){}
                    else if(aType.equals("delete")){ strings.set(i, null);}
                    else { strings.set(i, aVal); }
                } else if (negated && rType.equals("double") && !(strings.get(i).matches("-?\\d+(\\.\\d+)"))) {
                    log.info("Found negated double: " + strings.get(i));
                    if(aType.equals("this")){}
                    else if(aType.equals("delete")){ strings.set(i, null);}
                    else { strings.set(i, aVal); }
                }
                //Modify only chars
                else if (!negated && rType.equals("char") && strings.get(i).matches("\\D")) {
                    log.info("Found char: " + strings.get(i));
                    if(aType.equals("this")){}
                    else if(aType.equals("delete")){ strings.set(i, null);}
                    else { strings.set(i, aVal); }
                } else if (negated && rType.equals("char") && !(strings.get(i).matches("\\D"))) {
                    log.info("Found negated char: " + strings.get(i));
                    if(aType.equals("this")){}
                    else if(aType.equals("delete")){ strings.set(i, null);}
                    else { strings.set(i, aVal); }
                }
                //Modify only booleans
                else if (!negated && rType.equals("boolean") && (strings.get(i).equals("true") || strings.get(i).equals("false"))) {
                    log.info("Found boolean: " + strings.get(i));
                    if(aType.equals("this")){}
                    else if(aType.equals("delete")){ strings.set(i, null);}
                    else { strings.set(i, aVal); }

                } else if (negated && rType.equals("boolean") && !((strings.get(i).equals("true") || strings.get(i).equals("false")))) {
                    log.info("Found negated boolean: " + strings.get(i));
                    if(aType.equals("this")){}
                    else if(aType.equals("delete")){ strings.set(i, null);}
                    else { strings.set(i, aVal); }
                }
                //Modify only certain Strings
                else if (!negated && rType.equals("String") && strings.get(i).matches("\\D\\D+")
                        && !(strings.get(i).equals("true") || strings.get(i).equals("false"))) {
                    if(regexString != null && strings.get(i).equals(regexString)) {
                        log.info("Found String: " + strings.get(i) + " that matches regex: " + regexString);
                        if (aType.equals("this")) {
                        } else if (aType.equals("delete")) {
                            strings.set(i, null);
                        } else {
                            strings.set(i, aVal);
                        }
                    } else {
                        log.info("Found String: " + strings.get(i));
                        if (aType.equals("this")) {
                        } else if (aType.equals("delete")) {
                            strings.set(i, null);
                        } else {
                            strings.set(i, aVal);
                        }
                    }
                } else if ((negated && rType.equals("String") && !(strings.get(i).matches("\\D\\D+")
                        && !(strings.get(i).equals("true") || strings.get(i).equals("false"))))
                ) {
                    if(regexString != null && !strings.get(i).equals(regexString)) {
                        log.info("Found String: " + strings.get(i) + " that matches regex: " + regexString);
                        if (aType.equals("this")) {
                        } else if (aType.equals("delete")) {
                            strings.set(i, null);
                        } else {
                            strings.set(i, aVal);
                        }
                    } else if (regexString == null) {
                        log.info("Found String: " + strings.get(i));
                        if (aType.equals("this")) {
                        } else if (aType.equals("delete")) {
                            strings.set(i, null);
                        } else {
                            strings.set(i, aVal);
                        }
                    }
                }
            }
            // After every round delete the null-values (marker for removal)
            for(Iterator<String> iter = strings.iterator(); iter.hasNext();){
                if(iter.next() == null){
                    iter.remove();
                }
            }
            log.warn("List: " + strings);
        }
        // Check if empty return empty string or
        // all int, double, char or boolean, then return a fitting array else String[]
        if(strings.isEmpty()){
            node.type = new Type("String");
            node.value = "";
        }
        else if(strings.stream().allMatch((s -> s.matches("-?\\d+")))) {
            List<Integer> integers = strings.stream().mapToInt(s -> Integer.parseInt(s)).boxed().collect(Collectors.toList());
            log.info("Final KlammerAffe is only Integers: " + integers);
            node.value = integers.toString();
            node.type = new Type(true, 1, "int");
        }
        else if(strings.stream().allMatch((s -> s.matches("-?\\d+(\\.\\d+)")))) {
            List<Double> doubles = strings.stream().mapToDouble(s -> Double.parseDouble(s)).boxed().collect(Collectors.toList());
            log.info("Final KlammerAffe is only Doubles: " + doubles);
            node.value = doubles.toString();
            node.type = new Type(true, 1, "double");
        }
        else if(strings.stream().allMatch((s -> s.matches("\\D")))) {
            List<Character> characters = strings.stream().map(c -> c.charAt(0)).collect(Collectors.toList());
            log.info("Final KlammerAffe is only Characters: " + characters);
            node.value = characters.toString();
            node.type = new Type(true, 1, "char");
        }
        else if(strings.stream().allMatch((s -> s.equals("true") || s.equals("false")))) {
            List<Boolean> booleans = strings.stream().map(s -> Boolean.valueOf(s)).collect(Collectors.toList());
            log.info("Final KlammerAffe is only Booleans: " + booleans);
            node.value = booleans.toString();
            node.type = new Type(true, 1, "boolean");
        }
        else { //Strings
            List<String> stringList = strings.stream().collect(Collectors.toList());
            log.info("Final KlammerAffe is Strings: " + stringList);
            node.value = stringList.toString();
            node.type = new Type(true, 1, "String");
        }
        //TODO Range
        printExit(node);
        return data;
    }

    @Override
    public Object visit(KlammerAffeAusdruck node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);

        //Lef side
        if (node.getFirstChild() instanceof ConditionalNot) {
            node.regexConditionalNot = true;
        }
        if (node.firstChildOfType(INTEGER_CLASS.class) != null) {
            node.regexType = new Type("int");
        } else if (node.firstChildOfType(DOUBLE_CLASS.class) != null) {
            node.regexType = new Type("double");
        } else if (node.firstChildOfType(CHAR_CLASS.class) != null) {
            node.regexType = new Type("char");
        } else if (node.firstChildOfType(BOOLEAN_CLASS.class) != null) {
            node.regexType = new Type("boolean");
        } else if (node.firstChildOfType(TypeString.class) != null) {
            node.regexType = new Type("String");
            if(node.firstChildOfType(KlammerAffeStringRegex.class) != null) {
                node.regexString = node.firstChildOfType(KlammerAffeStringRegex.class).
                        firstChildOfType(StringLiteral.class).getImage().substring(1,
                        node.firstChildOfType(KlammerAffeStringRegex.class).
                                firstChildOfType(StringLiteral.class).getImage().length() - 1);
            }
        } else if (!node.regexConditionalNot && node.getChild(1) != null &&
                node.getChild(1).getSource().equals("Begin")) {
            node.regexType = new Type("Begin");
        } else if (!node.regexConditionalNot && node.getChild(1) != null  &&
                node.getChild(1).getSource().equals("End")) {
            node.regexType = new Type("End");
        } else {
            log.warn(node.children().toString());
            for(Node n: node.children()){
                log.warn("Child: " +  n.getSource());
            }
            throw new SemanticException("KlammerAffeAusdruck: No correct left side.");
        }
        //Right side
        if (node.firstChildOfType(THIS.class) != null) {
            node.actionType = new Type("this");
            node.actionValue = "";
        } else if (node.firstChildOfType(IntegerLiteral.class) != null) {
            node.actionType = new Type("int");
            node.actionValue = node.firstChildOfType(IntegerLiteral.class).getImage();
        } else if (node.firstChildOfType(DoubleLiteral.class) != null) {
            node.actionType = new Type("double");
            node.actionValue = node.firstChildOfType(DoubleLiteral.class).getImage();
        } else if (node.firstChildOfType(CharLiteral.class) != null) {
            node.actionType = new Type("char");
            node.actionValue = node.firstChildOfType(CharLiteral.class).getImage();
        } else if (node.firstChildOfType(BooleanLiteral.class) != null) {
            node.actionType = new Type("boolean");
            node.actionValue = node.firstChildOfType(BooleanLiteral.class).getImage();
        } else if (node.firstChildOfType(StringLiteral.class) != null) {
            node.actionType = new Type("String");
            node.actionValue = node.firstChildOfType(StringLiteral.class).getImage().substring(1,
                    node.firstChildOfType(StringLiteral.class).getImage().length() - 1);
        } else {
            node.actionType = new Type("delete");
            node.actionValue = "";
        }
        printExit(node);
        return data;
    }

    @Override
    public Object visit(PrintStmnt node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);

        Expr e = node.firstChildOfType(Expr.class);
        log.warn("TEST3: " + node.getFirstChild().getClass());

        if (node.getFirstChild() instanceof PRINT_LINE) {
            System.out.println(e.value);
        } else if (node.getFirstChild() instanceof PRINT) {
            System.out.print(e.value);
        }

        printExit(node);
        return data;
    }

    @Override
    public Object visit(NextStmnt node, Object data) {
        printEnter(node);
        data = node.childrenAccept(this, data);

        if (node.getFirstChild() instanceof NEXT) {
        } else if (node.nextValue.equals("System.nextInt")) {
            Scanner s = new Scanner(System.in);
            System.out.println("Please enter the next INT");
            node.value = String.valueOf(s.nextInt());
            node.type = new Type("int");
            log.info("NextStmnt: Read INT: " + node.value);
        } else if (node.nextValue.equals("System.nextDouble")) {
            Scanner s = new Scanner(System.in);
            System.out.println("Please enter the next DOUBLE");
            node.value = String.valueOf(s.nextDouble());
            node.type = new Type("double");
            log.info("NextStmnt: Read DOUBLE: " + node.value);
        } else if (node.nextValue.equals("System.nextChar")) {
            Scanner s = new Scanner(System.in);
            System.out.println("Please enter the next CHAR");
            node.value = String.valueOf(s.next().charAt(0));
            node.type = new Type("char");
            log.info("NextStmnt: Read CHAR: " + node.value);
        } else if (node.nextValue.equals("System.nextBoolean")) {
            Scanner s = new Scanner(System.in);
            System.out.println("Please enter the next BOOLEAN");
            node.value = String.valueOf(s.nextBoolean());
            node.type = new Type("boolean");
            log.info("NextStmnt: Read BOOLEAN: " + node.value);
        } else if (node.nextValue.equals("System.nextString")) {
            Scanner s = new Scanner(System.in);
            System.out.println("Please enter the next STRING");
            node.value = s.next();
            node.type = new Type("String");
            log.info("NextStmnt: Read STRING: " + node.value);
        }
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