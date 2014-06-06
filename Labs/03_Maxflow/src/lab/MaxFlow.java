package lab;

import java.util.ArrayList;
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

    Dotfile file;

    public Hashtable<String, Integer> flow = new Hashtable<>();
    public Hashtable<String, Integer> capacity = new Hashtable<>();

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

    public String PathFromTo(String from, String to) {
        Vertex vfrom = file.vertices.get(from);
        for(Edge fromhere : vfrom.edgesFromHere) {
            if (capacity.get(fromhere.name()) - flow.get(fromhere.name()) <= 0) continue;
            if (fromhere.to.equals(to))
                return from+","+to;
            else {
                String r = PathFromTo(fromhere.to, to);
                if (r != null) return from + "," + r;
            }
        }
        return null;
    }

    public void FordFulkerson(String fromNode, String toNode) {
        for(Edge e : file.edges) {
            flow.put(e.name(), 0);
            flow.put(e.revname(), 0);
            capacity.put(e.name(), e.cOriginal);
            capacity.put(e.revname(), 0);
        }
        String path = PathFromTo(fromNode, toNode);
        while(path != null) {
            
            path = PathFromTo(fromNode, toNode);
        }

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
		//TODO Add you code here
		return 0; // dummy, replace
	}
	
	/**
	 * Calculates the graph showing the maxFlow.
	 *
	 * @param sources a list of all source nodes
	 * @param destinations a list of all destination nodes
	 * @return a ArrayList of Strings as specified in the task in dot code
	 */
	public final ArrayList<String> findResidualNetwork(final String[] sources,	final String[] destinations) {
		//TODO Add you code here
		return null; // dummy, replace
	}

}