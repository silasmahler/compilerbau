/* Generated by: JavaCC 21 Parser Generator. Do not edit. 
  * Generated Code for VariableDeclAndAssignement AST Node type
  * by the ASTNode.java.ftl template
  */
package de.compilerbau.NewAwkCompiler.javacc21;

import java.beans.Expression;

import static de.compilerbau.NewAwkCompiler.javacc21.NewAwkConstants.TokenType.*;
@SuppressWarnings("unused")
public class VariableDeclAndAssignement extends BaseNode {

    public Type type = null;
    public ID id = null;
    public ExprStmnt exprStmnt = null;

    public Object jjtAccept(NewAwkParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

}
