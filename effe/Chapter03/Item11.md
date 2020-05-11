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

- 実装したら同じハッシュコードを返すかテストする（AutoValue使用時はテスト不要）
- 算出フィールドはハッシュコードの計算から除外できる。（冗長な情報は削る）
- equals比較で用いないフィールドの除外も必須（意味のある(*significant*)な変数のみであることを意味する）

---
### 31をかける理由

- 0でない正の整数をかける理由
同一ハッシュ値を満たすフィールドのハッシュコード値の組み合わせを減らして、限りなく１つに近づけてequalsメソッドと同じ領域にしたい。

- 31の理由が分からない（奇数かつ素数だかららしい...）
    - 奇数の理由
        - 偶数かつオーバーフローしたときに、２をかけることはシフトすることになる（？？？）
            - たぶん議論になる。（）
        - [参考記事](https://www.thekingsmuseum.info/entry/2015/08/28/000748)
        - [参考記事２](https://computinglife.wordpress.com/2008/11/20/why-do-hash-functions-use-prime-numbers/)
    - 素数
        - メリットは明確でないが伝統的に使用されている
        - 31のメリットは乗算がシフトと減算で置き換えられること
        ```
        // どれがシフトでどれが演算？？
        31 * i == (i << 5 ) - i
        ```
---
#### 最先端のハッシュ関数
上記の例で妥当なハッシュ関数であり、Javaでは同等のものが使用されているが、もっと衝突を抑えようとおもったら
[Guavaのやつを参照する。](https://github.com/google/guava/blob/master/guava/src/com/google/common/hash/Hashing.java)

---
#### Objectクラスのhash()の性能

`Object.hash`は引数が基本データ型ならオートボクシングが走ってコストがかかる。（）

---
#### クラスが不変で計算のコストが高い場合
- ハッシュコードをいちいち計算せずに、ハッシュコードをキャッシュすることを検討すべき。
- ハッシュのキーとして使われる場合は **遅延初期化(*lazily initialize*)** を選択しても良い。(ただし頻繁に作成されるインスタンスのハッシュコードであるべきではない)

```
// 遅延初期化されてキャッシュされたハッシュコードを持つhashCodeメソッド
private int hashCode(); // 自動的に0に初期化される

@Override public int hashCode() {
    int result = hasCode
    if (result == 0){
        int result = Short.hashCode(areaCode);
        result = 31 * result + Short.hashCode(prefix);
        result = 31 * result + Short.hashCode(lineNum);
        hashCode = result;
    }
    return result;
}
```

---
#### その他注意点
- パフォーマンス向上を狙って、ハッシュコードの計算から意味のあるオブジェクトの
フィールドを除外してはいけない
    - なぜなら論理的等価なものを認めないバグが起きえるから！
- hashCodeが返す値の詳細な仕様を提供しない
    - 提供しないことで、変更に対する柔軟性を得ることができる。
    - しかし、Java標準ライブラリの`String`や`Integer`もhashCodeで返される値を明記している。Joshua Bloch的にはアンチパターン

---
#### まとめ
- equalをオーバーライドするときはhashCodeもオーバーライドする必要がある。
- オーバーライドするときにはレシピにしたがう必要がある。
- AutoValueフレームワーク使うのが最も便利。IDEでも自動生成できる。

---
#### 未解決の疑問
- hashCodeの初期値は0?そうでない？
    - Effective Java とスッキリJavaで主張が違う

- 正規形のhashCodeを返すメリットが分からない

---

補足
- バージョンの考え方
https://www2.opro.net/jp/opss/docs/version.html







