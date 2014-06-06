package frame;
/**
 * MaxFlowTestCase.java
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import lab.MaxFlow;


public class AllTests {
	
	@Rule
	public Timeout globalTimeout = new Timeout(10000);
	
	@Test
	public final void testFindMaxFlow_Iksburg1_A_to_F() {
		String[] sources = {"A"};
		String[] destinations = {"F"};
		assertEquals("MaxFlow not correct!", 140, testMaxFlow("Iksburg1.txt", sources, destinations));
	}
	
	//new test
	@Test
	public final void testFindMaxFlow_Iksburg1_B_to_F() {
		String[] sources = {"A"};
		String[] destinations = {"F"};
		assertEquals("MaxFlow not correct!", 140, testMaxFlow("Iksburg1.txt", sources, destinations));
	}
	
	@Test
	public final void testFindMaxFlow_Iksburg1_no_source() {
		String[] sources = {"X"};
		String[] destinations = {"F"};
		assertEquals("MaxFlow not correct!", -1, testMaxFlow("Iksburg1.txt", sources, destinations));
	}
	
	@Test
	public final void testFindMaxFlow_Iksburg1_no_dest() {
		String[] sources = {"A"};
		String[] destinations = {"Y"};
		assertEquals("MaxFlow not correct!", -2, testMaxFlow("Iksburg1.txt", sources, destinations));
	}
	
	@Test
	public final void testFindMaxFlow_Iksburg1_no_source_no_dest() {
		String[] sources = {"X"};
		String[] destinations = {"Y"};
		assertEquals("MaxFlow not correct!", -3, testMaxFlow("Iksburg1.txt", sources, destinations));
	}
	
	@Test
	public final void testFindMaxFlow_Iksburg1_no_path() {
		String[] sources = {"F"};
		String[] destinations = {"A"};
		assertEquals("MaxFlow not correct!", -4, testMaxFlow("Iksburg1.txt", sources, destinations));
	}
	
	@Test
	public final void testFindMaxFlow_Iksburg1_source_identical_dest() {
		String[] sources = {"B"};
		String[] destinations = {"B"};
		assertEquals("MaxFlow not correct!", Integer.MAX_VALUE, testMaxFlow("Iksburg1.txt", sources, destinations));
	}
	
	@Test
	public final void testFindMaxFlow_Iksburg2_A_to_H() {
		String[] sources = {"A"};
		String[] destinations = {"H"};
		assertEquals("MaxFlow not correct!", 240, testMaxFlow("Iksburg2.txt", sources, destinations));
	}
	
	@Test
	public final void testFindMaxFlow_Iksburg2_no_source() {
		String[] sources = {"X"};
		String[] destinations = {"H"};
		assertEquals("MaxFlow not correct!", -1, testMaxFlow("Iksburg2.txt", sources, destinations));
	}
	
	@Test
	public final void testFindMaxFlow_Iksburg2_no_dest() {
		String[] sources = {"A"};
		String[] destinations = {"Y"};
		assertEquals("MaxFlow not correct!", -2, testMaxFlow("Iksburg2.txt", sources, destinations));
	}
	
	@Test
	public final void testFindMaxFlow_Iksburg2_no_source_no_dest() {
		String[] sources = {"X"};
		String[] destinations = {"Y"};
		assertEquals("MaxFlow not correct!", -3, testMaxFlow("Iksburg2.txt", sources, destinations));
	}
	
	@Test
	public final void testFindMaxFlow_Iksburg2_no_path() {
		String[] sources = {"H"};
		String[] destinations = {"A"};
		assertEquals("MaxFlow not correct!", -4, testMaxFlow("Iksburg2.txt", sources, destinations));
	}
	
	@Test
	public final void testFindMaxFlow_Iksburg2_source_identical_dest() {
		String[] sources = {"F"};
		String[] destinations = {"F"};
		assertEquals("MaxFlow not correct!", Integer.MAX_VALUE, testMaxFlow("Iksburg2.txt", sources, destinations));
	}
	
	@Test
	public final void testFindMaxFlow_Iksburg3_AB_to_FG() {
		String[] sources = {"A", "B"};
		String[] destinations = {"F", "G"};
		assertEquals("MaxFlow not correct!", 30, testMaxFlow("Iksburg3.txt", sources, destinations));
	}	
	
	@Test
	public final void testFindMaxFlow_Iksburg3_no_source() {
		String[] sources = {"X", "Y"};
		String[] destinations = {"F", "G"};
		assertEquals("MaxFlow not correct!", -1, testMaxFlow("Iksburg3.txt", sources, destinations));
	}
	
	@Test
	public final void testFindMaxFlow_Iksburg3_no_dest() {
		String[] sources = {"A", "B"};
		String[] destinations = {"X", "Y"};
		assertEquals("MaxFlow not correct!", -2, testMaxFlow("Iksburg3.txt", sources, destinations));
	}
	
	@Test
	public final void testFindMaxFlow_Iksburg3_no_source_no_dest() {
		String[] sources = {"X", "Y"};
		String[] destinations = {"Z", "W"};
		assertEquals("MaxFlow not correct!", -3, testMaxFlow("Iksburg3.txt", sources, destinations));
	}
	
	@Test
	public final void testFindMaxFlow_Iksburg3_no_path() {
		String[] sources = {"F"};
		String[] destinations = {"B"};
		assertEquals("MaxFlow not correct!", -4, testMaxFlow("Iksburg3.txt", sources, destinations));
	}
	
	@Test
	public final void testFindMaxFlow_Iksburg3_source_identical_dest() {
		String[] sources = {"A", "B"};
		String[] destinations = {"A", "B"};
		assertEquals("MaxFlow not correct!", Integer.MAX_VALUE, testMaxFlow("Iksburg3.txt", sources, destinations));
	}
		
	//new test
	@Test
	public final void testFindMaxFlow_Iksburg4_Luisenplatz_Kantplatz_to_ILPlatz() {
		String[] sources = {"Luisenplatz", "Kantplatz"};
		String[] destinations = {"Ilse_Langner_Platz"};
		assertEquals("MaxFlow not correct!", 42, testMaxFlow("Iksburg4.txt", sources, destinations));
	}	
	
	//new test
	@Test
	public final void testFindMaxFlow_Iksburg4_no_source() {
		String[] sources = {"Someplatz"};
		String[] destinations = {"Ilse_Langner_Platz"};
		assertEquals("MaxFlow not correct!", -1, testMaxFlow("Iksburg4.txt", sources, destinations));
	}
	
	//new test
	@Test
	public final void testFindMaxFlow_Iksburg4_no_dest() {
		String[] sources = {"Luisenplatz"};
		String[] destinations = {"Someplatz"};
		assertEquals("MaxFlow not correct!", -2, testMaxFlow("Iksburg4.txt", sources, destinations));
	}
	
	//new test
	@Test
	public final void testFindMaxFlow_Iksburg4_no_source_no_dest() {
		String[] sources = {"XPlatz", "YPlatz"};
		String[] destinations = {"ZPlatz", "WPlatz"};
		assertEquals("MaxFlow not correct!", -3, testMaxFlow("Iksburg4.txt", sources, destinations));
	}
	
	//new test
	@Test
	public final void testFindMaxFlow_Iksburg4_no_path() {
		String[] sources = {"Marktplatz"};
		String[] destinations = {"Luisenplatz"};
		assertEquals("MaxFlow not correct!", -4, testMaxFlow("Iksburg4.txt", sources, destinations));
	}
	
	//new test
	@Test
	public final void testFindMaxFlow_Iksburg4_source_identical_dest() {
		String[] sources = {"Luisenplatz", "Kantplatz"};
		String[] destinations = {"Luisenplatz", "Kantplatz"};
		assertEquals("MaxFlow not correct!", Integer.MAX_VALUE, testMaxFlow("Iksburg4.txt", sources, destinations));
	}
	
	
	@Test
	public final void testSourcesAndSinks_Iksburg1_A_to_F() {
		String[] sources = {"A"};
		String[] destinations = {"F"};
		ArrayList<String> flowGraph = new MaxFlow("Iksburg1.txt").findResidualNetwork(sources, destinations);
		
		assertTrue("Sources and/or sinks are not marked correctly!", 
				areSourcesSinksMarkedRight(flowGraph, sources, destinations));
	}
	
	@Test
	public final void testSourcesAndSinks_Iksburg2_A_to_H() {
		String[] sources = {"A"};
		String[] destinations = {"H"};
		ArrayList<String> flowGraph = new MaxFlow("Iksburg2.txt").findResidualNetwork(sources, destinations);
		
		assertTrue("Sources and/or sinks are not marked correctly!", 
				areSourcesSinksMarkedRight(flowGraph, sources, destinations));
	}
	
	@Test
	public final void testSourcesAndSinks_Iksburg3_AB_to_FG() {
		String[] sources = {"A", "B"};
		String[] destinations = {"F", "G"};
		ArrayList<String> flowGraph = new MaxFlow("Iksburg3.txt").findResidualNetwork(sources, destinations);
		
		assertTrue("Sources and/or sinks are not marked correctly!", 
				areSourcesSinksMarkedRight(flowGraph, sources, destinations));
	}	
	
	//new test
	@Test
	public final void testSourcesAndSinks_Iksburg4_Luisenplatz_Kantplatz_to_ILPlatz() {
		String[] sources = {"Luisenplatz", "Kantplatz"};
		String[] destinations = {"Ilse_Langner_Platz"};
		ArrayList<String> flowGraph = new MaxFlow("Iksburg4.txt").findResidualNetwork(sources, destinations);
		
		assertTrue("Sources and/or sinks are not marked correctly!", 
				areSourcesSinksMarkedRight(flowGraph, sources, destinations));
	}
	
	@Test
	public final void testEdges_Iksburg1_A_F() {
		String[] sources = {"A"};
		String[] destinations = {"F"};
		ArrayList<String> flowGraph = new MaxFlow("Iksburg1.txt").findResidualNetwork(sources, destinations);
		
		assertTrue("Edge A->B was wrongly bolded!", isEdgeMarkedRight(flowGraph, "A[ ]*->[ ]*B"));
		assertTrue("Edge A->C was wrongly bolded!", isEdgeMarkedRight(flowGraph, "A[ ]*->[ ]*C"));
		assertTrue("Edge B->D was wrongly bolded!", isEdgeMarkedRight(flowGraph, "B[ ]*->[ ]*D"));
		assertTrue("Edge B->E was wrongly bolded!", isEdgeMarkedRight(flowGraph, "B[ ]*->[ ]*E"));
		assertTrue("Edge C->D was wrongly bolded!", isEdgeMarkedRight(flowGraph, "C[ ]*->[ ]*D"));
		assertTrue("Edge C->E was wrongly bolded!", isEdgeMarkedRight(flowGraph, "C[ ]*->[ ]*E"));
		assertTrue("Edge D->F was wrongly bolded!", isEdgeMarkedRight(flowGraph, "D[ ]*->[ ]*F"));
		assertTrue("Edge E->F was wrongly bolded!", isEdgeMarkedRight(flowGraph, "E[ ]*->[ ]*F"));
	}
	
	@Test
	public final void testEdges_Iksburg2_A_H() {
		String[] sources = {"A"};
		String[] destinations = {"H"};
		ArrayList<String> flowGraph = new MaxFlow("Iksburg2.txt").findResidualNetwork(sources, destinations);
		
		assertTrue("Edge A->B was wrongly bolded!", isEdgeMarkedRight(flowGraph, "A[ ]*->[ ]*B"));
		assertTrue("Edge A->C was wrongly bolded!", isEdgeMarkedRight(flowGraph, "A[ ]*->[ ]*C"));
		assertTrue("Edge A->D was wrongly bolded!", isEdgeMarkedRight(flowGraph, "A[ ]*->[ ]*D"));
		assertTrue("Edge B->E was wrongly bolded!", isEdgeMarkedRight(flowGraph, "B[ ]*->[ ]*E"));
		assertTrue("Edge B->F was wrongly bolded!", isEdgeMarkedRight(flowGraph, "B[ ]*->[ ]*F"));
		assertTrue("Edge C->E was wrongly bolded!", isEdgeMarkedRight(flowGraph, "C[ ]*->[ ]*E"));
		assertTrue("Edge C->F was wrongly bolded!", isEdgeMarkedRight(flowGraph, "C[ ]*->[ ]*F"));
		assertTrue("Edge C->G was wrongly bolded!", isEdgeMarkedRight(flowGraph, "C[ ]*->[ ]*G"));
		assertTrue("Edge D->F was wrongly bolded!", isEdgeMarkedRight(flowGraph, "D[ ]*->[ ]*F"));
		assertTrue("Edge D->G was wrongly bolded!", isEdgeMarkedRight(flowGraph, "D[ ]*->[ ]*G"));
		assertTrue("Edge E->H was wrongly bolded!", isEdgeMarkedRight(flowGraph, "E[ ]*->[ ]*H"));
		assertTrue("Edge F->H was wrongly bolded!", isEdgeMarkedRight(flowGraph, "F[ ]*->[ ]*H"));
		assertTrue("Edge G->H was wrongly bolded!", isEdgeMarkedRight(flowGraph, "G[ ]*->[ ]*H"));
	}
	
	@Test
	public final void testEdges_Iksburg3_AB_FG() {
		String[] sources = {"A", "B"};
		String[] destinations = {"F", "G"};
		ArrayList<String> flowGraph = new MaxFlow("Iksburg3.txt").findResidualNetwork(sources, destinations);
		
		assertTrue("Edge A->C was wrongly bolded!", isEdgeMarkedRight(flowGraph, "A[ ]*->[ ]*C"));
		assertTrue("Edge A->D was wrongly bolded!", isEdgeMarkedRight(flowGraph, "A[ ]*->[ ]*D"));
		assertTrue("Edge B->D was wrongly bolded!", isEdgeMarkedRight(flowGraph, "B[ ]*->[ ]*D"));
		assertTrue("Edge B->E was wrongly bolded!", isEdgeMarkedRight(flowGraph, "B[ ]*->[ ]*E"));
		assertTrue("Edge C->F was wrongly bolded!", isEdgeMarkedRight(flowGraph, "C[ ]*->[ ]*F"));
		assertTrue("Edge D->C was wrongly bolded!", isEdgeMarkedRight(flowGraph, "D[ ]*->[ ]*C"));
		assertTrue("Edge D->F was wrongly bolded!", isEdgeMarkedRight(flowGraph, "D[ ]*->[ ]*F"));
		assertTrue("Edge D->G was wrongly bolded!", isEdgeMarkedRight(flowGraph, "D[ ]*->[ ]*G"));
		assertTrue("Edge D->E was wrongly bolded!", isEdgeMarkedRight(flowGraph, "D[ ]*->[ ]*E"));
		assertTrue("Edge E->G was wrongly bolded!", isEdgeMarkedRight(flowGraph, "E[ ]*->[ ]*G"));
	}
	
	//new test
	@Test
	public final void testEdges_Iksburg4_Luisenplatz_Kantplatz_to_ILPlatz() {
		String[] sources = {"Luisenplatz", "Kantplatz"};
		String[] destinations = {"Ilse_Langner_Platz"};
		ArrayList<String> flowGraph = new MaxFlow("Iksburg4.txt").findResidualNetwork(sources, destinations);
		
		assertTrue("Edge Luisenplatz -> Taunus_Platz was wrongly bolded!", 
				isEdgeMarkedRight(flowGraph, "Luisenplatz[ ]*->[ ]*Taunus_Platz"));
		assertTrue("Edge Luisenplatz  -> Schlossgartenplatz was wrongly bolded!",
				isEdgeMarkedRight(flowGraph, "Luisenplatz[ ]*->[ ]*Schlossgartenplatz"));
		assertTrue("Edge Kantplatz -> Schlossgartenplatz was wrongly bolded!",
				isEdgeMarkedRight(flowGraph, "Kantplatz[ ]*->[ ]*Schlossgartenplatz"));
		assertTrue("Edge Kantplatz  -> Ernst_Ludwigsplatz was wrongly bolded!", 
				isEdgeMarkedRight(flowGraph, "Kantplatz[ ]*->[ ]*Ernst_Ludwigsplatz"));
		assertTrue("Edge Kantplatz  -> Marktplatz->F was wrongly bolded!", 
				isEdgeMarkedRight(flowGraph, "Kantplatz[ ]*->[ ]*Marktplatz"));
		assertTrue("Edge Taunus_Platz -> Friedensplatz was wrongly bolded!", 
				isEdgeMarkedRight(flowGraph, "Taunus_Platz[ ]*->[ ]*Friedensplatz"));
		assertTrue("Edge Taunus_Platz -> Kopernikusplatz was wrongly bolded!", 
				isEdgeMarkedRight(flowGraph, "Taunus_Platz[ ]*->[ ]*Kopernikusplatz"));
		assertTrue("Edge Schlossgartenplatz -> Taunus_Platz was wrongly bolded!", 
				isEdgeMarkedRight(flowGraph, "Schlossgartenplatz[ ]*->[ ]*Taunus_Platz"));
		assertTrue("Edge Schlossgartenplatz -> Kennedyplatz was wrongly bolded!", 
				isEdgeMarkedRight(flowGraph, "Schlossgartenplatz[ ]*->[ ]*Kennedyplatz"));
		assertTrue("Edge Ernst_Ludwigsplatz -> Marktplatz was wrongly bolded!", 
				isEdgeMarkedRight(flowGraph, "Ernst_Ludwigsplatz[ ]*->[ ]*Marktplatz"));
		assertTrue("Edge Ernst_Ludwigsplatz -> Kennedyplatz was wrongly bolded!", 
				isEdgeMarkedRight(flowGraph, "Ernst_Ludwigsplatz[ ]*->[ ]*Kennedyplatz"));
		assertTrue("Edge Friedensplatz -> Marktplatz was wrongly bolded!", 
				isEdgeMarkedRight(flowGraph, "Friedensplatz[ ]*->[ ]*Marktplatz"));
		assertTrue("Edge Friedensplatz -> Platz_der_deutschen_Einheit was wrongly bolded!", 
				isEdgeMarkedRight(flowGraph, "Friedensplatz[ ]*->[ ]*Platz_der_deutschen_Einheit"));
		assertTrue("Edge Marktplatz -> Ilse_Langner_Platz was wrongly bolded!", 
				isEdgeMarkedRight(flowGraph, "Marktplatz[ ]*->[ ]*Ilse_Langner_Platz"));
		assertTrue("Edge Kennedyplatz -> Ilse_Langner_Platz was wrongly bolded!", 
				isEdgeMarkedRight(flowGraph, "Kennedyplatz[ ]*->[ ]*Ilse_Langner_Platz"));
		assertTrue("Edge Platz_der_deutschen_Einheit -> Marktplatz was wrongly bolded!", 
				isEdgeMarkedRight(flowGraph, "Platz_der_deutschen_Einheit[ ]*->[ ]*Marktplatz"));
		assertTrue("Edge Kopernikusplatz -> Ilse_Langner_Platz was wrongly bolded!", 
				isEdgeMarkedRight(flowGraph, "Kopernikusplatz[ ]*->[ ]*Ilse_Langner_Platz"));
	}
	
	@Test
	public final void testCuts_Iksburg1_A_F() {
		String[] sources = {"A"};
		String[] destinations = {"F"};
		String[] cut1 = new String[] {"A[ ]*->[ ]*B", "A[ ]*->[ ]*C"};
		String[] cut2 = new String[] {"D[ ]*->[ ]*F", "E[ ]*->[ ]*F"};
		ArrayList<String> flowGraph = new MaxFlow("Iksburg1.txt").findResidualNetwork(sources, destinations);

		assertEquals("The cut1 is different from max flow!", 140, cutFlow(flowGraph, cut1));
		
		assertEquals("The cut2 is different from max flow!", 140, cutFlow(flowGraph, cut2));
	}
	
	@Test
	public final void testCuts_Iksburg2_A_H() {
		String[] sources = {"A"};
		String[] destinations = {"H"};
		String[] cut1 = new String[] {"A[ ]*->[ ]*B", "A[ ]*->[ ]*D", "A[ ]*->[ ]*C"};
		String[] cut2 = new String[] {"B[ ]*->[ ]*E", "B[ ]*->[ ]*F", "C[ ]*->[ ]*E", "C[ ]*->[ ]*F",
				"C[ ]*->[ ]*G", "D[ ]*->[ ]*F", "D[ ]*->[ ]*G"};
		String[] cut3 = new String[] {"B[ ]*->[ ]*E", "C[ ]*->[ ]*E", "F[ ]*->[ ]*H", "G[ ]*->[ ]*H"};
		ArrayList<String> flowGraph = new MaxFlow("Iksburg2.txt").findResidualNetwork(sources, destinations);
		
		assertEquals("The cut1 is different from max flow!", 240, cutFlow(flowGraph, cut1));
		
		assertEquals("The cut2 is different from max flow!", 240, cutFlow(flowGraph, cut2));
		
		assertEquals("The cut3 is different from max flow!", 240, cutFlow(flowGraph, cut3));
	}
	
	@Test
	public final void testCuts_Iksburg3_AB_FG() {
		String[] sources = {"A", "B"};
		String[] destinations = {"F", "G"};
		String[] cut1 = new String[] {"A[ ]*->[ ]*C", "A[ ]*->[ ]*D", "B[ ]*->[ ]*D", "B[ ]*->[ ]*E"};
		String[] cut2 = new String[] {"A[ ]*->[ ]*C", "D[ ]*->[ ]*C", "D[ ]*->[ ]*F", "D[ ]*->[ ]*G", "E[ ]*->[ ]*G"};
		String[] cut3 = new String[] {"C[ ]*->[ ]*F", "D[ ]*->[ ]*F", "D[ ]*->[ ]*G", "E[ ]*->[ ]*G"};
		ArrayList<String> flowGraph = new MaxFlow("Iksburg3.txt").findResidualNetwork(sources, destinations);
		
		assertEquals("The cut1 is different from max flow!", 30, cutFlow(flowGraph, cut1));
		
		assertEquals("The cut2 is different from max flow!", 30, cutFlow(flowGraph, cut2));
		
		assertEquals("The cut3 is different from max flow!", 30, cutFlow(flowGraph, cut3));
	}
	
	//new test
	@Test
	public final void testCuts_Iksburg4_Luisenplatz_Kantplatz_to_ILPlatz() {
		String[] sources = {"Luisenplatz", "Kantplatz"};
		String[] destinations = {"Ilse_Langner_Platz"};
		String[] cut1 = new String[] {"Kopernikusplatz[ ]*->[ ]*Ilse_Langner_Platz", 
				"Marktplatz[ ]*->[ ]*Ilse_Langner_Platz", 
				"Kennedyplatz[ ]*->[ ]*Ilse_Langner_Platz"};
		String[] cut2 = new String[] {"Taunus_Platz[ ]*->[ ]*Kopernikusplatz",
				"Taunus_Platz[ ]*->[ ]*Friedensplatz", 
				"Kantplatz[ ]*->[ ]*Marktplatz",
				"Kennedyplatz[ ]*->[ ]*Ilse_Langner_Platz", 
				"Ernst_Ludwigsplatz[ ]*->[ ]*Marktplatz"};
		String[] cut3 = new String[] {"Luisenplatz[ ]*->[ ]*Taunus_Platz", 
				"Luisenplatz[ ]*->[ ]*Schlossgartenplatz", 
				"Kantplatz[ ]*->[ ]*Schlossgartenplatz",
				"Kantplatz[ ]*->[ ]*Marktplatz", 
				"Kantplatz[ ]*->[ ]*Ernst_Ludwigsplatz"};
		ArrayList<String> flowGraph = new MaxFlow("Iksburg4.txt").findResidualNetwork(sources, destinations);
		
		assertEquals("The cut1 is different from max flow!", 42, cutFlow(flowGraph, cut1));
		assertEquals("The cut2 is different from max flow!", 42, cutFlow(flowGraph, cut2));
		assertEquals("The cut3 is different from max flow!", 42, cutFlow(flowGraph, cut3));
	}
	
	private int cutFlow(final ArrayList<String> graph, final String[] cut) {
		int usedEdgeCapacity = 0;
		int cutFlow = 0;
		
		for (String edge : cut) {
			for (String graphElement : graph) {
				if(graphElement.matches(".*"+edge+".*")) {
					Scanner s=new Scanner(graphElement);
					String diff = s.findInLine("\\\".*?\\\"");
					s.close();
					String[] edgeLabel = diff.split("\"|-");
					usedEdgeCapacity = new Integer(edgeLabel[2].trim()).intValue();
				}
			}
			cutFlow += usedEdgeCapacity;
		}
		return cutFlow;
	}
	
	private boolean isEdgeMarkedRight(final ArrayList<String> graph, final String edge) {
		int maxEdgeCapacity = 0;
		int usedEdgeCapacity = 0;
		boolean isBold = false;
						
		//calculate edge
		for (String graphElement : graph) {
			if(graphElement.matches(".*"+edge+".*")) {
				Scanner s=new Scanner(graphElement);
				String diff = s.findInLine("\\\".*?\\\"");
				s.close();
				String[] edgeLabel = diff.split("\"|-");
				maxEdgeCapacity = new Integer(edgeLabel[1].trim()).intValue();
				usedEdgeCapacity = new Integer(edgeLabel[2].trim()).intValue();
			}
		}
		
		//verify if edge is bold or not 
		for (String graphElement : graph) {
			if (graphElement.matches(".*"+edge+".*")) {
				if (graphElement.matches(".*style[ ]*=[ ]*bold.*")){
					isBold = true;
				}
				else isBold = false;
			}
			
		}
		
		if (((maxEdgeCapacity != usedEdgeCapacity) && (isBold)) || ((maxEdgeCapacity == usedEdgeCapacity) && (!isBold)))
			return true;
		else 
			return false;
	}
	
	private boolean areSourcesSinksMarkedRight(final ArrayList<String> graph,
			final String[] sources, final String[] destinations) {
		boolean correct = true;
		int sourceMatches = 0;
		int destinationMatches = 0;
		
		for (String source : sources) {
			correct = false;
			for (String graphElement : graph) {
				if(graphElement.matches(".*=[ ]*doublecircle.*")) {
					if(graphElement.matches(".*"+source+".*")) {
						sourceMatches++;
						correct = true;
					}
				}
			}
			if(!correct) return false;
		}
		for (String destination : destinations) {
			correct = false;
			for (String graphElement : graph) {
				if(graphElement.matches(".*=[ ]*circle.*")) {
					if(graphElement.matches(".*"+destination+".*")) {
						destinationMatches++;
						correct = true;
					}
				}
			}
			if(!correct) return false;
		}		
		
		return (sourceMatches == sources.length)	&& (destinationMatches == destinations.length);
	}
	
	private int testMaxFlow(final String filename, final String[] sources, final String[] destinations) {
		MaxFlow mFlow = new MaxFlow(filename);
		return mFlow.findMaxFlow(sources, destinations);
	}
	
}
