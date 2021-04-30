package javagold;

/**
 * MySingletonクラスを利用するクラス
 */
public class Sample_1_6 {
    public static void main(String[] args) {
        MySingleton obj1 = MySingleton.getInstance();
        MySingleton obj2 = MySingleton.getInstance();
        if (obj1 == obj2){ // ちゃんｔIDEが常にtrueになることを指摘してくれている
            System.out.println("obj1 == obj2");
        } else {
            System.out.println("obj1 != obj2");
        }
    }
}