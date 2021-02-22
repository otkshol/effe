package effe.equals;

public class Main {
    public static void main(String[] args) {
        PhoneNumber pn = new PhoneNumber(111, 111,1111);
        PhoneNumber pn2 = new PhoneNumber(111, 111,9999);
        // 比較を呼びだしてみる
        System.out.println(pn.equals(pn));
        System.out.println(pn.equals(pn2));
    }
}
