/* Generated by: JavaCC 21 Parser Generator. Do not edit. 
  * Generated Code for ParameterList AST Node type
  * by the ASTNode.java.ftl template
  */
package de.compilerbau.NewAwkCompiler.javacc21;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("unused")
public class ParameterList extends BaseNode {

    public List<Parameter> parameterList;

    public Object jjtAccept(NewAwkParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    public ParameterList() {
        this.parameterList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ParameterList{" +
                "parameterList=" + parameterList +
                '}';
    }
}
