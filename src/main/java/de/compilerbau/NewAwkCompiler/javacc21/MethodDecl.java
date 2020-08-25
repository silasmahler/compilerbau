/* Generated by: JavaCC 21 Parser Generator. Do not edit.
 * Generated Code for MethodDecl AST Node type
 * by the ASTNode.java.ftl template
 */
package de.compilerbau.NewAwkCompiler.javacc21;

import java.util.List;

@SuppressWarnings("unused")
public class MethodDecl extends BaseNode {

    // Method-return Type
    public Type type = null;
    //is void-type?
    public boolean isVoid = false;
    //Method-ID/Name
    public ID id = null;
    //Parameters in Parameterlist
    public List<ParameterEntry> parameterList = null;
    //Statements in the Methodblock
    public List<Stmnt> statements = null;


    public Object jjtAccept(NewAwkParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }


    @Override
    public String toString() {
        return "MethodDecl{" +
                "type=" + type +
                ", id=" + id +
                ", parameterList=" + parameterList +
                ", statements=" + statements +
                '}';
    }
}
