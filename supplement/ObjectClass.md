# インスタンスの基本操作 (スッキリわかるJava実践編第4章ざっくりまとめ)

## インスタンスの5大操作

- `toString()` 文字列表現を得る
- `equals()` 等価判定を行う
- `hashCode()` ハッシュ値を得る
- `compareTo()` 大小関係を判定する
- `clone()` 複製する

---
## `toString()` 文字列表現を得る
インスタンスの内容を人間が分かる文字列にして返す。
**デバッグやログ出力で活躍する！**（よく使う`System.out.println()`も内部で`toString()`を使用している）

`Object.toString()`は「クラス名 + '@' + オブジェクトのハッシュ・コードの符号なし16進数」で表現される
[Javadocはこちら](https://docs.oracle.com/javase/jp/8/docs/api/java/lang/Object.html#toString--)

**公式にも`toString()`をオーバーライドして、人間が読める文字列を返すことを推奨されている**

---
## インスタンスの等価判定
### 等価(equivarent)と等値(equality)の違い
`Object.equals()`は「等価判定のみ行い、参照値の比較結果のみ」が出力される
[Javadocはこちら](https://docs.oracle.com/javase/jp/8/docs/api/java/lang/Object.html#equals-java.lang.Object-)

**等値判定（Effective Javaでは論理的等価性とよんでいる）を行うためにはequalsメソッドをオーバーライドする必要がある**

---
## インスタンスの要約

HashSetの内部動作では、下記２つの動作で等価判定をしている
-  Hash値を使用して「だいたい同じか？」を判定する
-  「だいたい同じ」オブジェクトに対してのみ`equals()`メソッドを適用する

**`hashCode()`もオーバーライドする必要がある**

---
## インスタンスの順序づけ
並び替えで代表的なメソッドに`Collection.sort()`がある。しかし、このメソッドには制約がある。
JVM的には何で並び替えをするべきか分からず、エラーが発生することがある。
ここで`java.lang.Comparable`インタフェースの`compareTo()`メソッドをオーバーライドすると解決される。
### 大小関係を判定する`compareTo()`をオーバーライドするときの制約 [Javadocはこちら](https://docs.oracle.com/javase/jp/8/docs/api/java/lang/Comparable.html)
- 比較元が比較対象より小さい場合: 負の数をリターン
- 比較元が比較対象と等しい場合: 0をリターン
- 比較元が比較対象より大きい場合:　正の数をリターン

---
## インスタンスの複製
`clone()`メソッドを使用する

### `clone()`メソッドを使用するときの注意点
- `Clonable`インタフェースを実装する
- `clone()`をpublicでオーバーライドする
    - Javaではオーバーライド時にアクセス修飾子を変更できる。しかし、`protected`から`public`のように制限をゆるくする方向のみ可能。

### `Clonable`は**マーカーインターフェース**
1つもメソッドを持っていないインターフェース`clone()`すらも定義されていない。

### 浅いコピーと深いコピー
- 浅いコピー (Shallow copy)
    - 参照のみのコピーで、インスタンスは複製されない
- 深いコピー
    - フィールドなども別インスタンスとして複製する