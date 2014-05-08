package lab;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created 08.05.14 09:49.
 *
 * @author Max Weller
 * @version 2014-05-08-001
 */
public class Vertex {
    //private final Pattern dotPattern = Pattern.compile("^([A-Z]+): \\[label=\"([^\"]+)\"\\];$");
    private final Pattern dotPattern = Pattern.compile("^([A-Z]+) \\[label=\"([A-Z]+),([0-9]+)\"\\];$");

    public String name, label;
    public int waitingTime;
    public ArrayList<Edge> edgesFromHere = new ArrayList<>();

    public Vertex(String dot) throws Exception {
        Matcher m = dotPattern.matcher(dot);
        if (m.matches()) {
            name = m.group(1);
            label = m.group(2);
            waitingTime = Integer.valueOf(m.group(3));
        } else {
            throw new Exception("Invalid Vertex Syntax");
        }
    }

    public Edge getEdgeTo(String vertexName) {
        for(Edge e: this.edgesFromHere) {
            if (e.to.equals(vertexName)) return e;
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s [label=\"%s,%d\"];", this.name, this.label, this.waitingTime);
    }

    public String toDebugString() {
        String edges = "";
        for(Edge e: edgesFromHere) {
            edges += e.to + " ";
        }
        return String.format("%s: [label=%s][waitingTime=%d][edgesFromHere=%s]", this.name, this.label, this.waitingTime, edges);
    }

}
