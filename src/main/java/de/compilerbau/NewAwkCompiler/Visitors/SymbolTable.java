package de.compilerbau.NewAwkCompiler.Visitors;

import de.compilerbau.NewAwkCompiler.javacc21.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {

    private static final Logger log = LoggerFactory.getLogger(SymbolTable.class);

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
        List<VariableDecl> decls = getVariableDeclsForContext(methodName);
        //Check if VariableDecl-List for context exists, if not create it
        if (decls == null) {
            decls = new ArrayList<>();
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
            return decls.stream().filter(
                    o -> o.id.getImage().equals(node.id.getImage())).findFirst().isPresent();
        }
        return false;
    }

    /**
     * @param id
     * @param value
     * @param methodName
     */
    public void updateVariableDeclValue(Type type, ID id, String value, String methodName) {
        //Check if list for context
        List<VariableDecl> decls = getVariableDeclsForContext(methodName);
        //If no List for context, create and add element, then done
        if (decls == null) {
            decls = new ArrayList<>();
            decls.add(new VariableDecl(type, id, value));
        }
        //Get element to update and udate it
        else {
            for (VariableDecl decl : decls) {
                if (decl.id.getImage().equals(id.getImage()) &&
                        decl.type.type.equals(type.type)
                ) {
                    decl.value = value;
                }
            }
        }
        if (methodName != null && !methodName.equals("")) {
            variableDeclTable.put(methodName, decls);
        } else {
            variableDeclTable.put("", decls);
        }
    }

    /**
     * Method that searches for an Id value in symboltable
     * and gives back the {@link VariableDecl}
     *
     * @param id         ID to search
     * @param methodName context
     * @return
     */
    public VariableDecl findVariableDeclFromID(ID id, String methodName) {
        return getVariableDeclsForContext(methodName).stream().filter(
                o -> o.id.getImage().equals(id.getImage()))
                .findFirst()
                .orElseThrow(() -> new TypeCheckingException("Variable: " + id.getImage()
                        + " hasn't been defined, it wasn't found in the SymbolTable."));
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

    public ArrayTypeAndValue getArrayValAndTypeForIDAndIntAccess(ID id, int accessIndex, String context) {
        log.warn("getArrayValForIDAndInt: Try to get value from Array");
        VariableDecl variableDecl = findVariableDeclFromID(id, context);
        if (variableDecl.value == null) {
            throw new TypeCheckingException("getArrayValAndTypeForIDAndIntAccess: The Array hasnt been " +
                    "initialized or assigned a value and it can't be accessed.");
        }
        String[] values = variableDecl.value.substring(1, variableDecl.value.length() - 1)
                .replaceAll("\\s", "").split(",");
        ArrayTypeAndValue typeAndValue = new ArrayTypeAndValue();
        if (variableDecl.type.type.equals("int")) {
            typeAndValue.type = new Type("int");
            typeAndValue.value = values[accessIndex];
        } else if (variableDecl.type.type.equals("double")) {
            typeAndValue.type = new Type("double");
            typeAndValue.value = values[accessIndex];
        } else if (variableDecl.type.type.equals("char")) {
            typeAndValue.type = new Type("char");
            typeAndValue.value = values[accessIndex];
        } else if (variableDecl.type.type.equals("boolean")) {
            typeAndValue.type = new Type("boolean");
            typeAndValue.value = values[accessIndex];
        } else if (variableDecl.type.type.equals("String")) {
            typeAndValue.type = new Type("String");
            typeAndValue.value = values[accessIndex];
        } else {
            throw new TypeCheckingException("getArrayValAndTypeForIDAndIntAccess: Invalid Type!");
        }
        return typeAndValue;
        /**
         //Save
         int[] test = {1,2,3,5};
         String value = Arrays.toString(test);
         // Unsave
         String[] strings = value.substring(1, value.length()-1).replaceAll("\\s","").split(",");
         for(String s: strings) {
         log.warn(s);
         }*/
    }

    public ArrayTypeAndValue getArrayAccessValAndTypeForIDAndInts(ID id, List<Integer> accessIndexes, String context) {
        log.warn("getArrayAccessValAndTypeForIDAndInts: Try to get value from Array");
        VariableDecl variableDecl = findVariableDeclFromID(id, context);
        if (variableDecl.value == null) {
            throw new TypeCheckingException("getArrayAccessValAndTypeForIDAndInts: The Array hasnt been " +
                    "initialized or assigned a value and it can't be accessed.");
        }
        log.warn("Params: ID:" + id + " AccessIndexes: " + accessIndexes + " Context: " + context);
        log.warn("VariableDeclValue: " + variableDecl.value);

        //Detect dimension
        String someString = variableDecl.value;
        char someChar = '[';
        int dimension = 0;
        boolean finished = false;

        for (int i = 0; (i < someString.length() && !finished); i++) {
            if (someString.charAt(i) == someChar) {
                dimension++;
            } else {
                finished = true;
            }
        }
        log.warn("Dimension: " + dimension);
        int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[] b = a[1];

        int accessIndexCount = accessIndexes.size();
        //TODO for every dimension:
        // 1. Check accessIndexCount
        // If both = e.g. 3 then return value inside array-leaf
        if (dimension == accessIndexCount) {
            //TODO
        }
        // else if less accessors, then build array on Ebene and return it
        else if (dimension > accessIndexCount) {
            //TODO
        } else { // if more => Error
            throw new TypeCheckingException("getArrayAccessValAndTypeForIDAndInts: ArrayAccess has more Accesses than" +
                    "there are dimensions! Please reduce accessors.");
        }
        // TODO 2.


        String[] values = variableDecl.value.substring(1, variableDecl.value.length() - 1)
                .replaceAll("\\s", "")
                .replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .split(",");
        for (String s : values) {
            log.warn(s);
        }

        ArrayTypeAndValue typeAndValue = new ArrayTypeAndValue();
       /* if (variableDecl.type.type.equals("int")) {
            typeAndValue.type = new Type("int");
            typeAndValue.value = values[accessIndex];
        } else if (variableDecl.type.type.equals("double")) {
            typeAndValue.type = new Type("double");
            typeAndValue.value = values[accessIndex];
        } else if (variableDecl.type.type.equals("char")) {
            typeAndValue.type = new Type("char");
            typeAndValue.value = values[accessIndex];
        } else if (variableDecl.type.type.equals("boolean")) {
            typeAndValue.type = new Type("boolean");
            typeAndValue.value = values[accessIndex];
        } else if (variableDecl.type.type.equals("String")) {
            typeAndValue.type = new Type("String");
            typeAndValue.value = values[accessIndex];
        } else {
            throw new TypeCheckingException("getArrayAccessValAndTypeForIDAndInts: Invalid Type!");
        }*/
        return typeAndValue;
    }
}
