# equalsをオーバーライドするときは、常にhashCodeをオーバーライドする

**equalsをオーバーライドしているすべてのクラスで、hashCodeをオーバーライドする必要がある。**　オーバーライドを忘れると、`HashMap`や`HashSet`で論理的等価なオブジェクトに対して`equals`が作用しないバグが発生する。

---
## hashCodeの一般契約
### **hashCodeの整合律**
論理的等価である2つのオブジェクト対して、それぞれ`hashCode`メソッドを実行すると同じ結果が返り、アプリケーションの再起動がかかるまでは何回実行しても結果は変わらない。

### **hashCodeの包含律**
`hashCode`の一致は`equals`を包含している。ただし`hashCode`と`equals`の領域は一致している必要はない。（`hashCode`と`equals`の領域が一致している状態が理想）

---

### hashCodeをオーバーライド忘れでバグる例
HashMapの`get()`と`put()`でそれぞれインスタンス生成している場合。
```
Map<PhoneNumber, String> m = new HashMap<>();
m.put(new PhoneNumber(707, 867, 5309), "Jenny");
// getしてもnullが返される！（論理的等価であるがhashCodeの値が異なるのでputでいれたインスタンスとは別の参照を見に行ってnullになっている）
m.get(new PhoneNumber(707, 867, 5309), "Jenny");
```

---
### hashCodeの悪いオーバーライドの例
一般契約は守っているが、計算量が*O*(1)から*O*(n)になり性能が非常に悪くなる。理由はデータ構造がハッシュテーブルから実質リンクドリストに変更されてしまうため。幾何的なイメージだと包含しているhashCodeの範囲が全平面になる。
[参考ウィキペディア](https://en.wikipedia.org/wiki/Hash_table)
```
@Override public int hashCode() {return 42; }
```

---
### hashCodeをオーバーライドするレシピ
- レシピ１
    - オブジェクト内の意味のあるフィールドに対するハッシュコード`c`で初期化する
- レシピ２
    - 残りのフィールド`f`に対しては、下記のようにハッシュコードを計算する
        - フィールドが基本データ型の場合
            - `Type.hashCode(f)`を計算する
        - フィールドがオブジェクト参照の場合
            - 基本データ型の比較に行き着くまでJavaの内部で再帰的にhashCodeを呼び出して計算する
            - Patternインスタンスのような複雑な比較なら、正規形に変換してそれに対してhashCodeを呼ぶ
            - フィールドの値がnullなら0を返す（他の定数でも良いが、伝統的に0らしい...そもそも`NullPointerException`が起こる気もする...）
        - フィールドが配列の場合
            - 配列の要素がすべて意味のある場合
                - `Arrays.hashCode`を使う
            - 配列の要素がすべて意味ない場合
                - ０でない定数として扱う
            - 配列の要素が意味のあるものとないものが混ざっている場合
                - 各要素をそれぞれ別のフィールドとして扱い、意味のあるフィールドのみに対してhashCodeを使う
- レシピ３
    - レシピ２で計算されたハッシュコード`c`に対して、下記の計算をする
    ```
    result = 31 * result + c;
    ```
---


```
// 典型的なhashCodeメソッド
@Override public int hashCode() {
    int result = Short.hashCode(areaCode); // レシピ１に相当
    result = 31 * result + Short.hashCode(prefix); // レシピ２に相当
    result = 31 * result + Short.hashCode(lineNum); // レシピ２に相当
    return result; // レシピ３に相当
}
```

---
#### 未解決の疑問
- hashCodeの初期値は0?そうでない？

- 正規形のhashCodeを返すメリットが分からない

- 31の理由が分からない
ハッシュ値を満たすフィールドの組み合わせを減らして、限りなく１つにしたい？
（定数をかける意味はわかったが、なぜ３１かまではわかってない）

- なんで奇数かつ素数？

---
補足

- バージョンの考え方
https://www2.opro.net/jp/opss/docs/version.html

- ネイティブメソッド

- ハッシュバケット






