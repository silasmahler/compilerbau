/* Generated by: JavaCC 21 Parser Generator. Do not edit. 
  * Generated Code for Type AST Node type
  * by the ASTNode.java.ftl template
  */
package de.compilerbau.NewAwkCompiler.javacc21;

import static de.compilerbau.NewAwkCompiler.javacc21.NewAwkConstants.TokenType.*;
@SuppressWarnings("unused")
public class Type extends BaseNode {
    public boolean isArray = false;
    public String dataType = "";
    public Object jjtAccept(NewAwkParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    @Override
    public String toString() {
        return "Type{" +
                "isArray=" + isArray +
                ", dataType='" + dataType + '\'' +
                '}';
    }
}
