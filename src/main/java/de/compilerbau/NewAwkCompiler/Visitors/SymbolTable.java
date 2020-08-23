package de.compilerbau.NewAwkCompiler.Visitors;

import de.compilerbau.NewAwkCompiler.javacc21.MethodDecl;
import de.compilerbau.NewAwkCompiler.javacc21.Type;
import de.compilerbau.NewAwkCompiler.javacc21.VariableDecl;
import de.compilerbau.NewAwkCompiler.javacc21.VariableDeclAndAssignement;

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
     * @return false if variable declared, true if success
     */
    public boolean insertVariableDecl(VariableDecl node, String methodName) {
        // 1.   Check context
        List<VariableDecl> decls;

        // 1.1  We are inside a method context
        if (methodName != null && !methodName.equals("")) {
            //Get list of declarations
            decls = variableDeclTable.get(methodName);
            for (VariableDecl v : decls) {
                if(v.id == node.id){
                    return false;
                }
            }
            decls.add(node);
            variableDeclTable.put(methodName, decls);
        }
        //1.2   We are inside global context
        else {
            decls = variableDeclTable.get("");
            if(decls == null){
                decls = new ArrayList<VariableDecl>();
                decls.add(node);
                variableDeclTable.put("", decls);
                return true;
            }
            for (VariableDecl v : decls) {
                if(v.id.getImage().equals(node.id.getImage())){
                    return false;
                }
            }
            decls.add(node);
            variableDeclTable.put("", decls);
            return true;
        }
        return false;
    }

    /**
     *
     * @param node the node to be inserted
     * @param methodName name of the method (context) a variable is used in, leave empty string or null, if outside
     *                   method/global context
     * @return false if variable has been declared or if the assignement is not possible,
     *         true if the variable could be declared and assignet correctly
     */
    public boolean insertVariableDeclAndAssignement(VariableDeclAndAssignement node, String methodName){


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
