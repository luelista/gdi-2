package frame;

import java.io.File;
import java.io.FilenameFilter;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;
import lab.SortingItem;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Do NOT change anything in this class!
 * 
 * The test cases defined by this class are used to test if the input file was
 * correctly sorted. This class is also responsible for outputting to the
 * console.
 * 
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AllTests extends TestCase {

	int NrOfTestFiles, correct;
	String[] inputFiles;
	boolean complexity_exceeded_randomA;
	boolean complexity_exceeded_asc_A;
	boolean complexity_exceeded_dsc_A;
	boolean complexity_exceeded_randomB;
	boolean complexity_exceeded_asc_B;
	boolean complexityTest;

	public static void main(String... args) {
		junit.textui.TestRunner.run(suite());
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(AllTests.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() {
		NrOfTestFiles = 0;
		// File dir = new File("TestFiles");
		File dir = new File(System.getProperty("user.dir"));
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith("TestFile");
			}
		};
		inputFiles = dir.list(filter);
		if (inputFiles == null) {
			System.out.println("Error: No TestFiles found!");
		} else {
			NrOfTestFiles = inputFiles.length;
		}
	}

	@After
	public void tearDown() {
		if (correct != NrOfTestFiles) {
			if (!complexityTest)
				System.out.println("Wrong sorting for file: "
						+ inputFiles[correct]
						+ " aborting testing for this algorithm!\n");
			else {

				if (!complexity_exceeded_randomA && !complexity_exceeded_asc_A
						&& !complexity_exceeded_dsc_A
						&& !complexity_exceeded_randomB
						&& !complexity_exceeded_asc_B)
					System.out
							.println("Aborted, test failed for file: "
									+ inputFiles[correct]
									+ " - complexity error: no operations have been counted!\n");

				if (complexity_exceeded_randomA) {
					System.out
							.println("Aborted, test failed for file: "
									+ inputFiles[correct]
									+ " - complexity out of allowed range: O(nlog(n)) required!\n");
				}
				if (complexity_exceeded_asc_A) {
					System.out
							.println("Aborted, test failed for file: "
									+ inputFiles[correct]
									+ " - complexity out of allowed range: O(n^2) required!\n");
				}
				if (complexity_exceeded_dsc_A) {
					System.out
							.println("Aborted, test failed for file: "
									+ inputFiles[correct]
									+ " - complexity out of allowed range: O(n^2) required!\n");
				}
				if (complexity_exceeded_randomB) {
					System.out
							.println("Aborted, test failed for file: "
									+ inputFiles[correct]
									+ " - complexity out of allowed range: O(nlog(n)) required!\n");
				}
				if (complexity_exceeded_asc_B) {
					System.out
							.println("Aborted, test failed for file: "
									+ inputFiles[correct]
									+ " - complexity out of allowed range: O(nlog(n)) required!\n");
				}
			}
		}
	}

	protected boolean sortingTester(SortArray records) {
		boolean sorted = true;
		SortingItem lastRecord = records.getElementAt(0);
		for (int i = 1; i < records.getNumberOfItems() && sorted; i++) {
			SortingItem actualRecord = records.getElementAt(i);
			sorted = (actualRecord.BookSerialNumber
					.compareTo(lastRecord.BookSerialNumber) > 0)
					|| ((actualRecord.BookSerialNumber
							.compareTo(lastRecord.BookSerialNumber) == 0) && ((actualRecord.ReaderID
							.compareTo(lastRecord.ReaderID) > 0) || ((actualRecord.ReaderID
							.compareTo(lastRecord.ReaderID) == 0))));
			lastRecord = actualRecord;

		}
		return sorted;
	}

	protected void complexityTesterA(SortArray records, String inputFile,
			int readOps) {

		complexity_exceeded_randomA = false;
		complexity_exceeded_asc_A = false;
		complexity_exceeded_dsc_A = false;
		int n = records.getNumberOfItems();
		double nlogn = n * (Math.log(n) / Math.log(2)) * 5;

		org.junit.Assert.assertTrue(readOps > 0);

		if (inputFile.contains("_r_") && readOps > nlogn) {

			complexity_exceeded_randomA = true;
			org.junit.Assert.assertTrue(readOps < nlogn);
		} else if (inputFile.contains("_a_") && readOps < Math.pow(n, 2) / 2) {
			complexity_exceeded_asc_A = true;
			org.junit.Assert.assertTrue(readOps > Math.pow(n, 2) / 2);

		} else if (inputFile.contains("_d_") && readOps < Math.pow(n, 2) / 2) {
			complexity_exceeded_dsc_A = true;
			org.junit.Assert.assertTrue(readOps > Math.pow(n, 2) / 2);

		}

	}

	protected void complexityTesterB(SortArray records, String inputFile,
			int readOps) {

		complexity_exceeded_randomB = false;
		complexity_exceeded_asc_B = false;
		int n = records.getNumberOfItems();
		double nlogn = n * (Math.log(n) / Math.log(2)) * 5;

		org.junit.Assert.assertTrue(readOps > 0);

		if (inputFile.contains("_r_") && readOps > nlogn) {
			complexity_exceeded_randomB = true;
			org.junit.Assert.assertTrue(readOps < nlogn);
		}
		if (inputFile.contains("_a_") && readOps > nlogn) {

			complexity_exceeded_asc_B = true;
			org.junit.Assert.assertTrue(readOps < nlogn);
		}

	}

	@Test
	public void testQuicksortA() {
		SortArray records = null;
		int readOps, writeOps;
		correct = 0;
		complexityTest = false;

		System.out.println("Starting QuicksortA tests!");

		for (int file = 0; file < NrOfTestFiles; file++) {

			// modified
			records = SortingLab.readFile(inputFiles[file]);
			SortingLab.QuicksortA(records, 0, records.getNumberOfItems() - 1);

			readOps = records.getReadingOperations();
			writeOps = records.getWritingOperations();

			org.junit.Assert.assertTrue(sortingTester(records));

			System.out.println("QuicksortA [" + inputFiles[file]
					+ "]: Correct order! Read Ops: " + readOps
					+ "; Write Ops: " + writeOps);
			correct++;
		}
		System.out.println("Correct QuicksortA sortings: " + correct
				+ " out of " + NrOfTestFiles + " tests\n");
	}

	@Test
	public void testQuicksortAComplexity() {
		SortArray records = null;
		int readOps;
		correct = 0;
		int notested = 0;
		complexityTest = true;
		int passed = 0;

		System.out.println("Starting QuicksortA complexity tests!");

		for (int file = 0; file < NrOfTestFiles; file++) {

			if (inputFiles[file].contains("_r_")
					|| inputFiles[file].contains("_a_")
					|| inputFiles[file].contains("_d_")) {
				notested++;
				records = SortingLab.readFile(inputFiles[file]);
				SortingLab.QuicksortA(records, 0,
						records.getNumberOfItems() - 1);

				readOps = records.getReadingOperations();

				complexityTesterA(records, inputFiles[file], readOps);

				System.out.println("Complexity QuicksortA [" + inputFiles[file]
						+ "]: Complexity within allowed range!");
				passed++;
			}
			correct++;
		}
		System.out.println("Passed complexity tests for QuicksortA: " + passed
				+ " out of " + notested + " tests\n");
	}

	@Test
	public void testQuicksortBComplexity() {
		SortArray records = null;
		int readOps;
		correct = 0;
		int notested = 0;
		int passed = 0;
		complexityTest = true;

		System.out.println("Starting QuicksortB complexity tests!");

		for (int file = 0; file < NrOfTestFiles; file++) {
			if (inputFiles[file].contains("_r_")
					|| inputFiles[file].contains("_a_")) {
				notested++;
				records = SortingLab.readFile(inputFiles[file]);
				SortingLab.QuicksortB(records, 0,
						records.getNumberOfItems() - 1);

				readOps = records.getReadingOperations();
				complexityTesterB(records, inputFiles[file], readOps);

				System.out.println("Complexity QuicksortB [" + inputFiles[file]
						+ "]: Complexity within allowed range!");
				passed++;
			}
			correct++;
		}
		System.out.println("Passed complexity tests for QuicksortB: " + passed
				+ " out of " + notested + " tests\n");

	}

	@Test
	public void testQuicksortB() {
		SortArray records = null;
		int readOps, writeOps;
		correct = 0;
		complexityTest = false;

		System.out.println("Starting QuicksortB tests!");

		for (int file = 0; file < NrOfTestFiles; file++) {
			records = SortingLab.readFile(inputFiles[file]);
			SortingLab.QuicksortB(records, 0, records.getNumberOfItems() - 1);

			readOps = records.getReadingOperations();
			writeOps = records.getWritingOperations();

			org.junit.Assert.assertTrue(sortingTester(records));

			System.out.println("QuicksortB [" + inputFiles[file]
					+ "]: Correct order! Read Ops: " + readOps
					+ "; Write Ops: " + writeOps);
			correct++;

		}
		System.out.println("Correct QuicksortB sortings: " + correct
				+ " out of " + NrOfTestFiles + " tests\n");
	}

}
