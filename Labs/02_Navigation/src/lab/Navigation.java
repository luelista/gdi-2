package lab;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * The class Navigation finds the shortest (and/or) path between points on a map
 * using the Dijkstra algorithm
 */
public class Navigation {
	/**
	 * Return codes: -1 if the source is not on the map -2 if the destination is
	 * not on the map -3 if both source and destination points are not on the
	 * map -4 if no path can be found between source and destination
	 */
  
	public static final int SOURCE_NOT_FOUND = -1;
	public static final int DESTINATION_NOT_FOUND = -2;
	public static final int SOURCE_DESTINATION_NOT_FOUND = -3;
	public static final int NO_PATH = -4;

    /**
     * whether debug messages should be sent to stdout
     */
    public static final boolean DO_DEBUG = false;
  
  
    public Hashtable<String, Vertex> vertices = new Hashtable<String, Vertex>();
    public ArrayList<Edge> edges = new ArrayList<Edge>();

    public String filename;

	/**
	 * The constructor takes a filename as input, it reads that file and fill
	 * the nodes and edges Lists with corresponding node and edge objects
	 * 
	 * @param filename
	 *            name of the file containing the input map
	 */
	public Navigation(String filename) {
        this.filename = filename;
        try {
            this.readFromFile(filename);
        } catch (ParseException e) {
            System.err.printf("Parse %s\n", e.toString());
        } catch (Exception e) {
            System.err.printf("Error Reading File '%s': %s\n", filename, e.getMessage());
        }
	}

    /**
     * reads a dotfile at the specified location, creates Edge and Vertex objects
     * accordingly, and stores them to member variables
     * @param filename     file in Dot format to read
     * @throws Exception  IOExceptions for problems reading the file,
     * ParseExceptions from Vertex and Edge constructors for problems parsing the file
     */
    public void readFromFile(String filename) throws Exception {
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
                    this.edges.add(new Edge(line));
                } catch (Exception e) {
                    throw new ParseException(lc, e.getMessage());
                }
            } else {
                try {
                    Vertex v = new Vertex(line);
                    this.vertices.put(v.name, v);
                } catch(Exception e) {
                    throw new ParseException(lc, e.getMessage());
                }
            }
        }

        for (Edge e: this.edges) {
            this.vertices.get(e.from).edgesFromHere.add(e);
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
            s.add(e.toString());
        }
        for(Vertex v:this.vertices.values()) {
            s.add(v.toString());
        }
        s.add("}");
        if (DO_DEBUG) System.out.println(Max.joinArrayListOfString("\n", s));
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

    /**
     * the main Dijkstra algorithm. this method uses the member variables vertices and edges
     * @param startNode   start node Q_0 for algorithm
     * @param mapper  an instance of a class implementing the EdgeMapper interface which allows
     *                mapping edges and vertices to their weights
     * @return the 'previous nodes' hash array
     */
    public Hashtable<String, String> dijkstra(String startNode, Edge.EdgeMapper mapper) {
        // declare and initialize temporary tables and lists
        Hashtable<String, Double> abstand = new Hashtable<>();
        Hashtable<String, String> vorgaenger = new Hashtable<>();
        ArrayList<Vertex> Q = new ArrayList<>(this.vertices.size());

        // fill in default distances of infinity, add all vertices to list of remaining vertices (Q)
        for(Vertex v: this.vertices.values()) {
            abstand.put(v.name, Double.POSITIVE_INFINITY);
            Q.add(v);
        }
        // start node always has distance 0, of course
        abstand.put(startNode, 0.0);

        while(Q.size() >= 1) {
            Vertex u = priorityExtractMin(abstand, Q);
            Q.remove(u);
            if (DO_DEBUG) System.out.println("Taken "+u.name+" from Q ("+abstand.get(u.name) + ")");
            for(Edge e: u.edgesFromHere) {
                Vertex v = this.vertices.get(e.to);
                if (Q.contains(v)) {
                    dijkstraUpdateDistance(u, v, abstand, vorgaenger, mapper);
                }
            }
        }
        return vorgaenger;
    }

    /**
     * determine the vertex from Q with the lowest rank in 'abstand'
     * @see java.util.PriorityQueue (was not used because priority needs to be updated)
     * @param abstand   ranking hash table
     * @param Q         the priority queue, represented as an arraylist
     * @return          lowest ranked vertex
     */
    private Vertex priorityExtractMin(Hashtable<String, Double> abstand, ArrayList<Vertex> Q) {
        Vertex minVertex = null; double min = Double.POSITIVE_INFINITY;
        for(Vertex v : Q) {
            if (abstand.get(v.name) < min || minVertex == null) {
                minVertex = v; min = abstand.get(v.name);
            }
        }
        return minVertex;
    }

    /**
     * updates the distance if neccessary. part of dijkstra algorithm
     * @param u             first vertex
     * @param v             second vertex
     * @param abstand       ranking hash table, to be updated
     * @param vorgaenger    previous nodes, to be updated
     * @param mapper        an instance of a class implementing the EdgeMapper interface which allows
     *                      mapping edges and vertices to their weights
     */
    private void dijkstraUpdateDistance(Vertex u, Vertex v,
                                        Hashtable<String, Double> abstand,
                                        Hashtable<String, String> vorgaenger,
                                        Edge.EdgeMapper mapper) {
        double altDist = abstand.get(u.name) + mapper.mapVertex(u) + mapper.map(u.getEdgeTo(v.name));
        if (DO_DEBUG) System.out.println("u = [" + u + "], v = [" + v + "], altDist = [" + altDist + "], abstand = [" + abstand.get(v.name) + "],");
        if (altDist < abstand.get(v.name)) {
            abstand.put(v.name, altDist);
            vorgaenger.put(v.name, u.name);
        }
    }

    /**
     * set the 'bold' property for all nodes on the path to endNode according to a
     * given hash of previous nodes
     * @param way   the path, usually calculated from dijkstra's algo
     * @param endNode   end node
     */
    public void routeMakeBold(Hashtable<String, String> way, String endNode) {
        //reset boldness
        for(Edge e : this.edges)
            e.bold = false;

        String u = endNode, v;
        while (way.get(u) != null) {
            v = way.get(u);
            if (DO_DEBUG) System.out.printf("%s -> %s\n", v, u);
            this.vertices.get(v).getEdgeTo(u).bold = true;
            u = v;
            //result.add(0, u);
        }
    }

    /**
     * sum the distance ranks along a path given by a hash of previous nodes, a start node, and an end node
     * @param way          the path, usually calculated from dijkstra's algo
     * @param startNode    the start node
     * @param endNode      end node
     * @param mapper       an instance of a class implementing the EdgeMapper interface which allows
     *                     mapping edges and vertices to their weights
     * @return the calculated sum
     */
    public int routeCalculateSum(Hashtable<String, String> way, String startNode, String endNode, Edge.EdgeMapper mapper) {
        // check for non-existing nodes
        if ((! this.vertices.containsKey(startNode)) && (! this.vertices.containsKey(endNode))) return SOURCE_DESTINATION_NOT_FOUND;
        if (! this.vertices.containsKey(startNode)) return SOURCE_NOT_FOUND;
        if (! this.vertices.containsKey(endNode)) return DESTINATION_NOT_FOUND;
        
        // go through the path starting at end node
        double sum = 0.0;
        String u = endNode, v;
        while (way.get(u) != null) {
            // get the preceding node of the current node
            v = way.get(u);
            if (DO_DEBUG) System.out.printf("%s -> %s\n", v, u);
            // retrieve the edge connecting the preceding node (v) and the current node (u)
            Edge e = this.vertices.get(v).getEdgeTo(u);
            // map the edge to a distance
            sum += mapper.map(e);
            u = v;
            // map the node to a additional distance (ignore on final iteration as per 2.2)
            if (!u.equals(startNode)) sum += mapper.mapVertex(this.vertices.get(v));
            //result.add(0, u);
        }
        // if start node was not reached, return error code
        if (u.equals(startNode) == false) return NO_PATH;

        return (int)Math.ceil(sum);
    }

	/**
	 * This methods finds the shortest route (distance) between points A and B
	 * on the map given in the constructor.
	 * 
	 * If a route is found the return value is an object of type
	 * ArrayList<String>, where every element is a String representing one line
	 * in the map. The output map is identical to the input map, apart from that
	 * all edges on the shortest route are marked "bold". It is also possible to
	 * output a map where all shortest paths starting in A are marked bold.
	 * 
	 * The order of the edges as they appear in the output may differ from the
	 * input.
	 * 
	 * @param A
	 *            Source
	 * @param B
	 *            Destinaton
	 * @return returns a map as described above if A or B is not on the map or
	 *         if there is no path between them the original map is to be
	 *         returned.
	 */
	public ArrayList<String> findShortestRoute(String A, String B) {
        if (DO_DEBUG) System.out.printf("\n%s: shortest %s -> %s\n", this.filename, A, B);
        Hashtable<String, String> vorgaenger;
        vorgaenger = dijkstra(A, new Edge.DistanceMapper());

        this.routeMakeBold(vorgaenger, B);

        return this.toDotFile();
	}

	/**
	 * This methods finds the fastest route (in time) between points A and B on
	 * the map given in the constructor.
	 * 
	 * If a route is found the return value is an object of type
	 * ArrayList<String>, where every element is a String representing one line
	 * in the map. The output map is identical to the input map, apart from that
	 * all edges on the shortest route are marked "bold". It is also possible to
	 * output a map where all shortest paths starting in A are marked bold.
	 * 
	 * The order of the edges as they appear in the output may differ from the
	 * input.
	 * 
	 * @param A
	 *            Source
	 * @param B
	 *            Destinaton
	 * @return returns a map as described above if A or B is not on the map or
	 *         if there is no path between them the original map is to be
	 *         returned.
	 */
	public ArrayList<String> findFastestRoute(String A, String B) {
        if (DO_DEBUG) System.out.printf("\n%s: fastest %s -> %s\n", this.filename, A, B);
        Hashtable<String, String> vorgaenger;
        vorgaenger = dijkstra(A, new Edge.SpeedMapper());

        this.routeMakeBold(vorgaenger, B);

        return this.toDotFile();
	}

	/**
	 * Finds the shortest distance in kilometers between A and B using the
	 * Dijkstra algorithm.
	 * 
	 * @param A
	 *            the start point A
	 * @param B
	 *            the destination point B
	 * @return the shortest distance in kilometers rounded upwards.
	 *         SOURCE_NOT_FOUND if point A is not on the map
	 *         DESTINATION_NOT_FOUND if point B is not on the map
	 *         SOURCE_DESTINATION_NOT_FOUND if point A and point B are not on
	 *         the map NO_PATH if no path can be found between point A and point
	 *         B
	 */
	public int findShortestDistance(String A, String B) {
        if (DO_DEBUG) System.out.printf("\n%s: shortest %s -> %s\n", this.filename, A, B);
        Hashtable<String, String> vorgaenger;
        vorgaenger = dijkstra(A, new Edge.DistanceMapper());

        return this.routeCalculateSum(vorgaenger, A, B, new Edge.DistanceMapper());
	}

	/**
	 * Find the fastest route between A and B using the dijkstra algorithm.
	 * 
	 * @param pointA
	 *            Source
	 * @param pointB
	 *            Destination
	 * @return the fastest time in minutes rounded upwards. SOURCE_NOT_FOUND if
	 *         point A is not on the map DESTINATION_NOT_FOUND if point B is not
	 *         on the map SOURCE_DESTINATION_NOT_FOUND if point A and point B
	 *         are not on the map NO_PATH if no path can be found between point
	 *         A and point B
	 */
	public int findFastestTime(String pointA, String pointB) {
        System.out.printf("\n%s: fastest %s -> %s\n", this.filename, pointA, pointB);
        Hashtable<String, String> vorgaenger;
        vorgaenger = dijkstra(pointA, new Edge.SpeedMapper());

        return this.routeCalculateSum(vorgaenger, pointA, pointB, new Edge.SpeedMapper());
	}

}
