package de.compilerbau.NewAwkCompiler.Visitors;

import de.compilerbau.NewAwkCompiler.javacc21.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {

    private Map<String, MethodSignature> methodTable;

    public SymbolTable() {
        methodTable = new HashMap<String, MethodSignature>();
    }

    /**
     * Find a method signature in the symbol table.
     *
     * @param methodName the method name
     * @return the method signature for the named method, or null if the named
     * method does not exist
     */
    public MethodSignature getMethodSignature(String methodName) {
        return methodTable.get(methodName);
    }

    /**
     * Add a new method signature to the symbol table.
     *
     * @param methodName the method name
     * @param type the method return type (null for procedures)
     * @param formalTypes the list of parameter types
     * @return true if the signature was added, false if a signature with the
     * given name was already present
     */
    public boolean addMethod(String methodName, Type type, List<Type> formalTypes) {
        if (methodTable.containsKey(methodName)) {
            return false;
        }
        MethodSignature sig = new MethodSignature(methodName, type, formalTypes);
        methodTable.put(methodName, sig);
        return true;
    }

}
