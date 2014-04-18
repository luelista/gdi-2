package lab;

import frame.SortArray;

public class QuickSortB extends QuickSort {

	/**
	 * Quicksort algorithm implementation to sort a SorrtArray by choosing the
	 * pivot as the median of the elements at positions (left,middle,right)
	 * 
	 * @param records
	 *            - list of elements to be sorted as a SortArray
	 * @param left
	 *            - the index of the left bound for the algorithm
	 * @param right
	 *            - the index of the right bound for the algorithm
	 * @return Returns the sorted list as SortArray
	 */
	@Override
	public void Quicksort(SortArray records, int left, int right) {
		SortingItem a = records.getElementAt(left), b = records.getElementAt((left+right)/2), c = records.getElementAt(right);
		qshelper(records, left, right, median(a,b,c));
		
		int _from = from, _to = to;
		if (left < _to) this.Quicksort(records, left, _to);
		if (_from < right) this.Quicksort(records, _from, right);
	}

	// You may add additional methods here

	public SortingItem median(SortingItem a, SortingItem b, SortingItem c) {
		if (a.compareTo(b) > 0)
			if (b.compareTo(c) > 0)
				return b;
			else if (a.compareTo(c) > 0)
				return c;
			else
				return a;
		else
			if (a.compareTo(c) > 0)
				return a;
			else if (b.compareTo(c) > 0)
				return c;
			else
				return b;
	}
	
}
