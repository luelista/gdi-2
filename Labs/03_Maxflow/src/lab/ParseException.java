package lab;

/**
 * Thrown on syntax errors in Dot files
 *
 * @author Max Weller
 * @version 2014-05-08-001
 */
public class ParseException extends Exception {
    public int lineNumber;
    public ParseException(int lineNumber, String message) {
        super(message);
        this.lineNumber = lineNumber;
    }
    public ParseException(int lineNumber, String message, Exception e) {
        super(e.getClass().getName() + ": " + message);
        this.lineNumber = lineNumber;
    }
    public String toString() {
        return String.format("Error in line %d: %s", this.lineNumber, this.getMessage());
    }
}
