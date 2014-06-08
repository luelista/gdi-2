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
    public int all_cap = 0;
    public int flow = 0;

    public Edge reverse;

    public boolean bold;

    public boolean fromFile = false;

    public int capacity() {
        return all_cap - flow;
    }

    public Edge(String dot) throws Exception {
        Matcher m = dotPattern.matcher(dot);
        if (m.matches()) {
            from = m.group(1);
            to = m.group(2);

            //label = m.group(2);
            all_cap = Integer.valueOf(m.group(3));
            if (m.group(4) != null) flow = Integer.valueOf(m.group(5));

            if (m.group(6) != null) bold = true;

            fromFile = true;
        } else {
            throw new Exception("Invalid Edge Syntax: "+dot);
        }
    }
    public Edge(String source, String sink, int capacity) {
        this.from = source; this.to = sink; this.all_cap = capacity;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s [label=\"%d-%d\"]%s;", this.from, this.to, this.all_cap, this.flow, this.bold ? "[style=bold]" : "");
    }

    public String toDebugString() {
        return String.format("%s -> %s: [all_capacity=%d][flow=%d][bold=%s]", this.from, this.to, this.all_cap, this.flow, String.valueOf(bold));
    }

    public String name() {
        return from+'-'+to;
    }



}
