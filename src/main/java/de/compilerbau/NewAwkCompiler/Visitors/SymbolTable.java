package de.compilerbau.NewAwkCompiler.Visitors;

import de.compilerbau.NewAwkCompiler.javacc21.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {

    private Map<String, MethodDecl> methodDeclTable;
    private HashMap<String, List<VariableDecl>> variableDeclTable;

    public SymbolTable() {
        methodDeclTable = new HashMap<String, MethodDecl>();
        variableDeclTable = new HashMap<String, List<VariableDecl>>();
    }

    /**
     * A Method to insert a VariableDeclaration into the symbol table
     * <p>
     * //TODO Checks if node can be inserted, if it can't, it is already declared
     *
     * @param node       the node to be inserted
     * @param methodName name of the method (context) a variable is used in, leave empty string or null, if outside
     *                   method/global context
     * @return false if variable declared so method fails, true if variable not declared and method success
     */
    public boolean checkAndInsertVariableDecl(VariableDecl node, String methodName) {
        // 1.   Check context
        List<VariableDecl> decls = getVariableDeclsForContext(methodName);
        //Check if VariableDecl-List for context exists, if not create it
        if (decls == null) {
            decls = new ArrayList<VariableDecl>();
        }
        //If variable declared, you can't insert --> Error
        if (isVariableDeclared(node, methodName)) {
            return false;
        } else {
            decls.add(node);
            if (methodName != null && !methodName.equals("")) {
                variableDeclTable.put(methodName, decls);
            } else {
                variableDeclTable.put("", decls);
            }
            return true;
        }
    }

    /**
     * Returns VariableDecls for method-context or global context
     *
     * @param methodName context
     * @return the List of VariableDecls
     */
    public List<VariableDecl> getVariableDeclsForContext(String methodName) {
        //1 We are inside a method context
        if (methodName != null && !methodName.equals("")) {
            return variableDeclTable.get(methodName);
        }
        //2  We are inside global context
        return variableDeclTable.get("");
    }

    /**
     * @return true if declared, false if not
     */
    public boolean isVariableDeclared(VariableDecl node, String methodName) {
        List<VariableDecl> decls = getVariableDeclsForContext(methodName);
        if (decls != null) {
            if (decls.stream().filter(o -> o.id.getImage().equals(node.id.getImage())).findFirst().isPresent()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param node       the node to be inserted
     * @param methodName name of the method (context) a variable is used in, leave empty string or null, if outside
     *                   method/global context
     * @return false if variable has been declared or if the assignement is not possible,
     * true if the variable could be declared and assignet correctly
     */
    public boolean insertVariableDeclAndAssignement(VariableDeclAndAssignement node, String methodName) {


        //Check if inserting the variable is possible


        return false;
    }


    // Getters and Setters

    public Map<String, MethodDecl> getMethodDeclTable() {
        return methodDeclTable;
    }

    public void setMethodDeclTable(Map<String, MethodDecl> methodDeclTable) {
        this.methodDeclTable = methodDeclTable;
    }

    public HashMap<String, List<VariableDecl>> getVariableDeclTable() {
        return variableDeclTable;
    }

    public void setVariableDeclTable(HashMap<String, List<VariableDecl>> variableDeclTable) {
        this.variableDeclTable = variableDeclTable;
    }
}
