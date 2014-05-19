package lab;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * some test methods and helper methods
 * Created 08.05.14 11:12.
 *
 * @author Max Weller
 * @version 2014-05-08-001
 */
public class Max {

    /**
     * convert a string array to an arraylist<string>
     * @param a the string array
     * @return the array list
     */
    public static ArrayList<String> stringArrayToArrayList(String[] a) {
        ArrayList<String> l = new ArrayList<>(a.length);
        for (String s : a) l.add(s);
        return l;
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
     * write a string to a text file
     * @param filename file to write to
     * @param content string to write
     */
    public static void filePutContents(String filename, String content) {
        try {

            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * some tests
     */
    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("user.dir"));

        String res;
        Navigation n;

        n = new Navigation("testfile3.txt");
        System.out.println(n.toDebugString());

        res = Max.joinArrayListOfString("\n", n.findFastestRoute("A", "C"));
        System.out.println(res);
        System.out.println("time=" + n.findFastestTime("A" , "C"));

        filePutContents("output_test3.txt", res);

/*
        n = new Navigation("testfile1.txt");
        System.out.println(n.toDebugString());

        System.out.println(Max.joinArrayListOfString("\n", n.findShortestRoute("A", "D")));
        System.out.println(Max.joinArrayListOfString("\n", n.findShortestRoute("D", "A")));
        */
    }


}
