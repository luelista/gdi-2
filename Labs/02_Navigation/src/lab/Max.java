package lab;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created 08.05.14 11:12.
 *
 * @author Max Weller
 * @version 2014-05-08-001
 */
public class Max {

    public static ArrayList<String> stringArrayToArrayList(String[] a) {
        ArrayList<String> l = new ArrayList<>(a.length);
        for (String s : a) l.add(s);
        return l;
    }

    public static String joinArrayListOfString(String joint, ArrayList<String> a) {
        StringBuilder builder = new StringBuilder();
        for (String s : a) {
            builder.append(s);
            builder.append(joint);
        }
        return builder.toString();
    }

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
