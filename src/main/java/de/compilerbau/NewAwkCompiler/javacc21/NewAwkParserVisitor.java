/* Generated by: JavaCC 21 Parser Generator. Do not edit. NewAwkParserVisitor.java */
package de.compilerbau.NewAwkCompiler.javacc21;

public interface NewAwkParserVisitor {
    Object visit(BaseNode node, Object data);
    Object visit(CompilationUnit node, Object data); 
    Object visit(Stmnt node, Object data); 
    Object visit(Block node, Object data); 
    Object visit(VariableDecl node, Object data); 
    Object visit(VariableAssignement node, Object data); 
    Object visit(AttrDecl node, Object data); 
    Object visit(AttrAssignement node, Object data); 
    Object visit(MethodDecl node, Object data); 
    Object visit(ParameterList node, Object data); 
    Object visit(Parameter node, Object data); 
    Object visit(ExprStmnt node, Object data); 
    Object visit(IfStmnt node, Object data); 
    Object visit(Expr node, Object data); 
    Object visit(LogicalOrExpr node, Object data); 
    Object visit(LogicalAndExpr node, Object data); 
    Object visit(LogicalNotExpr node, Object data); 
    Object visit(CompExpr node, Object data); 
    Object visit(Sum node, Object data); 
    Object visit(Product node, Object data); 
    Object visit(Sign node, Object data); 
    Object visit(Atom node, Object data); 
    Object visit(MethodCall node, Object data); 
    Object visit(VariableUse node, Object data); 
    Object visit(ArrayAccess node, Object data); 
    Object visit(ArgumentList node, Object data); 
    Object visit(Type node, Object data); 
    Object visit(ReturnStatement node, Object data); 
    Object visit(KlammerAffe node, Object data); 
    Object visit(KlammerAffeRegex node, Object data); 
    Object visit(KlammerAffeAusdruck node, Object data); 
    Object visit(Token node, Object data); 
}
