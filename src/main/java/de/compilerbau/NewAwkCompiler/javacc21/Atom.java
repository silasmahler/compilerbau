/* Generated by: JavaCC 21 Parser Generator. Do not edit. 
  * Generated Code for Atom AST Node type
  * by the ASTNode.java.ftl template
  */
package de.compilerbau.NewAwkCompiler.javacc21;

import static de.compilerbau.NewAwkCompiler.javacc21.NewAwkConstants.TokenType.*;
@SuppressWarnings("unused")
public class Atom extends BaseNode {

    public boolean hasLength = false;
    public int atomLength = 0;

    public Object jjtAccept(NewAwkParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    @Override
    public String toString() {
        return "Atom{" +
                "hasLength=" + hasLength +
                ", atomLength=" + atomLength +
                '}';
    }
}
