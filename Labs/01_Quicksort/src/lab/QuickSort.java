package lab;

import frame.SortArray;

/**
 * Abstract superclass for the Quicksort algorithm.
 * 
 * @author NAJI
 */
public abstract class QuickSort {

	// DO NOT modify this method
	public abstract void Quicksort(SortArray records, int left, int right);

	// You may add additional methods here

    // Return values of qshelper
    protected int from, to;

    /**
     * Helper method for the quicksort algorithm. This is where the actual work of determining which items to swap
     * and calculating the range (from, to) for the next iteration is done.
     * The result is stored in the member fields `from` and `to`
     * @param records  a `SortArray` instance
     * @param left     the lower bound of the current range
     * @param right    the upper bound of the current range
     * @param pivot    the pivot element
     */
	protected void qshelper(SortArray records, int left, int right, SortingItem pivot) {
		from = left; to = right;
		while (from <= to) {
            // find the first item between "from" and "right" which is higher than pivot
			for (; from < right; from++)
                if (itemComp(records.getElementAt(from), pivot) >= 0) break;

            // find the first item going from "to" downto "left" which is lower than pivot
			for (; to > left; to--)
                if (itemComp(records.getElementAt(to), pivot) <= 0) break;

            if (from <= to) { swap(records, from, to); from++; to--; }
		}
        // from and to are 'returned' as fields
	}

    /**
     * Swap two elements of a `SortArray` instance, given by their indices
     * @param r the `SortArray`
     * @param a element index 1
     * @param b element index 2
     */
	protected void swap(SortArray r, int a, int b) {
		SortingItem A = r.getElementAt(a), B = r.getElementAt(b);
		//System.out.printf("SWAP: %d=%s  <->   %d=%s\n", a, A.getSortKey(), b, B.getSortKey());
		r.setElementAt(b, A); r.setElementAt(a, B);
	}

    /**
     * Compare two `SortingItem`s
     * @param i1 the lower item
     * @param i2 the higher item
     * @return value < 0 if lower item is lower,
     *         value = 0 if items are sorted equal,
     *         value > 0 if lower item is higher
     */
	protected int itemComp(SortingItem i1, SortingItem i2) {
		//System.out.printf("COMP: %s %s \t %d\n", i1.getSortKey(), i2.getSortKey(), i1.getSortKey().compareTo(i2.getSortKey()));
		return i1.compareTo(i2);
	}
	
}



