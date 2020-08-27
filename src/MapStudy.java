import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HashMapがよく使用される理由が分からなかったので
 * 他のMapの特徴を調べて、「HashMapが無難」という結論を腹落ちさせるため学習する
 * MapのJavadocの既知の実装クラスを試しに使ってみながら理解する。
 * https://docs.oracle.com/javase/jp/8/docs/api/java/util/Map.html
 */

public class MapStudy {

    public static void main(String[] args) {

        /**
         * HashMap
         * https://docs.oracle.com/javase/jp/8/docs/api/java/util/HashMap.html
         */
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("hashMap", 1);
        System.out.println("hashMap: " + hashMap.get("hashMap"));

        /**
         * ConcurrentHashMap
         * https://docs.oracle.com/javase/jp/8/docs/api/java/util/concurrent/ConcurrentHashMap.html
         */
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("concurrentHashMap", 1);
        System.out.println("concurrentHashMap: " + concurrentHashMap.get("concurrentHashMap"));

        /**
         * LinkedHashMap
         * https://docs.oracle.com/javase/jp/8/docs/api/java/util/LinkedHashMap.html
         */
        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("linkedHashMap", 1);
        System.out.println("linkedHashMap: " + linkedHashMap.get("linkedHashMap"));

        /**
         * WeakHashMap
         * https://docs.oracle.com/javase/jp/8/docs/api/java/util/WeakHashMap.html
         */
        WeakHashMap<String, Integer> weakHashMap = new WeakHashMap<>();
        weakHashMap.put("weakHashMap", 1);
        System.out.println("weakHashMap: " + weakHashMap.get("weakHashMap"));

        /**
         * TreeMap
         * https://docs.oracle.com/javase/jp/8/docs/api/java/util/TreeMap.html
         */
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("treeMap", 1);
        System.out.println("treeMap: " + treeMap.get("treeMap"));
    }
}
