package lab;

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
        for(String s : a) l.add(s);
        return l;
    }

}
