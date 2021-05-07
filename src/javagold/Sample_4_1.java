package javagold;

import java.util.Arrays;
import java.util.List;

public class Sample_4_1 {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("Tanaka", "Sato");
        // 匿名クラスで実装した場合
//        words.replaceAll(new UnaryOperator<String>() {
//            @Override
//            public String apply(String str) {
//                return str.toUpperCase();
//            }
//        });
        // ラムダ式で実装した場合
        words.replaceAll((String str) -> {
            return str.toUpperCase();});
        System.out.println(words);
    }
}
