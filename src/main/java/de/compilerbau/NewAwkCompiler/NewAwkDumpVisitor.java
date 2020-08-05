package de.compilerbau.NewAwkCompiler;

import de.compilerbau.NewAwkCompiler.javacc21.*;

public class NewAwkDumpVisitor implements NewAwkParserVisitor {

    private int indent = 0;

    // Visitors can include any number of helper fields and methods, like
    // the indent variable above and the following method
    // indentString(), which is used during the dumping

    private String indentString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < indent; ++i) {
            sb.append("-");
        }
        return sb.toString();
    }

    @Override
    public Object visit(BaseNode node, Object data) {
        System.out.println(indentString() + node);
        ++indent;
        data = node.childrenAccept(this, data);
        --indent;
        return data;    }

    @Override
    public Object visit(CompilationUnit node, Object data) {
        System.out.println(indentString() + node);
        ++indent;
        data = node.childrenAccept(this, data);
        --indent;
        return data;    }

    @Override
    public Object visit(Stmnt node, Object data) {
        System.out.println(indentString() + node);
        ++indent;
        data = node.childrenAccept(this, data);
        --indent;
        return data;    }

    @Override
    public Object visit(Block node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(VariableDecl node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(VariableAssignement node, Object data) {
        System.out.println(indentString() + node);
        ++indent;
        data = node.childrenAccept(this, data);
        --indent;
        return data;    }

    @Override
    public Object visit(AttrDecl node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(AttrAssignement node, Object data) {
        return null;
    }

    @Override
    public Object visit(MethodDecl node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(ParameterList node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(Parameter node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(ExprStmnt node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(IfStmnt node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(Expr node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(LogicalOrExpr node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(LogicalAndExpr node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(LogicalNotExpr node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(CompExpr node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(Sum node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(Product node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(Sign node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(Atom node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(MethodCall node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(VariableUse node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(ArrayAccess node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(ArgumentList node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(Type node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(ReturnStatement node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }

    @Override
    public Object visit(KlammerAffe node, Object data) {
        System.out.println(indentString() + node);
        ++indent;
        data = node.childrenAccept(this, data);
        --indent;
        return data;    }

    @Override
    public Object visit(KlammerAffeRegex node, Object data) {
        System.out.println(indentString() + node);
        ++indent;
        data = node.childrenAccept(this, data);
        --indent;
        return data;    }

    @Override
    public Object visit(KlammerAffeAusdruck node, Object data) {
        System.out.println(indentString() + node);
        ++indent;
        data = node.childrenAccept(this, data);
        --indent;
        return data;    }

    @Override
    public Object visit(Token node, Object data) {
            System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
    }
}
