package lab;

import java.util.ArrayList;

/**
 * Created 21.05.14 15:59.
 *
 * @author Max Weller
 * @version 2014-05-21-001
 */
public class Max {

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

    public static void main(String[] args) {
        MaxFlow m = new MaxFlow("Iksburg4.txt");
        System.out.println(m.file.toString());
        String path = m.PathFromTo("Luisenplatz", "Ilse_Langner_Platz", ",");
        System.out.println("RESULT: "+path);
        for(Edge s:m.GetPathEdges(path))
            System.out.println(s);
        System.out.println(m.FindMaxRemainingCap(m.GetPathEdges(path)));
    }

}
