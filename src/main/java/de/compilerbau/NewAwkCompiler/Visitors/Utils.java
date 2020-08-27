package de.compilerbau.NewAwkCompiler.Visitors;

import de.compilerbau.NewAwkCompiler.javacc21.Node;
import de.compilerbau.NewAwkCompiler.javacc21.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);


    public boolean checkTypeIsEqual(Type type1, Type type2) {
        log.info("Type1: " + type1.type + " Type2: " + type2.type);
        if (type1.type.equals(type2)) {
            return true;
        }
        return false;
    }

    /**
     * Possible casts
     * <p>
     * int, double, char, boolean => String
     * int => double
     *
     * @param type1 Input-Type
     * @param type2 Result-Type
     * @return true if possible
     */
    public boolean checkTypeIsCastable(Type type1, Type type2) {
        //If type is equal
        if (checkTypeIsEqual(type1, type2)) {
            log.warn("Types are equal, no cast is needed at lines " + type1.getBeginLine() +
                    "till " + type2.getEndLine());
            return true;
        }
        // int => double
        if (type1.type.equals("int") && type2.type.equals("double")) {
            return true;
        }
        // int, double, char, boolean => String
        if ((type1.type.equals("int")
                || type1.type.equals("double")
                || type1.type.equals("char")
                || type1.type.equals("boolean")
        )
                && type2.type.equals("String")) {
            return true;
        }
        return false;
    }

}
