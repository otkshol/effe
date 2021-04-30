package javagold;

class Foo_1_10 {
}

class Bar {
    public String toString() {
        return "This is an object made from Bar";
    }
}

public class Sample_1_10 {
    public static void main(String[] args) {
        int[] ary = {1, 2};
        String obj1 = "tanaka";
        Foo_1_10 obj2 = new Foo_1_10();
        Bar obj3 = new Bar();
        // Objectクラスの toStringはオブジェクトのクラス名 + "@"（アットマーク） + オブジェクトのハッシュコードの符号なし16進数表現

        System.out.println(ary);
        // printlnメソッド内でtoString（）をオーバーライドしているので文字列が出力される
        System.out.println(obj1);
        System.out.println(obj2);
        System.out.println(obj3);
    }
}
