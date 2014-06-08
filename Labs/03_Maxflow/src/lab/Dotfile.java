package lab;


import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Created 21.05.14 15:52.
 *
 * @author Max Weller
 * @version 2014-05-21-001
 */
public class Dotfile {



    public Hashtable<String, Vertex> vertices = new Hashtable<String, Vertex>();
    public ArrayList<Edge> edges = new ArrayList<Edge>();
    public Hashtable<String, Edge> edgeHash = new Hashtable<>();

    public String filename;

    public Dotfile(String filespec) throws Exception {
        filename = filespec;
        this.readFromFile(filespec);
    }


    /**
     * reads a dotfile at the specified location, creates Edge and Vertex objects
     * accordingly, and stores them to member variables
     * @param filename     file in Dot format to read
     * @throws Exception  IOExceptions for problems reading the file,
     * ParseExceptions from Vertex and Edge constructors for problems parsing the file
     */
    protected void readFromFile(String filename) throws Exception {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(filename));
        Scanner s = new Scanner(isr);
        String line = "";
        int lc = 0; //line counter
        while (s.hasNext()) {
            line = s.nextLine();
            lc++;
            if (lc == 0 && !line.toLowerCase().contains("digraph"))
                throw new ParseException(lc, "graph type not supported");

            if (! line.contains(";")) continue; //ignore non-item lines

            if (line.contains("->")) { //parse edge
                try {
                    Edge e = new Edge(line);
                    this.addEdge(e);

                    Edge rev = new Edge(e.to, e.from, 0);
                    this.addEdge(rev);

                    e.reverse=rev;
                    rev.reverse=e;

                } catch (Exception e) {
                    throw new ParseException(lc, e.getMessage(), e);
                }
            } else {
                try {
                    Vertex v = new Vertex(line);
                    this.vertices.put(v.name, v);
                } catch(Exception e) {
                    throw new ParseException(lc, e.getMessage(), e);
                }
            }
        }

        for (Edge e: this.edges) {
        }
    }

    public void addEdge(Edge e) {
        this.edges.add(e);
        this.edgeHash.put(e.name(), e);
        autocreateVertex(e.from);
        autocreateVertex(e.to);
        this.vertices.get(e.from).edgesFromHere.add(e);
    }
    public Edge getEdge(String name) {
        return this.edgeHash.get(name);
    }

    private void autocreateVertex(String name) {
        if (!this.vertices.containsKey(name)) {
            Vertex v = new Vertex();
            v.name = name;
            this.vertices.put(name, v);
        }
    }

    /**
     * convert to Dotfile syntax
     * @return lines of the Dot file
     */
    public ArrayList<String> toDotFile() {
        ArrayList<String> s = new ArrayList<>();
        s.add("Digraph {");
        for(Edge e:this.edges) {
            if (e.fromFile) s.add(e.toString());
        }
        for(Vertex v:this.vertices.values()) {
            if(!v.hidden()) s.add(v.toString());
        }
        s.add("}");
        return s;
    }

    /**
     * convert to dotfile syntax, without Graph type
     * @return dot file as single string
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(Edge e:this.edges) {
            s.append(e.toString()); s.append("\n");
        }
        for(Vertex v:this.vertices.values()) {
            s.append(v.toString()); s.append("\n");
        }
        return s.toString();
    }

    /**
     * produces a longer string representation of this graph, useful for debugging purposes
     */
    public String toDebugString() {
        StringBuilder s = new StringBuilder();
        for(Edge e:this.edges) {
            s.append(e.toDebugString()); s.append("\n");
        }
        for(Vertex v:this.vertices.values()) {
            s.append(v.toDebugString()); s.append("\n");
        }
        return s.toString();
    }

}
