/* Generated by: JavaCC 21 Parser Generator. Do not edit. 
  * Generated Code for Type AST Node type
  * by the ASTNode.java.ftl template
  */
package de.compilerbau.NewAwkCompiler.javacc21;

import de.compilerbau.NewAwkCompiler.Visitors.SemanticException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Type extends BaseNode {

    public boolean isArray = false;
    public int arrayTypeDimension = 0;
    public String type = "";

    private static  List<String> typelist = new ArrayList<String>(Arrays.asList("int", "double", "boolean", "char", "String"));

    @Override
    public String toString() {
        return "Type{" +
                "isArray=" + isArray +
                ", arrayTypeDimension=" + arrayTypeDimension +
                ", type='" + type + '\'' +
                '}';
    }

    public Type() {
    }

    public Type(String type) {
        this.isArray = false;
        this.arrayTypeDimension = 0;
        this.type = type;
    }

    public Type(boolean isArray, int arrayTypeDimension, String type) {
        this.isArray = isArray;
        this.arrayTypeDimension = arrayTypeDimension;
        this.type = type;
    }

    public Object jjtAccept(NewAwkParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    static void typeCastCheck(final Type from, final Type to) {
        if (typelist.contains(from.type)) {
            if (!from.type.equals(to.type)) {
                throw new SemanticException("incompatible primitive type " + from.type + " -> " + to.type);
            }
        } else if (typelist.contains(to.type)) {
            throw new SemanticException("cast ClassType to PrimitiveType " + from.type + " -> " + from.type);
        } else {

        }
    }
}
