package lab;

/**
 * Created 08.05.14 10:00.
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
    public String toString() {
        return String.format("Error in line %d: %s", this.lineNumber, this.getMessage());
    }
}
