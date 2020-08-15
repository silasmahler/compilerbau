/* Generated by: JavaCC 21 Parser Generator. Do not edit. NewAwkParserVisitor.java */
package de.compilerbau.NewAwkCompiler.javacc21;

public interface NewAwkParserVisitor {
    Object visit(BaseNode node, Object data);
    Object visit(CompilationUnit node, Object data); 
    Object visit(Stmnt node, Object data); 
    Object visit(Block node, Object data); 
    Object visit(Assignement node, Object data); 
    Object visit(VariableDecl node, Object data); 
    Object visit(VariableDeclAndAssignement node, Object data); 
    Object visit(MethodDecl node, Object data); 
    Object visit(IfStmnt node, Object data); 
    Object visit(ExprStmnt node, Object data); 
    Object visit(Expr node, Object data); 
    Object visit(Atom node, Object data); 
    Object visit(Cast node, Object data); 
    Object visit(MethodCall node, Object data); 
    Object visit(ArrayAccess node, Object data); 
    Object visit(ArrayLength node, Object data); 
    Object visit(Type node, Object data); 
    Object visit(ReturnStatement node, Object data); 
    Object visit(KlammerAffe node, Object data); 
    Object visit(KlammerAffeRegex node, Object data); 
    Object visit(KlammerAffeAusdruck node, Object data); 
    Object visit(PrintStmnt node, Object data); 
    Object visit(Token node, Object data); 
}
