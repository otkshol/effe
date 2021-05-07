package javagold;

import java.util.Arrays;
import java.util.List;

public class Sample_4_2 {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("Tanaka", "Sato");
        // ラムダ式で実装した場合（記述省略ver）
        words.replaceAll(str -> str.toUpperCase());
        System.out.println(words);
    }
}
