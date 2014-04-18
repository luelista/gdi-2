package lab;

import frame.SortArray;

public class QuickSortA extends QuickSort {

	/**
	 * Quicksort algorithm implementation to sort a SorrtArray by choosing the
	 * pivot as the first (leftmost) element in the list
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
		qshelper(records, left, right, records.getElementAt(left));
		int _from = from, _to = to; //Store result locally as member fields will change

        if (left < _to) this.Quicksort(records, left, _to);
		if (_from < right) this.Quicksort(records, _from, right);
	}

	// You may add additional methods here

}
