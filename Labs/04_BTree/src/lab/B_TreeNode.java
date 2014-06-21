package lab;

/*
 * Implements a node of a B-Tree
 *
 * Make sure that you have tested all the given test cases
 * given on the homepage before you submit your solution.
 *
 */


import frame.Entry;

import java.util.ArrayList;

public class B_TreeNode {

    public ArrayList<Entry> entries = new ArrayList<>();
    public ArrayList<B_TreeNode> children = new ArrayList<>();

    int maxDeg;
    public boolean leaf;

    public String id;
    private static int idCounter = 1000;
    /**
	* The constructor
	* 
	* @param t minimum degree of the B-tree
      *
	*/

    public B_TreeNode(int t) {
        maxDeg = t;
        this.setId(++idCounter);
    }
    public void setId(int idx) {
        this.id = String.format("node%d", idx);
    }
    public int getMaxDepth() {
        int dep = 0;
        for(B_TreeNode child : children)
            dep = Math.max(dep, child.getMaxDepth());
        return dep + 1;
    }
    public int getEntryCount() {
        int n = 0;
        n += entries.size();
        for(B_TreeNode child : children)
            n += child.getEntryCount();
        return n;
    }

    public B_TreeNode child(int index) {
        return this.children.get(index);
    }
    public Entry entry(int index) {
        return this.entries.get(index);
    }

    //FIXME !!! should contain lots of off-by-ones
    public void splitChild(int index, B_TreeNode y) {
        if(Max.DEBUG)System.out.println("...splitChild "+index+", "+y);
        B_TreeNode z = new B_TreeNode(maxDeg);
        z.leaf = y.leaf;
        for(int i = maxDeg - 1; i > 0; i--) {
            z.entries.add(0, y.entry(i + maxDeg - 1));
            y.entries.remove(i + maxDeg - 1);
        }
        if (!y.leaf) {
            for(int i = maxDeg; i > 0; i--) {
                z.children.add(0, y.child(i + maxDeg - 1));
                y.children.remove(i + maxDeg - 1);
            }
        }
        this.children.add(index+1, z);
        this.entries.add(index, y.entry(maxDeg - 1));
        y.entries.remove(maxDeg - 1);

    }

    public Entry treeFindSmallest() {
        if (leaf) return this.entry(0);
        else return this.child(0).treeFindSmallest();
    }
    public Entry treeFindBiggest() {
        if (leaf) return this.entry(this.entries.size()-1);
        else return this.child(this.children.size()-1).treeFindBiggest();
    }
    public Entry treeSearch(String key) {
        int i = 0;
        for(; i < this.entries.size(); i++) {
            int comp = this.entry(i).getKey().compareTo(key);
            if (!leaf && comp > 0) {
                return this.child(i).treeSearch(key);
            } else if (comp == 0) {
                return this.entry(i);
            }
        }
        if (!leaf)
            return this.child(i).treeSearch(key);
        else
            return null;
    }
    public void treeTraverse(ArrayList<Entry> list) {
        int i = 0;
        for(; i < this.entries.size(); i++) {
            if (!leaf) {
                this.child(i).treeTraverse(list);
            }
            list.add(this.entry(i));
            System.out.println("traverse:"+this.entry(i));
        }
        if (!leaf)
            this.child(i).treeTraverse(list);
    }
    public Entry treeDeleteByKey(String key) {
        int i = 0;
        Entry deletedItem = null;
        for(; i < this.entries.size(); i++) {
            Entry e = this.entry(i);
            int comp = e.getKey().compareTo(key);
            if (!leaf && comp > 0) {
                if (this.child(i).minimal()) {
                    i+=this.balanceChild(i);
                }
                deletedItem = this.child(i).treeDeleteByKey(key);
                if (deletedItem != null) break;
            } else if (comp == 0) {
                deletedItem = e;

                if (leaf) {
                    this.entries.remove(i);
                } else {
                    if (!this.child(i+1).minimal()) {
                        //replace with successor
                        Entry replacement = this.child(i+1).treeFindSmallest();
                        this.child(i+1).treeDeleteByKey(replacement.getKey());

                        this.entries.remove(i);
                        this.entries.add(i, replacement);
                    } else if (!this.child(i).minimal()) {
                        //replace with predecessor
                        Entry replacement = this.child(i).treeFindBiggest();
                        this.child(i).treeDeleteByKey(replacement.getKey());

                        this.entries.remove(i);
                        this.entries.add(i, replacement);
                    } else {
                        //merge nodes
                        mergeChildren(i, i+1);
                        this.treeDeleteByKey(key);
                    }
                }

                break;
            }
        }
        if (!leaf && deletedItem == null && this.children.size()-1 >= i)
            deletedItem = this.child(i).treeDeleteByKey(key);

        return deletedItem;
    }
    public int balanceChild(int index) {
        if (index > 0 && !this.child(index - 1).minimal()) {
            //rotate with the left neighbor
            B_TreeNode left = this.child(index - 1);
            B_TreeNode right = this.child(index);

            if (!left.leaf) {
                right.children.add(0, left.child(left.children.size() - 1));
                left.children.remove(left.children.size() - 1);
            }

            right.entries.add(0, entry(index));
            entries.set(index, left.entry(left.entries.size()-1));
            left.entries.remove(left.entries.size()-1);

        } else if (index < this.children.size() - 1 && !this.child(index + 1).minimal()) {
            //rotate with the right neighbor
            B_TreeNode left = this.child(index);
            B_TreeNode right = this.child(index + 1);

            if (!left.leaf) {
                left.children.add(right.child(0));
                right.children.remove(0);
            }

            left.entries.add(this.entry(index));
            this.entries.set(index, right.entry(0));
            right.entries.remove(0);

        } else if (index > 0) {
            //merge with the left neighbor
            mergeChildren(index - 1, index);
            return -1;
        } else {
            // merge with the right neighbor
            mergeChildren(index, index + 1);
        }
        return 0;
    }
    private void mergeChildren(int left, int right) {
        B_TreeNode lnode = this.child(left);
        B_TreeNode rnode = this.child(right);
        Entry pivot = this.entry(left);
        lnode.entries.add(pivot);
        for(B_TreeNode child : rnode.children) lnode.children.add(child);
        for(Entry item : rnode.entries) lnode.entries.add(item);
        this.entries.remove(left);
        this.children.remove(right);

    }


    public boolean minimal() {
        return (this.entries.size() <= maxDeg-1);
    }

    /**
     * convert to Dotfile representation
     *
     */
    public String toDotRep(int label_maxlen) {
        int n = this.entries.size();
        String[] label = new String[n*2+1];
        for(int i = 0; i < n; i++)
            label[i*2+1] = String.format("<f%d>%s",
                    i*2+1, Max.truncstr(this.entry(i).getKey(), label_maxlen));
        if (leaf) {
            for (int i = 0; i < n + 1; i++)
                label[i * 2] = String.format("<f%d>*", i*2);
        } else {
            for (int i = 0; i < n + 1; i++)
                label[i * 2] = String.format("<f%d>*", i*2);
        }
        return String.format("%s[label=\"%s\"];", this.id, Max.join(label, "|"));
    }


    /**
    * Add your code here
    */

    @Override
    public String toString() {
        return "Node("+this.children.size()+"|"+this.entries.size()+")" + (leaf?"[leaf]":"");
    }
}