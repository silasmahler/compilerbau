/* Generated by: JavaCC 21 Parser Generator. Do not edit. 
  * Generated Code for MethodDecl AST Node type
  * by the ASTNode.java.ftl template
  */
package de.compilerbau.NewAwkCompiler.javacc21;

import static de.compilerbau.NewAwkCompiler.javacc21.NewAwkConstants.TokenType.*;
@SuppressWarnings("unused")
public class MethodDecl extends BaseNode {

    public String id;

    public Object jjtAccept(NewAwkParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

}
