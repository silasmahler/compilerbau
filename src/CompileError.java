public class CompileError {
    String message;
    int column, line;

    public CompileError(String message, int line, int column) {
        this.message = message;
        this.line = line;
        this.column = column;
    }

    public String toString() {
        return "Error at line " + line + ", column " + column + ": " + message;
    }
}