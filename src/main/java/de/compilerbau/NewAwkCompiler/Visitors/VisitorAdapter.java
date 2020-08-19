package de.compilerbau.NewAwkCompiler.Visitors;

import de.compilerbau.NewAwkCompiler.javacc21.*;

/**
 * Class for basic error reporting when calling the AST Transformation
 */
public class VisitorAdapter implements NewAwkParserVisitor {

    public Object visit(BaseNode node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(CompilationUnit node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(Stmnt node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(Block node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(Assignement node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(VariableDecl node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(VariableDeclAndAssignement node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(MethodDecl node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }

    public Object visit(ParameterList node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() +
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(IfStmnt node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(ExprStmnt node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(Expr node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(Atom node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(Cast node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(MethodCall node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(ArrayAccess node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(ArrayLength node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(Type node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(ReturnStatement node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(KlammerAffe node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(KlammerAffeRegex node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(KlammerAffeAusdruck node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(PrintStmnt node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }


    public Object visit(Token node, Object data) {
        throw new Error("Visitor called on unexpected AST node of class: " + node.getClass() + 
                "\n with content: " + node.toString() + " See if a visitor hasn't implemented it.");
    }
}
