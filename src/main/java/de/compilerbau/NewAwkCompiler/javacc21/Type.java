/* Generated by: JavaCC 21 Parser Generator. Do not edit. 
  * Generated Code for Type AST Node type
  * by the ASTNode.java.ftl template
  */
package de.compilerbau.NewAwkCompiler.javacc21;

import de.compilerbau.NewAwkCompiler.Visitors.TypeCheckingException;

import javax.lang.model.type.PrimitiveType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Type extends BaseNode {

    public boolean isArray = false;
    public int dimension = 0;
    public String type = "";

    private static  List<String> typelist = new ArrayList<String>(Arrays.asList("int", "double", "boolean", "char", "String"));

    public Object jjtAccept(NewAwkParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    @Override
    public String toString() {
        return "Type{" +
                "isArray=" + isArray +
                ", dimension=" + dimension +
                ", type='" + type + '\'' +
                '}';
    }

    static void typeCastCheck(final Type from, final Type to) {
        if (typelist.contains(from.type)) {
            if (!from.type.equals(to.type)) {
                throw new TypeCheckingException("incompatible primitive type " + from.type + " -> " + to.type);
            }
        } else if (typelist.contains(to.type)) {
            throw new TypeCheckingException("cast ClassType to PrimitiveType " + from.type + " -> " + from.type);
        } else {

        }
    }
}
