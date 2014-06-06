package lab;

/**
 * Created 21.05.14 15:59.
 *
 * @author Max Weller
 * @version 2014-05-21-001
 */
public class Max {

    public static void main(String[] args) {
        MaxFlow m = new MaxFlow("Iksburg1.txt");
        System.out.println(m.file.toDebugString());
        System.out.println(m.PathFromTo("A", "C"));
    }

}
