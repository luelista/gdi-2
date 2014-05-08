package frame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;

import java.util.ArrayList;
import lab.Navigation;

public class AllTests extends TestCase{

	private enum OutputFormat {Distance, Time};
	
	/**
	 * @param args program arguments
	 */
	public static void main(final String... args) {
		junit.textui.TestRunner.run(suite());
	}
	
	/**
	 * @return a test adapter for graphical plug-ins
	 */
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(AllTests.class);
	}
	
		
	/**
	 * Here follow the actual test cases
	 */

	@Test public final void testFile1_A_C_Distance() {
		assertEquals("From A to C: ", 8, testDistance("testfile1.txt","A","C"));
	}
	@Test public final void testFile1_A_G_Distance() {
		assertEquals("From A to G: ", 24, testDistance("testfile1.txt","A","G"));
	}
	@Test public final void testFile1_A_D_Time() {
		assertEquals("From A to D: ", 14, testTime("testfile1.txt","A","D"));
	}
	@Test public final void testFile1_A_F_Time() {
		assertEquals("From A to F: ", 18, testTime("testfile1.txt","A","F"));
	}
	@Test public final void testfile1_Route_A_E_Distance(){
		ArrayList<String> route = new ArrayList < String >();
		Navigation lab = new Navigation("testfile1.txt");
		// Build the route we expect to find
		route.add("A\\s*->\\s*C");
		route.add("C\\s*->\\s*D");
		route.add("D\\s*->\\s*E");	
		assertTrue("Route not correct", this.testRoute(lab.findShortestRoute("A","E"), route));
	}
	@Test public final void testfile1_Route_A_F_Time(){
		ArrayList<String> route = new ArrayList < String >();
		Navigation lab = new Navigation("testfile1.txt");
		// Build the route we expect to find
		route.add("A\\s*->\\s*C");
		route.add("C\\s*->\\s*D");
		route.add("D\\s*->\\s*F");
		assertTrue("Route not correct", this.testRoute(lab.findFastestRoute("A","F"), route));
	}
	
	@Test public final void testFile2_A_C_Distance() {
		assertEquals("From A to C: ", 8, testDistance("testfile2.txt","A","C"));
	}
	@Test public final void testFile2_A_D_Distance() {
		assertEquals("From A to D: ", 12, testDistance("testfile2.txt","A","D"));
	}
	@Test public final void testFile2_A_F_Distance() {
		assertEquals("From A to F: ", 16, testDistance("testfile2.txt","A","F"));
	}
	@Test public final void testFile2_A_C_Time() {
		assertEquals("From A to C: ", 8, testTime("testfile2.txt","A","C"));
	}
	@Test public final void testFile2_A_E_Time() {
		assertEquals("From A to E: ", 15, testTime("testfile2.txt","A","E"));
	}
	@Test public final void testFile2_A_F_Time() {
		assertEquals("From A to F: ", 12, testTime("testfile2.txt","A","F"));
	}
	@Test public final void testFile2_Size() {
		Navigation lab = new Navigation("testfile2.txt");
		assertEquals("Number of entries in output map: ", 16, lab.findShortestRoute("A","B").size());	
	}
	@Test public final void testFile2_Negative() {
		Navigation lab = new Navigation("testfile2.txt");
		assertEquals("Test non-existing node: ", -2 , lab.findShortestDistance("A","G"));	
	}
	@Test public final void testfile2_Route_A_E_Distance(){
		ArrayList<String> route = new ArrayList < String >();
		Navigation lab = new Navigation("testfile2.txt");
		// Build the route we expect to find
		route.add("A\\s*->\\s*B");
		route.add("B\\s*->\\s*C");
		route.add("C\\s*->\\s*D");
		route.add("D\\s*->\\s*E");	
		assertTrue("Route not correct", this.testRoute(lab.findShortestRoute("A","E"), route));
	}
	@Test public final void testfile2_Route_A_E_Time(){
		ArrayList<String> route = new ArrayList<>();
		Navigation lab = new Navigation("testfile2.txt");
		// Build the route we expect to find
		route.add("A\\s*->\\s*B");
		route.add("B\\s*->\\s*C");
		route.add("C\\s*->\\s*E");
		assertTrue("Route not correct", this.testRoute(lab.findFastestRoute("A","E"), route));
	}
	
	/**
	 * This method returns the shortest distance from start to
	 * stop on the map stored in filename.
	 * 
	 * It also writes the output map to a file. The file name follows
	 * the following format:
	 * 
	 * output_[filename]_from[start]to[stop]Distance.txt
	 * 
	 * @param filename The name of the file storing the map
	 * @param start Source node
	 * @param stop Destination node
	 * @return The shortest distance between start and stop in km
	 */
	private final int testDistance(String filename, String start, String stop){
		Navigation lab = new Navigation(filename);
		ArrayList<String> returnMap = new ArrayList < String >();
				
		returnMap = lab.findShortestRoute(start,stop);
		writeGraphToFile(returnMap, filename, start, stop, OutputFormat.Distance);
		return lab.findShortestDistance(start,stop);
	}
	
	/**
	 * This method returns the fastest route from start to
	 * stop on the map stored in filename.
	 * 
	 * It also writes the output map to a file. The file name follows
	 * the following format:
	 * 
	 * output_[filename]_from[start]to[stop]Time.txt
	 * 
	 * @param filename The name of the file storing the map
	 * @param start Source node
	 * @param stop Destination node
	 * @return Fastest route in minutes
	 */
	private final int testTime(String filename, String start, String stop){
		Navigation lab = new Navigation(filename);
		ArrayList < String > returnMap = new ArrayList < String >();
				
		returnMap = lab.findFastestRoute(start,stop);
		writeGraphToFile(returnMap, filename, start, stop, OutputFormat.Time);
		return lab.findFastestTime(start,stop);
	}

	/**
	 * This method tests wether the edges contained in boldEdges
	 * are present and marked as bold in map
	 * 
	 * @param map The map to check, in dot format
	 * @param boldEdges The edges to find
	 * @return True if all edges in boldEdges are marked bold in map
	 */
	private final boolean testRoute(ArrayList < String > map,
								   ArrayList < String > boldEdges) {
		boolean correct = true;
		int matches = 0;
		for (String edge: boldEdges) { // for all edges we're looking for
			for (String line : map) { // for all lines in the map
				if (line.matches(".*"+edge+".*")) { // if the edge is there
					correct = correct && line.matches(".*bold.*"); // check if it is bold
					matches++; // Count the number of bold lines correctly found
				}
			}
		}	
		// Check if we found all of them
		correct = correct && (matches == boldEdges.size());
		return correct;
	}
		
	/**
	 * This method writes a map to file
	 * 
	 * The format of the filename of the file created depends
	 * on the last four parameters:
	 * 
	 * if format = OutputForma.Time:
	 * 		output_[filename]_from[start]to[stop]Time.txt
	 * if format = OutputForma.Distance
	 * 		if format = OutputForma.Distance
	 * 
	 * @param map
	 * @param filename
	 * @param start
	 * @param stop
	 * @param format
	 */
	public final void writeGraphToFile(ArrayList < String > map, 
									   String filename, 
									   String start, 
									   String stop, 
									   OutputFormat format) {
		try {
			String typeString = null;
			switch (format) {
			case Distance:
				typeString = "distance"; break;
			case Time:
				typeString = "time"; break;			
			}
						
			FileWriter fw = new FileWriter("output_" + filename + "_from" + start
					+ "to" + stop + typeString + ".txt");
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (String element : map) {
				bw.write(element); 
				bw.newLine();
			}
			bw.flush(); bw.close(); fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
