package de.compilerbau.NewAwkCompiler.Visitors;

import de.compilerbau.NewAwkCompiler.javacc21.Type;

public class ArrayTypeAndValue {
    public Type type;
    public String value;

    public ArrayTypeAndValue() {
    }

    public ArrayTypeAndValue(Type type, String value) {
        this.type = type;
        this.value = value;
    }


    @Override
    public String toString() {
        return "ArrayTypeAndValue{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
