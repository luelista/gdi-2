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

    public int id;
    private static int idCounter = 1000;
    /**
	* The constructor
	* 
	* @param t minimum degree of the B-tree
      *
	*/

    public B_TreeNode(int t) {
        maxDeg = t;
        id = ++idCounter;
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
        System.out.println("...splitChild "+index+", "+y);
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


    /**
     * convert to Dotfile representation
     *
     */
    public String toDotRep() {
        int n = this.entries.size();
        String[] label = new String[n*2+1];
        for(int i = 0; i < n; i++)
            label[i*2+1] = String.format("<s%d>%s", i, this.entry(i).toString());
        if (leaf) {
            for (int i = 0; i < n + 1; i++)
                label[i * 2] = String.format("<dummy%d>*", i);
        } else {
            for (int i = 0; i < n + 1; i++)
                label[i * 2] = String.format("<ref%d>*", this.child(i).id);
        }
        return String.format("node%d[label=\"%s\"];", this.id, Max.join(label, "|"));
    }


    /**
    * Add your code here
    */

    @Override
    public String toString() {
        return "Node("+this.children.size()+"|"+this.entries.size()+")" + (leaf?"[leaf]":"");
    }
}