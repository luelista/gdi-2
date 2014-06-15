package lab;

import java.util.ArrayList;

/**
 * Created 21.05.14 15:59.
 *
 * @author Max Weller
 * @version 2014-05-21-001
 */
public class Max {
    public static boolean DEBUG = false;

    //join(String array,delimiter)
    public static String join(String r[],String d)
    {
        if (r.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        int i;
        for(i=0;i<r.length-1;i++)
            sb.append(r[i]+d);
        return sb.toString()+r[i];
    }

    /**
     * join all items of an arraylist together
     * note: the joint is also added after the last element
     * @param joint  string to insert between elements
     * @param a the array list
     * @return the joined string
     */
    public static String joinArrayListOfString(String joint, ArrayList<String> a) {
        StringBuilder builder = new StringBuilder();
        for (String s : a) {
            builder.append(s);
            builder.append(joint);
        }
        return builder.toString();
    }

    /**
     * truncate a string to the maximum length
     */
    public static String truncstr(String str, int maxlen) {
        return (str.length() > maxlen) ?
                str.substring(0, maxlen) : str;
    }

    public static void main(String[] args) {
        B_Tree tree = new B_Tree(2);
        tree.constructB_TreeFromFile("TestFile.txt");
        System.out.println(Max.joinArrayListOfString("\n", tree.getB_Tree()));
    }

}
