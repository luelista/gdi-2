package lab;

/**
 * 
 * This class represents one entry of the list that has to be sorted.
 * 
 */
public class SortingItem implements Comparable<SortingItem> {

	// DO NOT modify
	public String BookSerialNumber;
	public String ReaderID;
	public String Status;

	// DO NOT modify
	public SortingItem() {

	}

	// DO NOT modify
	public SortingItem(SortingItem otherItem) {
		this.BookSerialNumber = otherItem.BookSerialNumber;
		this.ReaderID = otherItem.ReaderID;
		this.Status = otherItem.Status;
	}

	// You may add additional methods here

    /**
     * concatenates relevant values to form a sorting key
     * @return the sort key
     */
	public String getSortKey() {
		return this.BookSerialNumber + ";" + this.ReaderID;
	}

    /**
     * compares this instance to another instance of SortingItem by their `sortKey`s
     * @param b the other instance
     * @return value < 0 if this instance is lower,
     *         value = 0 if instances are equivalent with respect to `sortKey`s,
     *         value > 0 if this instance is higher
     */
	public int compareTo(SortingItem b) {
		return this.getSortKey().compareTo(b.getSortKey());
	}
	
}
