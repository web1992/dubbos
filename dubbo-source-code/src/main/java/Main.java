

/**
 * <p>
 * Version		1.0.0
 *
 * @author web1992
 * <p>
 * Date	      2019/3/7 10:19
 */
public class Main {
    public static void main(String[] args) {
        final byte FLAG_REQUEST = (byte) 0x80;
        System.out.println(FLAG_REQUEST);

        String s1 = "abc";
        String s2 = new String("abc");
        System.out.println(s1.equals(s2));
    }
}
