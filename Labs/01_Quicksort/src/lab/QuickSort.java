package lab;

import frame.SortArray;

/**
 * Abstract superclass for the Quicksort algorithm.
 * 
 * @author NAJI
 */
public abstract class QuickSort {

	protected int from, to;
	
	// DO NOT modify this method
	public abstract void Quicksort(SortArray records, int left, int right);

	// You may add additional methods here
	
	protected void qshelper(SortArray records, int left, int right, SortingItem pivot) {
		from = left; to = right;
		while (from <= to) {
			while (from < right && itemComp(records.getElementAt(from), pivot) < 0) from++;
			while (to > left && itemComp(records.getElementAt(to), pivot) > 0) to--;
			if (from <= to) { swap(records, from, to); from++; to--; }
		}
	}
	
	protected void swap(SortArray r, int a, int b) {
		SortingItem A = r.getElementAt(a), B = r.getElementAt(b);
		//System.out.printf("SWAP: %d=%s  <->   %d=%s\n", a, A.getSortKey(), b, B.getSortKey());
		r.setElementAt(b, A); r.setElementAt(a, B);
	}
	
	protected int itemComp(SortingItem i1, SortingItem i2) {
		//System.out.printf("COMP: %s %s \t %d\n", i1.getSortKey(), i2.getSortKey(), i1.getSortKey().compareTo(i2.getSortKey()));
		return i1.getSortKey().compareTo(i2.getSortKey());
	}
	
}



