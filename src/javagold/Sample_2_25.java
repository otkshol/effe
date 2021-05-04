package javagold;

import java.util.function.Function;

class MyFunc implements Function<String, String>{
    public String apply(String str){
        return "Hello " + str;
    }
}

public class Sample_2_25 {
    public static void main(String[] args) {
        MyFunc obj = new MyFunc();
        String str = obj.apply("naoki");
        System.out.println(str);
        // Java7までは匿名クラスを使用していたが、Java8からはラムダ式を使用することでよりシンプルに実装できるようになっている
    }
}