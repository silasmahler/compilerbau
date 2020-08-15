package de.compilerbau.NewAwkCompiler.Visitors;

import de.compilerbau.NewAwkCompiler.javacc21.*;

public class NewAwkTypeCheckVisitor implements NewAwkParserVisitor {

    @Override
    public Object visit(BaseNode node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.BaseNode");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(CompilationUnit node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.CompilationUnit");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(Stmnt node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.Stmnt");
        data = node.childrenAccept(this, data);
        return data;

    }

    @Override
    public Object visit(Block node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.Block");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(VariableDecl node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.VariableDecl");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(Assignement node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.Assignement");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(MethodDecl node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.MethodDecl");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ParameterList node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.ParameterList");
        data = node.childrenAccept(this, data);
        return data;
    }
    @Override
    public Object visit(ExprStmnt node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.ExprStmnt");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(IfStmnt node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.IfStmnt");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(Expr node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.Expr");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(Atom node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.Expr");
        data = node.childrenAccept(this, data);
        return data;    }


    /**@Override
    public Object visit(Sum node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.Sum " + node.getBeginLine() + " " + node.getBeginColumn());
        data = node.childrenAccept(this, data);

        if (!(node instanceof Sum)) {
            throw new TypeCheckingException("Sum ");
        }

        double sum = 0.0;
        System.out.println("Test123: " + node.getChild(0));
        for (Product s : node.childrenOfType(Product.class)) {
            System.out.println("Sum with possible products detected: " + s.toString());
        }

        return data;
    }

    @Override
    public Object visit(Atom node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.Atom");
        data = node.childrenAccept(this, data);

        System.out.println("Atom found on Line: " + node.getBeginLine() + " - " + node.getEndLine() +
                " and Column: " + node.getBeginColumn() + " - " + node.getEndColumn() +
                " \n with Content: " + node.toString());

        if (node.getFirstChild() instanceof Cast) {
            //check right side for possibility to cast (e.g. double to int "yes", String to int "no")

        }
        if (node.children().isEmpty()) {
            throw new TypeCheckingException("Atom has no children token.");
        } else if (node.children().size() == 1) {
             switch (node.getFirstChild()) {
                case (node.getFirstChild() instanceof BooleanLiteral):
                    break;
                case(node.getFirstChild() instanceof IntegerLiteral):
                    break;
                case(node.getFirstChild() instanceof DoubleLiteral):
                    break;
                case(node.getFirstChild() instanceof StringLiteral):
                    break;
                case(node.getFirstChild() instanceof CharLiteral):
                    break;
                case(node.getFirstChild() instanceof NullLiteral):
                    break;
                case(node.getFirstChild() instanceof CharLiteral):
                    break;
                default:
                    throw new TypeCheckingException("No possible type was detected.");
                    break;
            }
        } else if (node.children().size() > 1) {

        }

        return data;
    }*/

    @Override
    public Object visit(Cast node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.Cast");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(MethodCall node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.MethodCall");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ArrayAccess node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.ArrayAccess");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ArrayLength node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.ArrayLength");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(Type node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.Type");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ReturnStatement node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.ReturnStatement");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(KlammerAffe node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.KlammerAffe");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(KlammerAffeRegex node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.KlammerAffeRegex");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(KlammerAffeAusdruck node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.KlammerAffeAusdruck");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(PrintStmnt node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.PrintStmnt");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(Token node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.Token");
        data = node.childrenAccept(this, data);
        return data;
    }
}