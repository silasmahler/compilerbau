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
    public Object visit(Parameter node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.Parameter");
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
    public Object visit(LogicalOrExpr node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.LogicalOrExpr");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(LogicalAndExpr node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.LogicalAndExpr");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(LogicalNotExpr node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.LogicalNotExpr");
        data = node.childrenAccept(this, data);
        if(!data.equals(LogicalNotExpr.class)){
            return NewAwkConstants.TokenType.ConditionalNot;
        }

        return data;
    }

    @Override
    public Object visit(CompExpr node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.CompExpr");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(Sum node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.Sum " + node.getBeginLine() + " " +  node.getBeginColumn());

        if (!(node instanceof Sum)) {
            throw new TypeCheckingException("Sum ");
        }

        double sum = 0.0;
        System.out.println("Test123: " + node.getChild(0));
        for (Product s : node.childrenOfType(Product.class)) {
            System.out.println("Sum with possible products detected: " + s.toString() + "\n 444 " + s.getAttributeNames());


            sum += Double.valueOf(s.toString());
        }

        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(Product node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.Product");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(Sign node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.Sign");
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(Atom node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.Atom");
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
    public Object visit(ArgumentList node, Object data) {
        System.out.println("Enter TypeCheckVisitor: visit.ArgumentList");
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