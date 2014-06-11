package frame;
/**
 * B_TreeTestCase
 *
 * Version:	1.0
 *
 *
 */


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import lab.B_Tree;

/**
 * TestCases for the forth lab.
 *
 */
public class AllTests{
	@Rule
	public Timeout globalTimeout = new Timeout(10000);
	
	
    ArrayList<TestNode> nodes = new ArrayList<TestNode>();
    ArrayList<String> node_names = new ArrayList<String>();
    ArrayList<String> pointers = new ArrayList<String>();
    
    
    
    @Test public void testInsertFile1_NumberOfNodes() {
        B_Tree b = new B_Tree(2);
        b.constructB_TreeFromFile("TestFile.txt");
        ArrayList<String> out = b.getB_Tree();
        ArrayList<TestNode> test_tree = this.constractB_Tree(out);
        assertEquals("Number of nodes not correct!", 14, test_tree.size());
    }
    
    @Test public void testInsertFile1_HeightOfTree() {
        B_Tree b = new B_Tree(2);
        b.constructB_TreeFromFile("TestFile.txt");
        ArrayList<String> out = b.getB_Tree();
        ArrayList<TestNode> test_tree = this.constractB_Tree(out);
        assertEquals("Height of the tree not correct!", 2, b.getB_TreeHeight());
    }
    
    @Test public void testInsertFile1_NumberOfEntries() {
        B_Tree b = new B_Tree(2);
        b.constructB_TreeFromFile("TestFile.txt");
        ArrayList<String> out = b.getB_Tree();
        ArrayList<TestNode> test_tree = this.constractB_Tree(out);
        assertEquals("Number of the entries is not correct!", 22, b.getB_TreeSize());
    }
    
    @Test public void testInsertFile1_NodesOfTree() {
        B_Tree b = new B_Tree(2);
        b.constructB_TreeFromFile("TestFile.txt");
        ArrayList<String> out = b.getB_Tree();
        ArrayList<TestNode> test_tree = this.constractB_Tree(out);
        testNode("root", nodes.get(0), 3, 4, "FMF1QTZ0QKC83OJVA8SSG24YXB1");
        testNode("node"+1, nodes.get(1), 2, 3, "4OVZBRS9FDP56R7OTD");
        testNode("node"+2, nodes.get(2), 2, 0, "14ST01GLP319BCWVH9");
        testNode("node"+3, nodes.get(3), 2, 0, "8ENSQEGV99BTCH8AHW");
        testNode("node"+4, nodes.get(4), 1, 0, "E465LZPOE");
        testNode("node"+5, nodes.get(5), 1, 2, "IUS3K395W");
        testNode("node"+6, nodes.get(6), 2, 0, "GFV8X4TT0HRR27B6JY");
        testNode("node"+7, nodes.get(7), 1, 0, "JZXHZFCB6");
        testNode("node"+8, nodes.get(8), 1, 2, "NXM6F6UWJ");
        testNode("node"+9, nodes.get(9), 1, 0, "L2Z7499YH");
        testNode("node"+10, nodes.get(10), 1, 0, "OTYAH43JX");
        testNode("node"+11, nodes.get(11), 1, 2, "W0KBB1RE7");  
        testNode("node"+12, nodes.get(12), 3, 0, "TY4L8P0D3U36TUQ5NOVM22T8ESM");
        testNode("node"+13, nodes.get(13), 1, 0, "YMUEM9LVC");
    }
    
    @Test public void testInsertFile1_InOrderTraversal() {
        B_Tree b = new B_Tree(2);
        b.constructB_TreeFromFile("TestFile.txt");
        ArrayList<String> out = b.getB_Tree();
        ArrayList<TestNode> test_tree = this.constractB_Tree(out);
        assertTrue("getInorderTraversal() doesn't deliver entries in inorder traversal!", testOrderOfEntries(b.getInorderTraversal()));
    }
    
    @Test public void testDeleteFile1_ReturnedEntries() {
        B_Tree b = new B_Tree(2);
        b.constructB_TreeFromFile("TestFile.txt");
        Entry e1 = b.delete("L2Z7499YH");
        Entry e2 = b.delete("FMF1QTZ0Q");
        Entry e3 = b.delete("L2Z74TZ0Q");
        ArrayList<String> out = b.getB_Tree();      
        ArrayList<TestNode> test_tree = this.constractB_Tree(out);
        assertEquals("delete(L2Z7499YH) output not correct!", "L2Z74;99YH;Error", e1.toString());
        assertEquals("delete(FMF1QTZ0Q) output not correct!", "FMF1Q;TZ0Q;Error", e2.toString());
        assertTrue("delete(L2Z74TZ0Q) output not correct!", null == e3);
    }
    
    @Test public void testDeleteFile1_NumberOfNodes() {
        B_Tree b = new B_Tree(2);
        b.constructB_TreeFromFile("TestFile.txt");
        Entry e1 = b.delete("L2Z7499YH");
        Entry e2 = b.delete("FMF1QTZ0Q");
        Entry e3 = b.delete("L2Z74TZ0Q");
        ArrayList<String> out = b.getB_Tree();
        ArrayList<TestNode> test_tree = this.constractB_Tree(out);
        
        assertEquals("Number of nodes not correct!", 12, test_tree.size());
    }
    
    @Test public void testDeleteFile1_HeightOfTree() {
        B_Tree b = new B_Tree(2);
        b.constructB_TreeFromFile("TestFile.txt");
        Entry e1 = b.delete("L2Z7499YH");
        Entry e2 = b.delete("FMF1QTZ0Q");
        Entry e3 = b.delete("L2Z74TZ0Q");
        ArrayList<String> out = b.getB_Tree();
        ArrayList<TestNode> test_tree = this.constractB_Tree(out);
        assertEquals("Height of the tree not correct!", 2, b.getB_TreeHeight());
    }
    
    @Test public void testDeleteFile1_NumberOfEntries() {
        B_Tree b = new B_Tree(2);
        b.constructB_TreeFromFile("TestFile.txt");
        Entry e1 = b.delete("L2Z7499YH");
        Entry e2 = b.delete("FMF1QTZ0Q");
        Entry e3 = b.delete("L2Z74TZ0Q");
        ArrayList<String> out = b.getB_Tree();
        ArrayList<TestNode> test_tree = this.constractB_Tree(out);
        assertEquals("Number of the entries is not correct!", 20, b.getB_TreeSize());
    }
    
    @Test public void testDeleteFile1_NodesOfTree() {
        B_Tree b = new B_Tree(2);
        b.constructB_TreeFromFile("TestFile.txt");
        Entry e1 = b.delete("L2Z7499YH");
        Entry e2 = b.delete("FMF1QTZ0Q");
        Entry e3 = b.delete("L2Z74TZ0Q");
        ArrayList<String> out = b.getB_Tree();
        ArrayList<TestNode> test_tree = this.constractB_Tree(out);
        testNode("root", nodes.get(0), 2, 3, "E465LZPOESSG24YXB1");
        testNode("node"+1, nodes.get(1), 2, 3, "4OVZBRS9F9BTCH8AHW");
        testNode("node"+2, nodes.get(2), 2, 0, "14ST01GLP319BCWVH9");
        testNode("node"+3, nodes.get(3), 1, 0, "8ENSQEGV9");
        testNode("node"+4, nodes.get(4), 1, 0, "DP56R7OTD");
        testNode("node"+5, nodes.get(5), 2, 3, "IUS3K395WNXM6F6UWJ");
        testNode("node"+6, nodes.get(6), 2, 0, "GFV8X4TT0HRR27B6JY");
        testNode("node"+7, nodes.get(7), 2, 0, "JZXHZFCB6KC83OJVA8");
        testNode("node"+8, nodes.get(8), 1, 0, "OTYAH43JX");
        testNode("node"+9, nodes.get(9), 1, 2, "W0KBB1RE7");
        testNode("node"+10, nodes.get(10), 3, 0, "TY4L8P0D3U36TUQ5NOVM22T8ESM");
        testNode("node"+11, nodes.get(11), 1, 0, "YMUEM9LVC");
    }
    
    @Test public void testDeleteFile1_InOrderTraversal() {
        B_Tree b = new B_Tree(2);
        b.constructB_TreeFromFile("TestFile.txt");
        Entry e1 = b.delete("L2Z7499YH");
        Entry e2 = b.delete("FMF1QTZ0Q");
        Entry e3 = b.delete("L2Z74TZ0Q");
        ArrayList<String> out = b.getB_Tree();
        ArrayList<TestNode> test_tree = this.constractB_Tree(out);
        
        assertTrue("getInorderTraversal() doesn't deliver entries in inorder traversal!", testOrderOfEntries(b.getInorderTraversal()));
    }
    
    @Test public void testfindFile1_ReturnedEntries() {
        B_Tree b = new B_Tree(2);
        b.constructB_TreeFromFile("TestFile.txt");
        Entry e1 = b.find("SSG24YXB1");
        Entry e2 = b.find("SSG27YXB1");
        assertEquals("find(SSG24YXB1) output not correct!", "SSG24;YXB1;Error", e1.toString());
        assertTrue("find(SSG27YXB1) output not correct!", null == e2);
    }
  
    

	@Test public void testInsertFile1_Balanced() {
		B_Tree b = new B_Tree(2);
		b.constructB_TreeFromFile("TestFile.txt");
		ArrayList<String> out = b.getB_Tree();
		ArrayList<TestNode> test_tree = this.constractB_Tree(out);
		assertTrue("The Tree is not balanced!", isBalanced(test_tree.get(0)));
	}


	@Test public void testDeleteFile1_Balanced() {
		B_Tree b = new B_Tree(2);
		b.constructB_TreeFromFile("TestFile.txt");
		Entry e1 = b.delete("L2Z7499YH");
		Entry e2 = b.delete("FMF1QTZ0Q");
		Entry e3 = b.delete("L2Z74TZ0Q");
		ArrayList<String> out = b.getB_Tree();
		ArrayList<TestNode> test_tree = this.constractB_Tree(out);
		assertTrue("The Tree is not balanced!", isBalanced(test_tree.get(0)));
	}

 
    protected void testNode(String name, TestNode t, int n_e, int n_ch, String ent) {
        assertEquals("Number of entries of "+name+" not correct!", n_e, t.getEntries().size());
        assertEquals("Number of childen of "+name+" not correct!", n_ch, t.getChildren().size());
        assertEquals("Entries of "+name+" not correct!", ent, t.entriesToString());
    }
    
    protected boolean testOrderOfEntries(ArrayList<Entry> e) {
        for(int i=0;i<e.size()-1;i++) {
            if(e.get(i).compareTo(e.get(i+1)) >= 0) {
                return false;
            }
        }
        return true;
    }
    
    protected void parseB_Tree(ArrayList<String> output, int k) {
        int i=0;
        String line;
        while (i<output.size()) {
            line = output.get(i);
            /**only progress if the word "Digraph" and "}" is not contained
             * in the line. "Digraph" is only contained in the first and
             * "}" only in the last line.*/
            if (!line.contains(DotFileConstants.DOT_FILE_DIGRAPH)
            && !line.contains(DotFileConstants.DOT_FILE_CLOSE_BRACKET)
            && !line.contains(DotFileConstants.DOT_FILE_SOURCE)) {
                
                //progress a node
                if(line.contains(DotFileConstants.DOT_FILE_LABEL_START)) {
                    progressNode(line, k);
                }
                //progress a pointer
                else if(line.contains(DotFileConstants.DOT_FILE_EDGE)) {
                    progressPointer(line);
                }
            }
            i++;
        }
    }
    
    protected void progressNode(String line, int k) {
        TestNode result = new TestNode(k);
        String[] names = line.split("\\[");
        String[] entries = (line.split("\""))[1].split("\\|?+<f[0-9]+>\\*?+\\|?+");
        for(int i=0;i<entries.length;i++) {
            if(!entries[i].equals("")) {
                Entry e = new Entry();
                e.setKey(entries[i]);
                result.addEntry(e);
            }
        }
        node_names.add(names[0].trim());
        nodes.add(result);
    }
    
    protected void progressPointer(String line) {
        String pointer = (line.split(";"))[0];
        pointers.add(pointer.trim());
    }
    
    
    protected ArrayList<TestNode> constractB_Tree(ArrayList<String> output) {
        ArrayList<TestNode> r = new ArrayList<TestNode>();
        parseB_Tree(output, 2);
        Collections.sort(pointers);
        TestNode root = nodes.get(node_names.indexOf("root"));
        ArrayList<String> pts = new ArrayList<String>();
        for(int i=0;i<pointers.size();i++) {
            String[] p = pointers.get(i).split("->");
            String parent = (p[0].split(":"))[0];
            pts.add(parent.trim());
            pts.add(p[1].trim());
        }
        for(int i=0;i<pts.size();i=i+2) {
            nodes.get(node_names.indexOf(pts.get(i))).addChild(nodes.get(node_names.indexOf(pts.get(i+1))));
        }
        
        r.add(root);
        int i=0;
        while(i<r.size()) {
            r.addAll(r.get(i).getChildren());
            i++;
        }
        return r;
    }
    
    private void printArrayList(ArrayList<String> dot) {
        try {
            FileWriter fw = new FileWriter("test.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            
            for (String string : dot) {
                bw.write(string + System.getProperty("line.separator"));
            }
            
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

	protected boolean isBalanced(TestNode t) {
		return getBalancedHeight(t) > -1;
	}

	/**
	 * @param t
	 *            the root of the checked subtree
	 * @return -1 if the subtree isnt balanced, and the height of the subtree,
	 *         if it is.
	 */
	protected int getBalancedHeight(TestNode t) {
		ArrayList<TestNode> children = t.getChildren();
		if (children.isEmpty())
			return 0;
		int height = getBalancedHeight(children.get(0));
		for (TestNode child : children) {
			if (height != getBalancedHeight(child))
				return -1;
		}
		return (height == -1) ? -1 : height + 1;
	}


    

    
    
    
    
    
    
    
    
    
    
}
