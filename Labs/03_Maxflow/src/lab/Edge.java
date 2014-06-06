package lab;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Representation of an edge in a dot file
 *
 * @author Max Weller
 * @version 2014-05-08-001
 */
public class Edge {
    //private final Pattern dotPattern = Pattern.compile("^([A-Z]+) -> ([A-Z]+): \\[label=\"([^\"]+)\"\\](\\[style=bold\\])?;$");
    private final Pattern dotPattern = Pattern.compile("(\\w+)\\s*->\\s*(\\w+)\\s*\\[label=\"([0-9]+)(-([0-9]+))?\"\\](\\[style=bold\\])?;");

    public String from, to;
    // public String label;
    public int cOriginal, cUsed;


    public boolean bold;

    public Edge(String dot) throws Exception {
        Matcher m = dotPattern.matcher(dot);
        if (m.matches()) {
            from = m.group(1);
            to = m.group(2);

            //label = m.group(2);
            cOriginal = Integer.valueOf(m.group(3));
            if (m.group(4) != null) cUsed = Integer.valueOf(m.group(5));

            if (m.group(6) != null) bold = true;

        } else {
            throw new Exception("Invalid Edge Syntax: "+dot);
        }
    }

    @Override
    public String toString() {
        return String.format("%s -> %s [label=\"%d-%d\"]%s;", this.from, this.to, this.cOriginal, this.cUsed, this.bold ? "[style=bold]" : "");
    }

    public String toDebugString() {
        return String.format("%s -> %s: [cOriginal=%d][cUsed=%d][bold=%s]", this.from, this.to, this.cOriginal, this.cUsed, String.valueOf(bold));
    }

    public String name() {
        return from+'-'+to;
    }
    public String revname() {
        return to+'-'+from;
    }



}
