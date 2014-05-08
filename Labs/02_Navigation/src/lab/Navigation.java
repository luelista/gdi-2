package lab;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    public Hashtable<String, Vertex> vertices = new Hashtable<String, Vertex>();
    public ArrayList<Edge> edges = new ArrayList<Edge>();

	/**
	 * The constructor takes a filename as input, it reads that file and fill
	 * the nodes and edges Lists with corresponding node and edge objects
	 * 
	 * @param filename
	 *            name of the file containing the input map
	 */
	public Navigation(String filename) {
        try {
            this.readFromFile(filename);
        } catch (ParseException e) {
            System.err.printf("Parse %s\n", e.toString());
        } catch (Exception e) {
            System.err.printf("Error Reading File '%s': %s\n", filename, e.getMessage());
        }
	}

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

    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("user.dir"));

        Navigation n = new Navigation("Labs/02_Navigation/testfile1.txt");
        System.out.println(n.toDebugString());

        System.out.println(n.findShortestRoute("A", "D"));
    }



    public Hashtable<String, String> dijkstra(String startNode, Edge.EdgeMapper mapper) {
        final Hashtable<String, Double> abstand = new Hashtable<>();
        Hashtable<String, String> vorgaenger = new Hashtable<>();
        PriorityQueue<Vertex> Q = new PriorityQueue<>(1, new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return (int)(abstand.get(o1.name) - abstand.get(o2.name));
            }
        });

        this.initDijkstra(startNode, abstand, vorgaenger, Q);

        while(! Q.isEmpty()) {
            Vertex u = Q.poll();
            for(Edge e: u.edgesFromHere) {
                Vertex v = this.vertices.get(e.to);
                if (Q.contains(v)) {
                    dijkstraUpdateDistance(u, v, abstand, vorgaenger, mapper);
                }
            }
        }
        return vorgaenger;
    }
/*
 1  Funktion Dijkstra(Graph, Startknoten):
 2      initialisiere(Graph,Startknoten,abstand[],vorgänger[],Q)
 3      solange Q nicht leer:                       // Der eigentliche Algorithmus
 4          u := Knoten in Q mit kleinstem Wert in abstand[]
 5          entferne u aus Q                                // für u ist der kürzeste Weg nun bestimmt
 6          für jeden Nachbarn v von u:
 7              falls v in Q:
 8                 distanz_update(u,v,abstand[],vorgänger[])   // prüfe Abstand vom Startknoten zu v
 9      return vorgänger[]
 */
    public void initDijkstra(String startNode, Hashtable<String, Double> abstand, Hashtable<String, String> vorgaenger, PriorityQueue<Vertex> Q) {
        for(Vertex v: this.vertices.values()) {
            abstand.put(v.name, Double.POSITIVE_INFINITY);
            //vorgaenger.put(v.name, null);
            Q.add(v);
        }
        //reset boldness
        for(Edge e : this.edges)
            e.bold = false;
        abstand.put(startNode, 0.0);
    }

/*
 1  Methode distanz_update(u,v,abstand[],vorgänger[]):
 2      alternativ := abstand[u] + abstand_zwischen(u, v)   // Weglänge vom Startknoten nach v über u
 3      falls alternativ < abstand[v]:
 4          abstand[v] := alternativ
 5          vorgänger[v] := u
 */
    private void dijkstraUpdateDistance(Vertex u, Vertex v,
                                        Hashtable<String, Double> abstand,
                                        Hashtable<String, String> vorgaenger,
                                        Edge.EdgeMapper mapper) {
        double alternativ = abstand.get(u.name) + mapper.map(u.getEdgeTo(v.name));
        if (alternativ < abstand.get(v.name)) {
            abstand.put(v.name, alternativ);
            vorgaenger.put(v.name, u.name);
        }
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
        Hashtable<String, String> vorgaenger;
        vorgaenger = dijkstra(A, new Edge.DistanceMapper());

        //ArrayList<String> result = new ArrayList<>();
        //result.add(B);
        String u = B, v;
        while (vorgaenger.get(u) != null) {
            v = vorgaenger.get(u);
            System.out.printf("%s -> %s\n", v, u);
            this.vertices.get(v).getEdgeTo(u).bold = true;
            u = v;

            //result.add(0, u);
        }

        return Max.stringArrayToArrayList(this.toString().split("\n"));
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
        Hashtable<String, String> vorgaenger;
        vorgaenger = dijkstra(A, new Edge.SpeedMapper());

        //ArrayList<String> result = new ArrayList<>();
        //result.add(B);
        String u = B, v;
        while (vorgaenger.get(u) != null) {
            v = vorgaenger.get(u);
            System.out.printf("%s -> %s\n", v, u);
            this.vertices.get(v).getEdgeTo(u).bold = true;
            u = v;

            //result.add(0, u);
        }

        return Max.stringArrayToArrayList(this.toString().split("\n"));
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
		//TODO Add you code here
		int sd = 0; 

		return sd;
	}

	/**
	 * Find the fastest route between A and B using the dijkstra algorithm.
	 * 
	 * @param A
	 *            Source
	 * @param B
	 *            Destination
	 * @return the fastest time in minutes rounded upwards. SOURCE_NOT_FOUND if
	 *         point A is not on the map DESTINATION_NOT_FOUND if point B is not
	 *         on the map SOURCE_DESTINATION_NOT_FOUND if point A and point B
	 *         are not on the map NO_PATH if no path can be found between point
	 *         A and point B
	 */
	public int findFastestTime(String pointA, String pointB) {
		//TODO Add you code here
		int ft = 0;

		return ft;
	}

}
