package lab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * MaxFlow.java
 */


public class MaxFlow {
	/**
	 * Return codes:
	 * 		-1 no source on the map
	 * 		-2 no destination on the map
	 * 		-3 if both source and destination points are not on the map
	 * 		-4 if no path can be found between source and destination
	 * 	MAXINT if sources identical to destinations
	 */
	public static final int NO_SOURCE_FOUND = -1;
	public static final int NO_DESTINATION_FOUND = -2;
	public static final int NO_SOURCE_DESTINATION_FOUND = -3;
	public static final int NO_PATH = -4;
	public static final int SOURCES_SAME_AS_DESTINATIONS = Integer.MAX_VALUE;

    public static final String START_NODE = ".START";
    public static final String END_NODE = ".END";

    Dotfile file;

	/**
	 * The constructor, setting the name of the file to parse.
	 * 
	 * @param filename the absolute or relative path and filename of the file
	 */
	public MaxFlow(final String filename) {
        try {
            this.file = new Dotfile(filename);
        } catch (ParseException e) {
            System.err.printf("Parse %s\n", e.toString());
        } catch (Exception e) {
            System.err.printf("Error Reading File '%s': %s (%s)\n", filename, e.getMessage(), e.getClass().getName());
        }

		
	}

    public String PathFromTo(String from, String to, String path) {
        Vertex vfrom = file.vertices.get(from);
        for(Edge fromhere : vfrom.edgesFromHere) {
            if (fromhere.capacity() <= 0) continue;
            if (path.contains("," + fromhere.to + ",")) continue;
            if (fromhere.to.equals(to)) {
                //System.out.println(fromhere.from + "---(" + fromhere.all_cap + "," + fromhere.flow + "," + fromhere.capacity() + ")--->" + fromhere.to + "  FOUND (" + path + "->" + to + ")");
                return path.substring(1) + fromhere.from + "," + fromhere.to;
            } else {
                //System.out.println(fromhere.from+"---("+fromhere.all_cap+","+fromhere.flow+","+ fromhere.capacity()+")--->"+fromhere.to+"   ("+path+"->"+to+")");
                String r = PathFromTo(fromhere.to, to, path + fromhere.from + ",");
                if (r != null) return r;
            }
        }
        return null;
    }
    public Edge[] GetPathEdges(String path) {
        String[] mypath = path.split(",");
        Edge[] edges = new Edge[mypath.length - 1];
        int i = 0;
        String lastedge = null;
        for(String edge : mypath) {
            if (lastedge != null) edges[i++] = file.getEdge(lastedge+'-'+edge);
            lastedge = edge;
        }
        return edges;
    }

    public boolean FordFulkerson(String fromNode, String toNode) {
        for(Edge e : file.edges) {
            e.flow = 0;
        }
        String pathString = PathFromTo(fromNode, toNode, ",");
        if (pathString == null) return false;
        while(pathString != null) {
            Edge[] path = GetPathEdges(pathString);
            int maxcap = FindMaxRemainingCap(path);
            MarkPathFlow(maxcap, path);
            pathString = PathFromTo(fromNode, toNode, ",");
        }
        return true;
    }

    public void MarkPathFlow(int cap, Edge[] path) {
        for(Edge edge:path) {
            edge.flow += cap;
            edge.reverse.flow -= cap;
        }
    }

    public int FindMaxRemainingCap(Edge[] path) {
        int maxcap=Integer.MAX_VALUE;
        for(Edge edge:path) {
            if(edge.capacity() < maxcap) maxcap = edge.capacity();
        }
        return maxcap;
    }
	
	/**
	 * Calculates the maximum number of cars able to travel the graph specified
	 * in filename.
	 *
	 * @param sources a list of all source nodes
	 * @param destinations a list of all destination nodes
	 * @return 	the maximum number of cars able to travel the graph,
	 * 			NO_SOURCE_FOUND if no source is on the map
	 * 			NO_DESTINATION_FOUND if no destination is on the map
	 * 			NO_SOURCE_DESTINATION_FOUND if both - no source and no destination - are not on the map
	 * 			NO_PATH if no path can be found
	 * 			SOURCES_SAME_AS_DESTINATIONS if sources == destinations 
	 */
	public final int findMaxFlow(final String[] sources, final String[] destinations) {
        boolean ford; int errno;

        //check error conditions
        errno = GetErrno(sources, destinations);
        if (errno < 0) return errno;

        //create proxy nodes for start and end
        this.MakeSourceEdges(sources);
        this.MakeDestinationEdges(destinations);

        ford = this.FordFulkerson(START_NODE, END_NODE);
        if (!ford) return NO_PATH; //returns false if no path exists at all

        // go through all adjacent nodes of start node and sum the flow values
        Vertex start = file.vertices.get(START_NODE);
        int flow = 0;
        for(Edge edge : start.edgesFromHere) {
            flow += edge.flow;
            if (flow == Integer.MAX_VALUE) break; //will wrap around otherwise
        }

		return flow;
	}
	
	/**
	 * Calculates the graph showing the maxFlow.
	 *
	 * @param sources a list of all source nodes
	 * @param destinations a list of all destination nodes
	 * @return a ArrayList of Strings as specified in the task in dot code
	 */
	public final ArrayList<String> findResidualNetwork(final String[] sources,	final String[] destinations) {
        //create proxy nodes for start and end
        this.MakeSourceEdges(sources);
        this.MakeDestinationEdges(destinations);

        this.FordFulkerson(START_NODE, END_NODE);

        for(Edge e  : file.edges) {
            if (e.capacity() != 0) e.bold = true;
        }
        System.out.println("["+file.filename+"]   " +  Max.join(sources,";")+"    --->    "+Max.join(destinations,";"));
        System.out.println(Max.joinArrayListOfString("\n", file.toDotFile()));
        return this.file.toDotFile();
	}

    public int GetErrno(final String[] sources,	final String[] destinations) {
        int sc = CountVertices(sources), dc = CountVertices(destinations);
        if (sc == 0 && dc == 0) return NO_SOURCE_DESTINATION_FOUND;
        if (sc == 0) return NO_SOURCE_FOUND;
        if (dc == 0) return NO_DESTINATION_FOUND;

        return 0;
    }

    public int CountVertices(String[] names) {
        int count = 0;
        for(String node : names) {
            if (file.vertices.containsKey(node)) count++;
        }
        return count;
    }
    public void MakeSourceEdges(String[] sources) {
        for(String node : sources) {
            this.MakeDummyEdge(START_NODE, node);
            this.file.vertices.get(node).setAttributes("doublecircle", "bold");
        }
    }
    public void MakeDestinationEdges(String[] destinations) {
        for(String node : destinations) {
            this.MakeDummyEdge(node, END_NODE);
            this.file.vertices.get(node).setAttributes("circle", "bold");
        }
    }

    public void MakeDummyEdge(String from, String to) {
        Edge e = new Edge(from, to, Integer.MAX_VALUE);
        Edge rev = new Edge(to, from, 0);
        file.addEdge(e);
        file.addEdge(rev);
        e.reverse=rev;
        rev.reverse=e;
    }

}