package javagold;

class Foo_2_2{
    public void method(String s, int... a){
        System.out.println(s + " サイズ : " + a.length);
        for(int i : a ){
            System.out.println(" 第２引数の値 :" + i);
        }
    }
}

public class Sample_2_2 {
    public static void main(String[] args) {
        Foo_2_2 obj = new Foo_2_2();
        int[] ary = {10, 20, 30};
        obj.method("1回目");
        obj.method("2回目", 10);
        obj.method("3回目", 10, 20);
        obj.method("4回目", ary);
        obj.method("5回目", null);
    }
}
