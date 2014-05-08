package lab;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created 08.05.14 09:49.
 *
 * @author Max Weller
 * @version 2014-05-08-001
 */
public class Edge {
    //private final Pattern dotPattern = Pattern.compile("^([A-Z]+) -> ([A-Z]+): \\[label=\"([^\"]+)\"\\](\\[style=bold\\])?;$");
    private final Pattern dotPattern = Pattern.compile("([A-Z]+) -> ([A-Z]+) \\[label=\"([0-9]+),([0-9]+)\"\\](\\[style=bold\\])?;");

    public String from, to;
    // public String label;
    public int distance, speedLimit;
    public boolean bold;

    public Edge(String dot) throws Exception {
        Matcher m = dotPattern.matcher(dot);
        if (m.matches()) {
            from = m.group(1);
            to = m.group(2);

            //label = m.group(2);
            distance = Integer.valueOf(m.group(3));
            speedLimit = Integer.valueOf(m.group(4));

            if (m.group(5) != null) bold = true;

        } else {
            throw new Exception("Invalid Edge Syntax: "+dot);
        }
    }

    @Override
    public String toString() {
        return String.format("%s -> %s: [label=\"%d,%d\"]%s", this.from, this.to, this.distance, this.speedLimit, this.bold ? "[style=bold]" : "");
    }

    public String toDebugString() {
        return String.format("%s -> %s: [distance=%d][speedlimit=%d][bold=%s]", this.from, this.to, this.distance, this.speedLimit, String.valueOf(bold));
    }


    public interface EdgeMapper {
        public int map(Edge e);
    }

    public static class DistanceMapper implements EdgeMapper {
        @Override
        public int map(Edge e) {
            return e.distance;
        }
    }
    public static class SpeedMapper implements EdgeMapper {
        @Override
        public int map(Edge e) {
            return e.speedLimit;
        }
    }

}
