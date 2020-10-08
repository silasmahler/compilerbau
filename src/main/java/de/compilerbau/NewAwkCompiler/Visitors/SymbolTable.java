package de.compilerbau.NewAwkCompiler.Visitors;

import de.compilerbau.NewAwkCompiler.javacc21.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new SemanticException("Variable: " + id.getImage()
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
        log.info("getArrayValAndTypeForIDAndIntAccess: Try to get value from Array");
        log.info("Symboltable: VariableDecls: " + variableDeclTable);
        VariableDecl variableDecl = findVariableDeclFromID(id, context);
        if (variableDecl.value == null) {
            throw new SemanticException("getArrayValAndTypeForIDAndIntAccess: The Array hasnt been " +
                    "initialized or assigned a value and it can't be accessed.");
        }
        String[] values = variableDecl.value.substring(1, variableDecl.value.length() - 1)
                .replaceAll("\\s", "").split(",");
        ArrayTypeAndValue typeAndValue = new ArrayTypeAndValue();
        Type t = variableDecl.type;
        if (t.type.equals("int") || t.type.equals("double") || t.type.equals("char") ||
                t.type.equals("boolean") || t.type.equals("String")) {
            typeAndValue.type = variableDecl.type;
            typeAndValue.value = values[accessIndex];
        } else {
            throw new SemanticException("getArrayValAndTypeForIDAndIntAccess: Invalid Type!");
        }
        log.info("Returning ArrayTypeAndValue: " + typeAndValue);
        return typeAndValue;
        /**
         //Save
         int[] test = {1,2,3,5};
         String value = Arrays.toString(test);
         // Unsave
         String[] strings = value.substring(1, value.length()-1).replaceAll("\\s","").split(",");
         for(String s: strings) {
         log.info(s);
         }*/
    }

    public ArrayTypeAndValue getArrayAccessValAndTypeForIDAndInts(ID id, List<Integer> accessIndexes, String context) {
        log.info("getArrayAccessValAndTypeForIDAndInts: Try to get value from Array");
        VariableDecl variableDecl = findVariableDeclFromID(id, context);
        if (variableDecl.value == null) {
            throw new SemanticException("getArrayAccessValAndTypeForIDAndInts: The Array hasnt been " +
                    "initialized or assigned a value and it can't be accessed.");
        }
        log.info("Params: ID:" + id + " AccessIndexes: " + accessIndexes + " Context: " + context);
        log.info("VariableDecl: " + variableDecl);

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
        log.info("Dimension: " + dimension + " AccessIndexCount: " + accessIndexes.size());
        //int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        //int[] b = a[1];
        //int c = a[1][0];

        int accessIndexCount = accessIndexes.size();
        //No need to Check accessIndexCount == 0, because Visitor Takes care of it,
        // Its technically no access, just passing the value
        // If both = e.g. 3 then return value inside array-leaf
        if (dimension == accessIndexCount) {
            log.info("1. SomeString: Input " + someString);
            //1. Leerzeichen entfernen, Trim
            someString = someString.replaceAll("\\s", "");
            log.info("2. SomeString: No spaces" + someString);
            String regexBrackets = "";
            String regexBrackets2 = "";
            for (int i = 0; i < dimension; i++) {
                regexBrackets += "\\[";
                regexBrackets2 += "]";
            }
            someString = someString.replaceAll(regexBrackets, "");
            someString = someString.replaceAll(regexBrackets2, "");
            log.info("3. SomeString: Trimmed " + someString);

            //2. Split -> select object -> split --> rootvalue; split dim -1 x => 2; last split ","
            //3 return; Objectsplit: Works with multiple or one objects
            for (int i = 0; i < accessIndexCount; i++) {
                log.info("Runde: " + (i + 1));
                // If nicht letzte Runde
                if (i != accessIndexCount - 1) {
                    log.info("Nicht letzte Runde!");
                    // Finde Element
                    String splitString = ",";
                    for (int j = 1; j < (dimension - i); j++) {
                        splitString = "]" + splitString + "\\[";
                    }
                    log.info("SplitString: " + splitString);
                    List<String> objects = Arrays.stream(someString.split(splitString)).collect(Collectors.toList());
                    log.info("Objects: " + objects);
                    String element = objects.get(accessIndexes.get(i));
                    log.info("Element: " + element);
                    someString = element;
                }
                //If letzte Runde
                else {
                    log.info("Letzte Runde!");
                    String[] objects = someString.split(",");
                    String element = objects[accessIndexes.get(i)];
                    log.info("Element: " + element);
                    someString = element;
                    Type t = variableDecl.type;
                    t.isArray = false;
                    t.arrayTypeDimension = 0; //TODO Gucken ob korrekt
                    ArrayTypeAndValue value = new ArrayTypeAndValue(t, someString);
                    log.info("Returning ArrayTypeAndValue: " + value);
                    return value;
                }
            }
        }
        // else if less accessors, then build array on Ebene and return it
        else if (dimension > accessIndexCount) {
            log.info("1. SomeString: Input " + someString);
            //1. Leerzeichen entfernen, Trim
            someString = someString.replaceAll("\\s", "");
            log.info("2. SomeString: No spaces" + someString);
            for (int i = 0; i < accessIndexCount; i++) {
                // Dim = 3
                // Access = 2 [[[1,2,3],[4,5,6],[7,8,9]],[[1,2,3],[5,5,5],[7,8,9]]]
                // Entferne außen (jew. 1 Klammer trimmen),
                someString = someString.substring(1, someString.length() - 1);
                log.info("3. SomeString: Trimmed for iteration " + someString);
                //Ermittle Replacer: ]],[[ (Ebene 2) oder ],[ (Ebene 1) -> Schleife von accessIndexCount bis 1
                String replacerString = ",";
                String splitterString = "@";
                for (int j = accessIndexCount - i; 0 < j; j--) {
                    replacerString = "]" + replacerString + "[";
                    splitterString = "]" + splitterString + "[";
                }
                splitterString = splitterString.replaceAll("\\[", "[");
                log.info("4. ReplacerString: " + replacerString + " SplitterString: " + splitterString);
                // Replace: ]],[[  mit ]@[    [[1,2,3],[4,5,6],[7,8,9]],[[1,2,3],[5,5,5],[7,8,9]]
                someString = someString.replace(replacerString, splitterString);
                log.info("5. SomeString: Replaced " + someString);
                someString = someString.replace("@\\", "@");
                // Split: an @  [[1,2,3],[4,5,6],[7,8,9]]@[[1,2,3],[5,5,5],[7,8,9]]
                log.info("6. SomeString: Replaced @ fix " + someString);
                List<String> objects = Pattern.compile("@").splitAsStream(someString)
                        .collect(Collectors.toList());
                //List<String> objects = Arrays.stream(someString.split(splitterString)).collect(Collectors.toList());
                log.info("6. SomeString: Splitted " + objects.get(0));
                // Access = 2 [[[1,2,3],[4,5,6],[7,8,9]],[[1,2,3],[5,5,5],[7,8,9]]]
                // Access = 2 [[[1,2,3]],[[1,2,3]],[[1,2,3]]]
                log.info("Objects: " + objects);
                String element = objects.get(accessIndexes.get(i));
                log.info("Element: " + element);
                someString = element;
            }
            Type t = variableDecl.type;
            t.isArray = true;
            t.arrayTypeDimension = 0; //TODO BERECHNEN!
            ArrayTypeAndValue value = new ArrayTypeAndValue(t, someString);
            log.info("Returning ArrayTypeAndValue: " + value);
            return value;
        } else { // if more => Error
            throw new SemanticException("getArrayAccessValAndTypeForIDAndInts: ArrayAccess has more Accesses than" +
                    "there are dimensions! Please reduce accessors.");
        }
        return null;
    }
}
